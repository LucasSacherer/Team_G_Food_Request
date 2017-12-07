import foodRequest.FoodRequest;
import javafx.application.Application;
import javafx.stage.Stage;

public class Runner extends Application{
    public static void main(String[] args){
        launch();
    }

    private void callRun(){
        FoodRequest foodRequest = new FoodRequest();
        try {
            foodRequest.run(0, 0, 100, 100, null, null, null);
        }catch (Exception ex){
            System.out.println("Error went all the way to the top!");
            ex.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FoodRequest foodRequest = new FoodRequest();
        foodRequest.run(0,0,0,0,null,null,null);
    }
}
