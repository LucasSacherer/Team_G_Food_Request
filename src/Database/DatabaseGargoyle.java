package Database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseGargoyle {
    final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private Connection connection;
    private Statement statement;
    //private ArrayList<EntityManager> managers;

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
        tableCreator.createNodeTable(this.connection);
        tableCreator.createFoodRequestTable();
        tableCreator.createFoodOrderTable();
        tableCreator.createFoodLogTable();
    }

    public void executeUpdateOnDatabase(String sql) {

    }

    public ResultSet executeQueryOnDatabase(String sql) {
        return null;
    }

}
