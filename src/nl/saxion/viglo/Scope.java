package nl.saxion.viglo;

import nl.saxion.viglo.type.ClassHeader;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {

    private GlobalScope globalScope;
    private Scope parent = null;
    private HashMap<String, Value> valueMap = new HashMap<>();
    private ArrayList<String> labelList = new ArrayList<>();
    private int childLocals = 0;
    private int labelCounter = 0;

    public Scope(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public Scope(Scope scope) {
        parent = scope;
        labelList.addAll(scope.labelList);
    }

    public void addValue(String label, Value value) {
        valueMap.put(label, value);
        labelList.add(label);
    }

    public Value getValue(String label) {
        if (valueMap.containsKey(label)) {
            return valueMap.get(label);
        } else if (parent != null) {
            return parent.getValue(label);
        } else {
            return null;
        }
    }

    public int getIndex(String label) {
        if (labelList.contains(label)) {
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

    /**
     * Close the scope and inform te parent
     */
    public void close() {
        if (hasParent()) {
            parent.addChildLocals(valueMap.size());
        }
    }

    /**
     * Get a unique label for this scope
     * @return te unique label
     */
    public String getLabel() {
        if (hasParent()) {
            return parent.getLabel();
        } else {
            return "#_" + (labelCounter++);
        }
    }

    /**
     * Get a unique label for this scope that contains a name
     * @param name the name of the label
     * @return te unique label
     */
    public String getLabel(String name) {
        if (hasParent()) {
            return parent.getLabel(name);
        } else {
            return "#" + name + "_" + (labelCounter++);
        }
    }

    private ClassHeader getClassHeader() {
        if(globalScope != null) {
            return globalScope.getClassHeader();
        } else {
            return parent.getClassHeader();
        }
    }

    public FunctionDescriptor getFunction(String name) {
        return getClassHeader().getFunction(name);
    }
}
