package nl.saxion.viglo;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {

    private Scope parent = null;
    private HashMap<String, Value> valueMap = new HashMap<>();
    private ArrayList<String> labelList = new ArrayList<>();

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
}
