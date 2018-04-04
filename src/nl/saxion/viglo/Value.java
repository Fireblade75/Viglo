package nl.saxion.viglo;

public class Value {
    private Object value;
    private String type;
    private boolean constant;

    public Value(Object value, String type, boolean constant) {
        this.value = value;
        this.type = type;
    }
}
