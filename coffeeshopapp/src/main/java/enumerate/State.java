package enumerate;

public enum State {
    IN_STOCK, OUT_OF_STOCK;
    public String getValue(){
        switch (this){
            case IN_STOCK:
                return "In stock";
            case OUT_OF_STOCK:
                return "Out of stock";
        }
        return "";
    }
    public int getInt() {
        switch (this){
            case IN_STOCK:
                return 1;
            case OUT_OF_STOCK:
                return 2;
        }
        return -1;
    }
    public static State getValueByInt(int value) {
        switch (value){
            case 1:
                return State.IN_STOCK;
            case 2:
                return State.OUT_OF_STOCK;
        }
        return null;
    }
}
