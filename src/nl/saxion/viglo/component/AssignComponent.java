package nl.saxion.viglo.component;

import nl.saxion.viglo.component.expr.*;

import java.util.ArrayList;

public class AssignComponent implements VigloComponent {

    private ExprComponent expr;
    private int localId;

    public AssignComponent(ExprComponent expr, int localId) {
        this.expr = expr;
        this.localId = localId;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.addAll(expr.generateCode());
        switch (expr.getValue().getRawType()) {
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
        return asm;
    }
}
