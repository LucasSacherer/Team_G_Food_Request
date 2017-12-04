import Boundary.GodController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FoodRequest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("Boundary/fxml/foodRequestHub.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boundary/fxml/foodRequestHub.fxml"));
        GodController godController = new GodController();
        loader.setController(godController);
        Parent root = loader.load();
        primaryStage.setTitle("B&W Path Finding");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public void run(int xcoord, int ycoord, int windwoWidth, int windowLength, String cssPath, String destNodeID,
                    String originNode) throws ServiceException {
        launch();
    }
}

