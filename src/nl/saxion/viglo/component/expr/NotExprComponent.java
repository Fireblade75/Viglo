package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.Value;

import java.util.ArrayList;

public class NotExprComponent extends ExprComponent {

    private final Scope scope;
    private ExprComponent childExpr;

    public NotExprComponent(ExprComponent childExpr, Scope scope) {
        super(new Value("bool", childExpr.isStatic()));
        this.childExpr = childExpr;
        this.scope = scope;
    }

    @Override
    boolean isStatic() {
        return childExpr.isStatic();
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>(childExpr.generateCode());
        String trueLabel = scope.getLabel();
        String falseLabel = scope.getLabel();
        asm.add("\tifne " + falseLabel);
        asm.add("\ticonst_1");
        asm.add("\tgoto " + trueLabel);
        asm.add(falseLabel + ":");
        asm.add("\ticonst_0");
        asm.add(trueLabel + ":");
        return asm;
    }
}
