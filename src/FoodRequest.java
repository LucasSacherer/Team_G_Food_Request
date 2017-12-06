import Boundary.GodController;
import Database.DatabaseGargoyle;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FoodRequest extends Application {

    public FoodRequest(){}
    @Override
    public void start(Stage primaryStage) throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        databaseGargoyle.createTables();
        databaseGargoyle.destroyConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("boundary/fxml/foodRequestHub.fxml"));
        GodController godController = new GodController();
        loader.setController(godController);
        godController.initialize();
        Parent root = loader.load();
        Scene scene = new Scene(root,800,600);
        primaryStage.setTitle("B&W Path Finding");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/boundary/APIStyle.css").toExternalForm());
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void run(int xcoord, int ycoord, int windwoWidth, int windowLength, String cssPath, String destNodeID,
                    String originNode) throws ServiceException {
        launch();
    }
}

