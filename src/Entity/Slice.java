package Entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Slice extends RecursiveTreeObject<Slice> {
    
        private String name;
        private int qty;
        private double percent;

        public Slice(String n, int q, double p){
            this.name = n;
            this.qty = q;
            this.percent = p;
        }

        public String getName(){
            return name;
        }

        public int getQty(){
            return qty;
        }

        public double getPercent(){
            return percent;
        }

}
