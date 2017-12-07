import foodRequest.FoodRequest;
import javafx.application.Application;
import javafx.stage.Stage;

public class Runner extends Application{
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FoodRequest foodRequest = new FoodRequest();
        foodRequest.run(0,0,1900,1000,null,null,null);
    }
}
