package app;

import java.util.HashMap;
import java.util.Map;

/**
 * DummyAppManager loads a predefined forwarding table to debug purpose. It assumes that all app servers are ready to
 * service, and it doesn't manage the state of app servers.
 */
public class DummyAppManager implements AppManager {

    private final Map<String, AppDelegate> lookupTable;

    public DummyAppManager() {
        lookupTable = new HashMap<>();
        lookupTable.put("apple", new AppDelegate("localhost", 8081));
    }

    @Override
    public AppDelegate lookup(String appName) {
        if (!lookupTable.containsKey(appName)) {
            return null;
        }
        return lookupTable.get(appName);
    }

}
