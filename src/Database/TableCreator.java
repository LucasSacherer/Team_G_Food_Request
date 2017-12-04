package Database;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TableCreator {
    private Statement statement;
    private String defaultMenuItemPath = "/Database/defaultMenuItems.txt";
    private String defaultNodesPath = "/Database/defaultNodes.txt";

    public TableCreator(Statement statement) {
        this.statement = statement;
    }

    /**
     * Creates the MenuITem table and adds the default items to the database as well
     * @param connection
     */
    public void createMenuItemTable(Connection connection) {
        //Create the table and add in the default nodes
        try {
            statement.execute("CREATE TABLE menuItem (\n" +
                    " foodName VARCHAR(250) PRIMARY KEY,\n" +
                    " description VARCHAR(250) NOT NULL,\n" +
                    " stockAvailable INTEGER NOT NULL,\n" +
                    " calories INTEGER NOT NULL,\n" +
                    " isVegan  VARCHAR(10) NOT NULL,\n" +
                    " isDiabetic VARCHAR(10) NOT NULL,\n" +
                    " isGluttenFree VARCHAR(10) NOT NULL\n)");
            System.out.println("MenuItem table created!");
            //Insert all Nodes to the table
            try {
                insertCSVToDatabase(defaultMenuItemPath, connection,"MENUITEM");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find path " + defaultMenuItemPath);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("MenuItem table already exists");
        }
    }

    /**
     * Creates the Node table and adds all the default nodes to it
     * @param connection
     */
    public void createNodeTable(Connection connection) {
        //Create the table and add in the default nodes
        try {
            statement.execute("CREATE TABLE node (\n" +
                    " nodeID varchar(20) PRIMARY KEY,\n" +
                    " xcoord INTEGER NOT NULL,\n" +
                    " ycoord INTEGER NOT NULL,\n" +
                    " floor varchar(100) NOT NULL,\n" +
                    " building varchar(250) NOT NULL,\n" +
                    " nodeType varchar(250) NOT NULL,\n" +
                    " longName varchar(250) NOT NULL,\n" +
                    " shortName varchar(250) NOT NULL,\n" +
                    " teamAssigned varchar(10) NOT NULL\n)");
            System.out.println("Node table created!");
            //Insert all Nodes to the table
            try {
                insertCSVToDatabase(defaultNodesPath, connection,"NODE");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find path " + defaultNodesPath);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Node table already exists");
        }
    }

    public void createWorkerTable() {
        //Create the table and add in the default nodes
        try {
            statement.execute("CREATE TABLE worker (\n" +
                    " workerID VARCHAR(250) PRIMARY KEY,\n" +
                    " username VARCHAR(250) NOT NULL\n)");
            System.out.println("Worker table created!");
            //Insert default workers to the table
            statement.executeUpdate("INSERT INTO WORKER VALUES ('worker1','worker1Username')");
            statement.executeUpdate("INSERT INTO WORKER VALUES ('worker2','worker2Username')");
        } catch (SQLException e) {
            System.out.println("Worker table already exists");
            e.printStackTrace();
        }
    }

    /**
     * Creates the FoodRequest table and adds a few test requests
     */
    public void createFoodRequestTable() {
        try {
            statement.execute("CREATE TABLE FoodRequest (\n" +
                    " name VARCHAR(250) NOT NULL,\n" +
                    " timeCreated TIMESTAMP NOT NULL,\n" +
                    " timeCompleted TIMESTAMP NOT NULL,\n" +
                    " type VARCHAR(250) NOT NULL,\n" +
                    " description VARCHAR(250) NOT NULL,\n" +
                    " nodeID VARCHAR(20) NOT NULL,\n" +
                    " workerID VARCHAR(20),\n" +
                    " CONSTRAINT foodRequest_PK PRIMARY KEY (name, timeCreated),\n" +
                    " CONSTRAINT foodNodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
            System.out.println("FoodRequest table created!");
            statement.executeUpdate("INSERT INTO FOODREQUEST VALUES ('food1','1960-01-01 23:03:20','1960-01-01 23:03:20','type1', 'description1','GRETL03501', 'worker1')");
            statement.executeUpdate("INSERT INTO FOODREQUEST VALUES ('food2','1960-01-01 23:03:20','1961-01-01 23:03:20','type2', 'description1','GSTAI00501', null)");
        } catch (SQLException e) {
            System.out.println("FoodRequest table already exists");
            //e.printStackTrace();
        }
    }


    public void createFoodOrderTable() {
        try {
            statement.execute("CREATE TABLE foodOrder (\n" +
                    " requestName VARCHAR(250) NOT NULL,\n" +
                    " timeCreated TIMESTAMP NOT NULL,\n" +
                    " foodName VARCHAR(250) NOT NULL,\n" +
                    " CONSTRAINT foodOrder_FK FOREIGN KEY (requestName, timeCreated) REFERENCES FoodRequest(name, timeCreated),\n" +
                    " CONSTRAINT foodItem_FK FOREIGN KEY (foodName) REFERENCES MenuItem(foodName))");
            System.out.println("FoodOrder table created!");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food1','1960-01-01 23:03:20','Milk')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food1','1960-01-01 23:03:20','Cereal')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food1','1960-01-01 23:03:20','Fruit')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food2','1960-01-01 23:03:20','Burger')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food2','1960-01-01 23:03:20','Ice Cream')");
        } catch (SQLException e) {
            System.out.println("FoodOrder table already exists");
            //e.printStackTrace();
        }
    }

    /**
     * Creates the FoodLog table
     */
    public void createFoodLogTable() {
        try {
            statement.execute("CREATE TABLE foodLog (\n" +
                    " foodName VARCHAR(250) NOT NULL,\n" +
                    " timeCreated TIMESTAMP NOT NULL, \n" +
                    " nodeID VARCHAR(250) NOT NULL\n)");
            System.out.println("FoodLog table created!");
        } catch (SQLException e) {
            System.out.println("FoodLog table already exists");
            //e.printStackTrace();
        }
    }


    /**
     * Reads a csv file of type (node or menuItem) and creates insert statements and executes them to the database
     * @param path
     * @param table
     * @throws FileNotFoundException
     */
    private void insertCSVToDatabase(String path, Connection connection, String table) throws FileNotFoundException {
        InputStream file = TableCreator.class.getResourceAsStream(path);
        Scanner br = new Scanner(file);
        String line;
        PreparedStatement query = null;

        while (br.hasNext()){
            line = br.nextLine();
            String[] array = line.split(",");
            //Execute query to database
            try {
                if (table.equals("NODE")){
                    query = connection.prepareStatement("INSERT INTO NODE VALUES (?,?,?,?,?,?,?,?,?)");
                    query.setString(1, array[0]);
                    query.setInt(2, Integer.parseInt(array[1]));
                    query.setInt(3, Integer.parseInt(array[2]));
                    query.setString(4,array[3]);
                    query.setString(5,array[4]);
                    query.setString(6,array[5]);
                    query.setString(7,array[6]);
                    query.setString(8,array[7]);
                    query.setString(9,array[8]);
                } else if (table.equals("MENUITEM")) {
                    query = connection.prepareStatement("INSERT INTO MENUITEM VALUES (?,?,?,?,?,?,?)");
                    query.setString(1, array[0]);
                    query.setString(2, array[1]);
                    query.setString(3, array[2]);
                    query.setString(4, array[3]);
                    query.setString(5, array[4]);
                    query.setString(6, array[5]);
                    query.setString(7, array[6]);
                }
                query.executeUpdate();
            } catch (SQLException e){
                System.out.println("Failed to execute: " + query.toString());
                e.printStackTrace();
            }
        }
    }
}
