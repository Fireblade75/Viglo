package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.Value;

import java.util.ArrayList;

public class LogicExpression extends ExprComponent {

    private final Scope scope;
    private ExprComponent leftExpr, rightExpr;
    private LogicExpressionType type;

    private enum LogicExpressionType { AND, OR }

    public LogicExpression(ExprComponent leftExpr, ExprComponent rightExpr, String symbol, Scope scope) {
        super(new Value("bool", true));
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
        this.scope = scope;
        if(symbol.equals("and")) {
            type = LogicExpressionType.AND;
        } else if(symbol.equals("or")) {
            type = LogicExpressionType.OR;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    boolean isStatic() {
        return leftExpr.isStatic() && rightExpr.isStatic();
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();

        String trueLabel = scope.getLabel();
        String falseLabel = scope.getLabel();

        if(type == LogicExpressionType.AND) {
            asm.addAll(leftExpr.generateCode());
            asm.add("\tifeq " + falseLabel);
            asm.addAll(rightExpr.generateCode());
            asm.add("\tifeq " + falseLabel);
            asm.add("\ticonst_1");
            asm.add("\tgoto " + trueLabel);
            asm.add(falseLabel + ":");
            asm.add("\ticonst_0");
            asm.add(trueLabel + ":");
        } else {
            String endLabel = scope.getLabel();
            asm.addAll(leftExpr.generateCode());
            asm.add("\tifne  " + trueLabel);
            asm.addAll(rightExpr.generateCode());
            asm.add("\tifeq  " + falseLabel);
            asm.add(trueLabel + ":");
            asm.add("\ticonst_1");
            asm.add("\tgoto  " + endLabel);
            asm.add(falseLabel + ":");
            asm.add("\ticonst_0");
            asm.add(endLabel + ":");
        }
        return asm;
    }
}