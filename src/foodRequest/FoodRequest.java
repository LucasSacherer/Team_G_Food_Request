package foodRequest;

import Boundary2.GodController;
import Database2.DatabaseGargoyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FoodRequest {

    public FoodRequest(){}
    public void thing(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        databaseGargoyle.createTables();
        databaseGargoyle.destroyConnection();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary2/fxml/foodRequestHub.fxml"));
        GodController godController = new GodController();
        loader.setController(godController);
        godController.initialize();
        Parent root;
        try{
            root = loader.load();
        }catch (Exception e){
            System.out.println("failed to load the file");
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root,windowWidth,windowLength);
        primaryStage.setX(xcoord);
        primaryStage.setY(ycoord);
        primaryStage.setTitle("B&W Path Finding");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        primaryStage.show();
    }

    public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String destNodeID,
                    String originNode) throws ServiceException {
        String cssFile = cssPath;
        if (xcoord < 0 || ycoord < 0 || windowWidth < 0 || windowLength < 0){
            throw new ServiceException("There cannot be a negative value in the parameter of run().");
        }
        if (xcoord > windowWidth){
            throw new ServiceException("Xcoord is out of the bounds of the window.");
        }
        if (ycoord > windowLength){
            throw new ServiceException("Ycoord is out of the bounds of the window.");
        }
        if (cssPath == null){
            cssFile = "/Boundary2/APIStyle.css";
        }
        thing(xcoord, ycoord, windowWidth, windowLength, cssFile);
    }
}

