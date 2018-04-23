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
            case "char":
                return "I";
            default:
                return type;
        }
    }

    public static String toRawType(String type) {
        switch (type) {
            case "void":
                return "void";
            case "int":
                return "int";
            case "long":
                return "long";
            case "float":
                return "float";
            case "double":
                return "double";
            case "bool":
                return "int";
            case "char":
                return "int";
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

    public static boolean canFreelyCast(String currentType, String targetType) {
        if((!isNumber(currentType) || !isNumber(targetType)) || currentType.equals(targetType)) {
            return false;
        }
        if(targetType.equals("double")) {
            return true;
        } else if(targetType.equals("float") && !currentType.equals("double")) {
            return true;
        } else if(targetType.equals("long") && currentType.equals("int")) {
            return true;
        }
        return false;
    }
}
