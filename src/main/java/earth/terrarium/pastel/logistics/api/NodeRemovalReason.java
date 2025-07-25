package earth.terrarium.pastel.logistics.api;

public enum NodeRemovalReason {
    UNLOADED(false, false),
    BROKEN(true, true),
    DISCONNECT(true, true),
    REMOVED(false, false);

    public final boolean destructive;
    public final boolean checksForNetworkSplit;

    NodeRemovalReason(boolean destructive, boolean checksForNetworkSplit) {
        this.destructive = destructive;
        this.checksForNetworkSplit = checksForNetworkSplit;
    }
}
