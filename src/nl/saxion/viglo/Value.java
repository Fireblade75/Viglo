package nl.saxion.viglo;

public class Value {
    private String type;
    private boolean constant;

    public Value(String type, boolean constant) {
        this.type = type;
        this.constant = constant;
    }

    /**
     * Get the type of the variable in the code itself
     * @return the name of the type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the type of the variable used in the Java Bytecode
     * For example, booleans are represented by integers in the bytecode
     * @return the name of the type
     */
    public String getRawType() {
        if(type.equals("bool") || type.equals("char")) {
            return "int";
        } else {
            return type;
        }
    }

    public boolean isConstant(){
        return constant;
    }
}
