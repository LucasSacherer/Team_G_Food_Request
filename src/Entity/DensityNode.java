package Entity;

public class DensityNode {
    private String nodeID;
    private int density;

    public DensityNode(String n, int density){
        this.nodeID = n;
        this.density = density;
    }

    public String getNodeID(){
        return nodeID;
    }
    public int getDensity(){return density;}
}
