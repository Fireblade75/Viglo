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
            case "bool":
                return "I";
            default:
                return type;
        }
    }

    public static boolean isNumber(String type) {
        return type.equals("double") || type.equals("float") || type.equals("long") || type.equals("int");
    }

    public static String combineNumbers(String type1, String type2) {
        if(type1.equals("double") || type2.equals("double")) {
            return "double";
        } else if(type1.equals("float") || type2.equals("float")) {
            return "float";
        } else if(type1.equals("long") || type2.equals("long")) {
            return "long";
        } else {
            return "int";
        }
    }
}
