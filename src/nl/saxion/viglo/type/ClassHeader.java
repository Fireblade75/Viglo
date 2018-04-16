package nl.saxion.viglo.type;

import nl.saxion.viglo.FunctionDescriptor;

import java.util.HashMap;

public class ClassHeader {
    private String className;
    private HashMap<String, FunctionDescriptor> functions = new HashMap<>();
    private HashMap<String, String> fields = new HashMap<>();

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

    public void addField(String name, String type) {
        fields.put(name, type);
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public int countFields() {
        return fields.size();
    }

    public String getField(String label) {
        return fields.get(label);
    }
}
