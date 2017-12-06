package Controller;

import Database.DatabaseGargoyle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CSVController {
    DatabaseGargoyle databaseGargoyle;

    public CSVController(DatabaseGargoyle dbG) {
        this.databaseGargoyle = dbG;
    }

    /**
     * Takes a ResultSet and writes it to a CSV file
     * @param rs
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveCSVFile(ResultSet rs, String filePath) throws SQLException, IOException {
        File out = new File(filePath);
        FileWriter writer = new FileWriter(out);
        final CSVPrinter printer = CSVFormat.DEFAULT.withHeader(rs).print(writer);
        printer.printRecords(rs);
        writer.close();
    }

    /**
     * Gets all MenuItems and calls saveCSVFile with the result sets
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveMenuItems(String filePath) throws SQLException, IOException{
        databaseGargoyle.createConnection();
        ResultSet rsMenuItem = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM MENUITEMS");
        saveCSVFile(rsMenuItem, filePath);
        rsMenuItem.close();
        databaseGargoyle.destroyConnection();
    }

    /**
     * Gets all Worker Logs and calls saveCSVFile with the result set
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveWorkerLogs(String filePath) throws SQLException, IOException{
        databaseGargoyle.createConnection();
        ResultSet rsWorkerLog = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM WORKERLOG");
        saveCSVFile(rsWorkerLog, filePath);
        rsWorkerLog.close();
        databaseGargoyle.destroyConnection();
    }

    /**
     * Gets all AdminLogs and calls saveCSVFile with the result set
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveFoodLogs(String filePath) throws SQLException, IOException{
        databaseGargoyle.createConnection();
        ResultSet rsFoodLog = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODLOG");
        saveCSVFile(rsFoodLog, filePath);
        rsFoodLog.close();
        databaseGargoyle.destroyConnection();
    }
}
