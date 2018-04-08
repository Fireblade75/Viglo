package nl.saxion.viglo.type;

public class TypeConverter {

    public static String rawToJasmin(String type) {
        switch (type) {
            case "void":
                return "V";
            case "int":
                return "I";
            case "long":
                return "J";
            case "float":
                return "F";
            case "double":
                return "D";
            default:
                return type;
        }
    }
}
