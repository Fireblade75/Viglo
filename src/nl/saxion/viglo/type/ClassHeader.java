package nl.saxion.viglo.type;

import nl.saxion.viglo.FunctionDescriptor;

import java.util.HashMap;

public class ClassHeader {
    private String className;
    private HashMap<String, FunctionDescriptor> functions = new HashMap<>();

    public ClassHeader(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void addFunction(FunctionDescriptor function) {
        functions.put(function.getName(), function);
    }

    public FunctionDescriptor getFunction(String name) {
        return functions.get(name);
    }
}
