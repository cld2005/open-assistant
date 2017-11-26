import app.DummyAppManager;
import io.undertow.Undertow;

public class Server {

    private final RequestHandler requestHandler;
    private Config config;

    private Server() {
        requestHandler = new RequestHandler(new DummyAppManager());
        config = Config.getInstance();
    }

    private void start() {
        Undertow.builder()
                .addHttpListener(config.serverPort, "localhost")
                .setHandler(requestHandler).build().start();
    }

    public static void main(final String[] args) {
        Server server = new Server();
        server.start();
    }

}
