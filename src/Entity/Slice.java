package Entity;

public class Slice {
    
        private String name;
        private int qty;
        private int percent;

        public Slice(String n, int q, int p){
            this.name = n;
            this.qty = q;
            this.percent = p;
        }

        public String getName(){
            return name;
        }

}
