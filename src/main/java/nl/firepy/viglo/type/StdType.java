package nl.firepy.viglo.type;

public class StdType {
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String CHAR = "char";
    public static final String BOOL = "bool";
    public static final String FUNCTION = "function";
    public static final String OBJECT = "object";

    public static int getStackSize(String type) {
        return (type.equals(DOUBLE) || type.equals(LONG)) ? 2 : 1;
    }
}
