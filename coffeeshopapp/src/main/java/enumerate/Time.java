package enumerate;

public enum Time {
    MORNING, NOON, EVENING, ALL_DAY;
    public String getValue(){
        switch (this){
            case MORNING:
                return "Morning";
            case NOON:
                return "Noon";
            case EVENING:
                return "Evening";
            case ALL_DAY:
                return "All day";
        }
        return "";
    }
    public int getInt() {
        switch (this){
            case MORNING:
                return 1;
            case NOON:
                return 2;
            case EVENING:
                return 3;
            case ALL_DAY:
                return 4;
        }
        return -1;
    }
    public static Time getValueByInt(int value) {
        switch (value){
            case 1:
                return Time.MORNING;
            case 2:
                return Time.NOON;
            case 3:
                return Time.EVENING;
            case 4:
                return Time.ALL_DAY;
        }
        return null;
    }
}
