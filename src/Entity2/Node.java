package Entity2;

public class Node {
    private String nodeID;
    private int xcoord;
    private int ycoord;
    private String floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;

    public Node(String nodeID, int xcoord, int ycoord, String floor, String building,
                String nodeType, String longName, String shortName) {
        this.nodeID = nodeID;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }

    public String getNodeID() {
        return nodeID;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public String getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    /**
     * Returns true if the node is visitable
     * @return
     */
    public boolean isVisitable() {
        if (nodeType != "HALL"){
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return this.shortName;
    }
}
