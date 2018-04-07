package nl.saxion.viglo;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {

    private Scope parent = null;
    private HashMap<String, Value> valueMap = new HashMap<>();
    private ArrayList<String> labelList = new ArrayList<>();
    private int childLocals = 0;
    private int labelCounter = 0;

    public Scope() {

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
}
