package nl.saxion.viglo.type;

public class FieldValue extends Value {

    private String type;
    private String className;
    private String label;

    public FieldValue(String label, String type, String className) {
        super(type, false);
        this.className = className;
        this.label = label;
        this.type = type;
    }

    public String getPath() {
        return "viglo/" + className + "/" + label + " " + TypeConverter.rawToJasmin(type);
    }
}
