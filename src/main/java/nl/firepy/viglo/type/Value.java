package nl.firepy.viglo.type;

import nl.firepy.viglo.compiler.scope.Scope;

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
     * @param scope
     */
    public String getType(Scope scope) {
        return type;
    }

    /**
     * Get the type of the variable used in the Java Bytecode
     * For example, booleans are represented by integers in the bytecode
     * @return the name of the type
     */
    public String getRawType(Scope scope) {
        if(type.equals("bool") || type.equals("char")) {
            return "int";
        } else {
            return type;
        }
    }

    public int getStackSize() {
        return StdType.getStackSize(type);
    }

    public boolean isConstant(){
        return constant;
    }
}
