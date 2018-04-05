package nl.saxion.viglo.component.expr;

import java.util.ArrayList;

public class NotExprComponent extends ExprComponent {

    private ExprComponent childExpr;

    public NotExprComponent(ExprComponent childExpr) {
        this.childExpr = childExpr;
    }

    @Override
    boolean isStatic() {
        return childExpr.isStatic();
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>(childExpr.generateCode());
        asm.add("ineg");
        return null;
    }
}
