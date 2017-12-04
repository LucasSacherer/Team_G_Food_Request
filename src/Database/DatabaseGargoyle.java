package Database;

import Manager.EntityManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseGargoyle {
    final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private Connection connection;
    private Statement statement;
    private ArrayList<EntityManager> managers;

    public DatabaseGargoyle() {
        this.managers = new ArrayList<>();
    }

    /**
     * Loads the drivers for the database connection, and then opens a connection to the DB with an open statement
     */
    public void createConnection() {
        //Load driver
        try {
            Class.forName(driver).newInstance();
        }catch (Exception ex){
            System.out.println("Failed to find Embedded JavaDB driver!");
            ex.printStackTrace();
        }

        //Create connection and statement to be run
        try {
            this.connection = DriverManager.getConnection("jdbc:derby:derby-db;create=true;user=granite_gargoyle;password=wong");
            this.statement = connection.createStatement();
        }catch (SQLException ex){
            System.out.println("Exception thrown in createConnection()");
            ex.printStackTrace();
        }
    }

    /**
     * Destroys the current connection to the database, as well as it's statement
     */
    public void destroyConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Exception thrown in destroyConnection()");
            e.printStackTrace();
        }
    }

    /**
     * Creates all the tables in the database
     */
    public void createTables() {
        TableCreator tableCreator = new TableCreator(this.statement);
        tableCreator.createMenuItemTable(this.connection);
        tableCreator.createWorkerTable();
        tableCreator.createNodeTable(this.connection);
        tableCreator.createFoodRequestTable();
        tableCreator.createFoodOrderTable();
        tableCreator.createFoodLogTable();
    }

    /**
     * Runs an update query on the database
     * @param sql
     */
    public void executeUpdateOnDatabase(String sql) {
        try {
            statement.executeUpdate(sql);
            this.destroyConnection();
            notifyManagers();
            this.createConnection();
        } catch (SQLException e) {
            System.out.println("The statement " + sql +  " failed: ");
            e.printStackTrace();
        }
    }

    /**
     * Runs a query on the database and returns the results
     * @param sql
     * @return
     */
    public ResultSet executeQueryOnDatabase(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("The statement " + sql +  " failed: ");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adds a manager to the list of managers to be notified of updates
     * @param manager
     */
    public void attachManager(EntityManager manager) {
        this.managers.add(manager);
    }

    /**
     * Updates all the managers
     */
    public void notifyManagers(){
        for (EntityManager manager: managers){
            manager.update();
        }
    }

    /**
     * Returns the list of managers
     * @return
     */
    public ArrayList<EntityManager> getManagers() {return managers; }
}
