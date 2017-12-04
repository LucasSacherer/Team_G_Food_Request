package Manager;

import Database.DatabaseGargoyle;
import Entity.Node;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NodeManager implements EntityManager{
    private List<Node> nodes;
    private DatabaseGargoyle databaseGargoyle;

    public NodeManager(DatabaseGargoyle databaseGargoyle) {
        this.databaseGargoyle = databaseGargoyle;
        nodes = new ArrayList<>();
    }

    /**
     * Updates the nodes to be up to date with the database
     */
    public void update(){
        int xcoord, ycoord;
        String floor, building, nodetype, longName, shortName;
        nodes.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM NODE");
        try {
            while(rs.next()){
                String nodeID = rs.getString("NODEID");
                xcoord = rs.getInt("XCOORD");
                ycoord = rs.getInt("YCOORD");
                floor = rs.getString("FLOOR");
                building = rs.getString("BUILDING");
                nodetype = rs.getString("NODETYPE");
                longName = rs.getString("LONGNAME");
                shortName = rs.getString("SHORTNAME");
                nodes.add(new Node(nodeID,xcoord,ycoord,floor,building,nodetype,longName,shortName));
            }
        } catch (SQLException e) {
            System.out.println("Failed to update the node manager!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns the node with the given nodeID, returns null if the node could not be found
     * @param nodeID the ID of the node you want to get
     * @return the corresponding node
     */
    public Node getNode(String nodeID){
        if(nodeID == null)
            return null;

        for(Node node : nodes){
            if (node.getNodeID().equals(nodeID)){
                return node;
            }
        }
        return null;
    }

    /**
     * For testing purposes
     * @return
     */
    public List<Node> getAllNodes(){
        return nodes;
    }
}
