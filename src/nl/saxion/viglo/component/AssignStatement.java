package nl.saxion.viglo.component;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.component.expr.*;
import nl.saxion.viglo.type.FunctionValue;

import java.util.ArrayList;

public class AssignStatement implements VigloComponent {

    private ExprComponent expr;
    private Scope scope;
    private int localId;

    public AssignStatement(ExprComponent expr, Scope scope, int localId) {
        this.expr = expr;
        this.scope = scope;
        this.localId = localId;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.addAll(expr.generateCode());
        String expType = expr.getValue().getRawType(scope);

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
        return asm;
    }
}
