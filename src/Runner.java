public class Runner {
    public static void main(String[] args){
        Runner runner = new Runner();
        runner.callRun();
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
}
