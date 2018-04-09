package nl.saxion.viglo;

import nl.saxion.viglo.type.ClassHeader;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {

    private Scope parent = null;
    private HashMap<String, Value> valueMap = new HashMap<>();
    private ArrayList<String> labelList = new ArrayList<>();
    private int childLocals = 0;
    private int labelCounter = 0;
    private String className;
    private ClassHeader classHeader;

    public Scope(String className, ClassHeader classHeader) {
        this.className = className;
        this.classHeader = classHeader;
    }

    public Scope(Scope scope) {
        parent = scope;
        className = scope.className;
        labelList.addAll(scope.labelList);
    }

    public void addValue(String label, Value value) {
        valueMap.put(label, value);
        labelList.add(label);
    }

    public Value getValue(String label) {
        if(valueMap.containsKey(label)) {
            return valueMap.get(label);
        } else if(parent!=null) {
            return parent.getValue(label);
        } else {
            return null;
        }
    }

    public int getIndex(String label) {
        if(labelList.contains(label)) {
            return labelList.indexOf(label) + 1;
        }
        return -1;
    }

    public Scope getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public int getLocals() {
        return valueMap.size() + childLocals + 1;
    }

    public void addChildLocals(int locals) {
        this.childLocals += locals;
    }

    public void close() {
        if(hasParent()) {
            parent.addChildLocals(valueMap.size());
        }
    }

    public String getLabel () {
        if(hasParent()) {
            return parent.getLabel();
        } else {
            return  "#_" + (labelCounter++);
        }
    }

    private ClassHeader getClassHeader() {
        if(hasParent()) {
            return parent.getClassHeader();
        } else {
            return classHeader;
        }
    }

    public FunctionDescriptor getFunction(String name) {
        return getClassHeader().getFunction(name);
    }
}
