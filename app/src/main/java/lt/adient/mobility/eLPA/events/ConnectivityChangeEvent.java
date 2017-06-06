package lt.adient.mobility.eLPA.events;


public class ConnectivityChangeEvent {
    private final boolean connected;

    public ConnectivityChangeEvent(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }
}
