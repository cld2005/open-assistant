import app.AppDelegate;
import app.AppManager;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.apache.commons.io.IOUtils;
import protobuf.Communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

public class RequestHandler implements HttpHandler {

    private final AppManager appManager;
    RequestHandler(AppManager appManager) {
        this.appManager = appManager;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String path = exchange.getRelativePath();
        AppDelegate appDelegate = appManager.lookup(path.substring(1));
        if (appDelegate == null) {
            exchange.getResponseSender().send("APP NOT FOUND");
            return;
        }

        byte[] requestBody = getRequestBody(exchange);
        Communication.Message message = Communication.Message.parseFrom(requestBody);
        byte[] responseBody = forwardRequest(appDelegate, message);
        if (responseBody == null) {
            exchange.getResponseSender().send("APP RETURNS NOTHING");
            return;
        }
        exchange.getResponseSender().send(ByteBuffer.wrap(responseBody));

    }

    private byte[] getRequestBody(HttpServerExchange exchange) throws IOException {
        return IOUtils.toByteArray(exchange.getInputStream());
    }

    private byte[] forwardRequest(AppDelegate appDelegate, Communication.Message message) {

        HttpURLConnection connection = null;

        int contentLength = message.getSerializedSize();

        try {

            // Initializes connection
            URL url = new URL(appDelegate.ip);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", Integer.toString(contentLength));

            // Forwards request
            DataOutputStream wr = new DataOutputStream ( connection.getOutputStream());
            wr.write(message.toByteArray());
            wr.close();

            // Reads response
            InputStream is = connection.getInputStream();
            return IOUtils.toByteArray(is);

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }

    }

}

