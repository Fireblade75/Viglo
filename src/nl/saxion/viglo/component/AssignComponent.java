package nl.saxion.viglo.component;

import nl.saxion.viglo.component.expr.ExprComponent;

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
        asm.add("\tistore "+localId);
        return asm;
    }
}
