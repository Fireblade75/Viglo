package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.type.TypeConverter;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class MathExpression extends ExprComponent {

    private ExprComponent leftExpr, rightExpr;
    private Scope scope;
    private MathExpressionType type;

    private enum MathExpressionType { PLUS, MINUS, MULTIPLY, DIVIDE }

    public MathExpression(ExprComponent leftExpr, ExprComponent rightExpr, String symbol, Scope scope) {
        super(new Value(NumberConverter.superType(leftExpr, rightExpr, scope), true));
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
        this.scope = scope;
        if(symbol.equals("+")) {
            type = MathExpressionType.PLUS;
        } else if(symbol.equals("-")) {
            type = MathExpressionType.MINUS;
        } else if(symbol.equals("*")) {
            type = MathExpressionType.MULTIPLY;
        } else if(symbol.equals("/")) {
            type = MathExpressionType.DIVIDE;
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

        String resultType = getValue().getType(scope);
        NumberConverter converter = new NumberConverter(scope);
        asm.addAll(converter.loadNumber(leftExpr, rightExpr, resultType));

        String opperator = "\t";
        if(resultType.equals("int")) {
            opperator += "i";
        } else if(resultType.equals("long")) {
            opperator += "l";
        } else if(resultType.equals("float")) {
            opperator += "f";
        } else if(resultType.equals("double")) {
            opperator += "d";
        }

        if(type == MathExpressionType.PLUS) {
            opperator += "add";
        } else if(type == MathExpressionType.MINUS) {
            opperator += " sub";
        } else if(type == MathExpressionType.MULTIPLY) {
            opperator += "mul";
        } else if(type == MathExpressionType.DIVIDE) {
            opperator += "div";
        } else {
            throw new UnsupportedOperationException();
        }

        asm.add(opperator);

        return asm;
    }
}