package nl.saxion.viglo.component;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.component.expr.*;
import nl.saxion.viglo.type.FieldValue;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class AssignStatement implements VigloComponent {

    private ExprComponent expr;
    private Scope scope;
    private int localId;
    private FieldValue fieldValue;

    public AssignStatement(ExprComponent expr, Scope scope, String label) {
        this.expr = expr;
        this.scope = scope;
        Value value = scope.getValue(label);
        if(value instanceof FieldValue) {
            this.fieldValue = (FieldValue) value;
        } else{
            this.localId = scope.getIndex(label);
        }
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        String expType = expr.getValue().getRawType(scope);

        if(fieldValue != null) {
            asm.add("\taload_0");
            asm.addAll(expr.generateCode());
            asm.add("\tputfield " + fieldValue.getPath());
        } else {
            asm.addAll(expr.generateCode());
            switch (expType) {
                case "int":
                    asm.add("\tistore " + localId);
                    break;
                case "long":
                    asm.add("\tlstore " + localId);
                    break;
                case "float":
                    asm.add("\tfstore " + localId);
                    break;
                case "double":
                    asm.add("\tdstore " + localId);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return asm;
    }
}
