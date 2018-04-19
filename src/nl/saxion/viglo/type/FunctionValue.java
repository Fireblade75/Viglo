package nl.saxion.viglo.type;

import nl.saxion.viglo.Scope;

public class FunctionValue extends Value {
    private String functionName;

    public FunctionValue(String functionName) {
        super(StdType.FUNCTION, false);
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    @Override
    public String getType(Scope scope) {
        return scope.getFunction(functionName).getReturnType();
    }

    @Override
    public String getRawType(Scope scope) {
        String type = scope.getFunction(functionName).getReturnType();
        if(type.equals("bool") || type.equals("char")) {
            return "int";
        } else {
            return type;
        }
    }
}
