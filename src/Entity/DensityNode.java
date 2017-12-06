package Entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DensityNode extends RecursiveTreeObject<DensityNode> {
    private String nodeID;
    private int density;

    public DensityNode(String n, int density){
        this.nodeID = n;
        this.density = density;
    }

    public String getNodeID(){
        return nodeID;
    }
    public Integer getDensity(){return density;}
}
