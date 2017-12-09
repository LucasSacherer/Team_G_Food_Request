package Entity2;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class TypeData extends RecursiveTreeObject<TypeData> {


        private String type;
        private int amount;

        public TypeData(String type, int n) {
            this.type = type;
            this.amount = n;
        }

        public String getType() {
            return type;
        }

        public Integer getAmount() {
            return amount;
        }


}
