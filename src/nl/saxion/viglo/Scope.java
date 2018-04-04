package nl.saxion.viglo;

import java.util.HashMap;

public class Scope {

    private Scope parent = null;
    private HashMap<String, Value> valueMap = new HashMap();

    public Scope() {

    }

    public Scope(Scope scope) {
        parent = scope;
    }

    public void addValue(String label, Value value) {
        valueMap.put(label, value);
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

    public Scope getParent() {
        return parent;
    }
}
