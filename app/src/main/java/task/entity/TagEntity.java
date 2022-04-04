package task.entity;

public class TagEntity {
    private final int nodeId;
    private final String k;
    private final String v;

    public TagEntity(int nodeId, String k, String v) {
        this.nodeId = nodeId;
        this.k = k;
        this.v = v;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}
