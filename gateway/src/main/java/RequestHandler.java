import app.AppDelegate;
import app.AppManager;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        exchange.getResponseSender().send("Nice, app is located at " + appDelegate.ip + ":" + appDelegate.port);

    }

    private String getRequestBody(HttpServerExchange exchange) {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            exchange.startBlocking();
            reader = new BufferedReader(new InputStreamReader(exchange.getInputStream()));

            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line)
            }
        } catch(IOException e) {
            e.printStackTrace( );
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();

    }

    private void forwardRequest(AppDelegate appDelegate, Message message) {
    }

}

