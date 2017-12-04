import Database.DatabaseGargoyle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FoodRequest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load the Database
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        databaseGargoyle.createTables();
        databaseGargoyle.destroyConnection();

        //Load the UI
        Parent root = FXMLLoader.load(getClass().getResource("Boundary/fxml/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public void run(int xcoord, int ycoord, int windwoWidth, int windowLength, String cssPath, String destNodeID,
                    String originNode) throws ServiceException {
        launch();
    }
}
