package enumerate;

public enum Gender {
    MALE, FEMALE, UNDEFINED;
    public String getValue(){
        switch (this){
            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
            case UNDEFINED:
                return "Don't want to reveal";
        }
        return "";
    }
    public int getInt() {
            switch (this){
                case MALE:
                    return 1;
                case FEMALE:
                    return 2;
                case UNDEFINED:
                    return 3;
            }
            return -1;
    }
    public static Gender getValueByInt(int value) {
        switch (value){
            case 1:
                return Gender.MALE;
            case 2:
                return Gender.FEMALE;
            case 3:
                return Gender.UNDEFINED;
        }
        return null;
    }
}
