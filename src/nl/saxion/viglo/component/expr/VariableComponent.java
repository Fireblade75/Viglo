package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Value;
import nl.saxion.viglo.component.Variable;
import nl.saxion.viglo.component.VigloComponent;

import java.util.ArrayList;

public class VariableComponent extends ExprComponent {

    private Value value;
    private int localId;

    public VariableComponent(Value value, int localId) {
        super(value);
        this.value = value;
        this.localId = localId;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        switch (value.getRawType()) {
            case "int":
                asm.add("\tiload " + localId);
                break;
            case "long":
                asm.add("\tlload " + localId);
                break;
            case "float":
                asm.add("\tfload " + localId);
                break;
            case "double":
                asm.add("\tdload " + localId);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return asm;
    }

    @Override
    boolean isStatic() {
        return value.isConstant();
    }
}
