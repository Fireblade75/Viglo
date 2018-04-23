package nl.saxion.viglo;

import nl.saxion.viglo.type.ClassHeader;
import nl.saxion.viglo.type.FieldValue;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scope {

    private GlobalScope globalScope;
    private Scope parent = null;
    private HashMap<String, Value> valueMap = new HashMap<>();
    private ArrayList<String> labelList = new ArrayList<>();
    private int childLocals = 0;
    private int labelCounter = 0;
    private ArrayList<Scope> children = new ArrayList<>();
    private String returnType;

    public Scope(GlobalScope globalScope, boolean isStatic) {
        this.globalScope = globalScope;
        if(!isStatic) {
            addValue("this", new Value("object", true));
        }
    }

    public Scope(Scope scope) {
        parent = scope;
        parent.addChild(this);
        labelList.addAll(scope.labelList);
    }

    public void addValue(String label, Value value) {
        if(!hasValueDirect(label)) {
            valueMap.put(label, value);
            labelList.add(label);
            if(value.getStackSize() == 2) {
                labelList.add("");
            }
        } else {
            throw new RuntimeException("Cannot add '"+label+"' to this scope, variable already defined");
        }
    }

    public Value getValue(String label) {
        if (valueMap.containsKey(label)) {
            return valueMap.get(label);
        } else if (parent != null) {
            return parent.getValue(label);
        } else {
            String fieldType = getClassHeader().getField(label);
            if(fieldType != null) {
                String className = getClassHeader().getClassName();
                return new FieldValue(label, fieldType, className);
            }
            return null;
        }
    }

    /**
     * Checks whether the label is already defined in this scope
     * If true the variable can not be added
     * @param label the label of the variable
     * @return true if the variable already exists
     */
    public boolean hasValueDirect(String label) {
        return valueMap.containsKey(label);
    }

    public int getIndex(String label) {
        if (labelList.contains(label)) {
            return labelList.indexOf(label);
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
        int locals = parent == null ? 1 : 0;
        for(Map.Entry<String, Value> erntry : valueMap.entrySet()) {
            locals += erntry.getValue().getStackSize();
        }
        for(Scope child : children) {
            locals += child.getLocals();
        }
        return locals;
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

    public boolean isField(String label) {
        return getClassHeader().getField(label) != null;
    }

    /**
     * Add a child to this scope
     * @param scope the scope to add
     */
    private void addChild(Scope scope) {
        children.add(scope);
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnType() {
        if (hasParent()) {
            return parent.getReturnType();
        } else {
            return returnType;
        }
    }
}
