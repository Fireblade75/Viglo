package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.type.StdType;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class NotExprComponent extends ExprComponent {

    private final Scope scope;
    private ExprComponent childExpr;

    public NotExprComponent(ExprComponent childExpr, Scope scope) {
        super(new Value(StdType.BOOL, childExpr.isStatic()));
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
        String endLabel = scope.getLabel();
        String falseLabel = scope.getLabel();
        asm.add("\tifne " + falseLabel);
        asm.add("\ticonst_1");
        asm.add("\tgoto " + endLabel);
        asm.add(falseLabel + ":");
        asm.add("\ticonst_0");
        asm.add(endLabel + ":");
        return asm;
    }
}

/*
    ifne #_false
    iconst_1
    goto #_end
    #_false
    iconst_0
    #_end
 */