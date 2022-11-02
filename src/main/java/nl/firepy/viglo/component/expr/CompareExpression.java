package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.compiler.scope.Scope;
import nl.firepy.viglo.type.StdType;
import nl.firepy.viglo.type.Value;

import java.util.ArrayList;

public class CompareExpression extends ExprComponent {

    private ExprComponent leftExpr, rightExpr;
    private Scope scope;
    private EqualityExpressionType type;

    private enum EqualityExpressionType {LESS, LESS_OR_EQUAL, GREATER, GREATER_OR_EQUAL}

    public CompareExpression(ExprComponent leftExpr, ExprComponent rightExpr, String symbol, Scope scope) {
        super(new Value(StdType.BOOL, true));
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
        this.scope = scope;
        if (symbol.equals(">")) {
            type = EqualityExpressionType.GREATER;
        } else if (symbol.equals(">=")) {
            type = EqualityExpressionType.GREATER_OR_EQUAL;
        } else if (symbol.equals("<")) {
            type = EqualityExpressionType.LESS;
        } else if (symbol.equals("<=")) {
            type = EqualityExpressionType.LESS_OR_EQUAL;
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
        NumberConverter converter = new NumberConverter(scope);
        String commonType = NumberConverter.superType(leftExpr, rightExpr, scope);
        asm.addAll(converter.loadNumber(leftExpr, rightExpr, commonType));
        String falseLabel = scope.getLabel("comp_false");
        String endLabel = scope.getLabel("comp_end");
        if (commonType.equals("int")) {
            asm.add(compareInt(falseLabel));
        } else if (commonType.equals("long")) {
            asm.addAll(compareLong(falseLabel));
        } else if (commonType.equals("float")) {
            asm.addAll(compareFloat(falseLabel));
        } else if (commonType.equals("double")) {
            asm.addAll(compareDouble(falseLabel));
        }
        asm.add("\ticonst_1");
        asm.add("\tgoto " + endLabel);
        asm.add(falseLabel+":");
        asm.add("\ticonst_0");
        asm.add(endLabel+":");

        return asm;
    }

    private String compareInt(String falseLabel) {
        switch (type) {
            case LESS:
                return "\tif_icmpge " + falseLabel;
            case LESS_OR_EQUAL:
                return "\tif_icmpgt " + falseLabel;
            case GREATER:
                return "\tif_icmple " + falseLabel;
            case GREATER_OR_EQUAL:
                return "\tif_icmplt " + falseLabel;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private ArrayList<String> compareLong(String falseLabel) {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tlcmp");
        switch (type) {
            case LESS:
               asm.add("\tifge " + falseLabel);
                break;
            case LESS_OR_EQUAL:
                asm.add("\tifgt " + falseLabel);
                break;
            case GREATER:
                asm.add("\tifle " + falseLabel);
                break;
            case GREATER_OR_EQUAL:
                asm.add("\tiflt " + falseLabel);
                break;
        }
        return asm;
    }

    private ArrayList<String> compareFloat(String falseLabel) {
        ArrayList<String> asm = new ArrayList<>();

        switch (type) {
            case LESS:
                asm.add("\tfcmpg");
                asm.add("\tifge " + falseLabel);
                break;
            case LESS_OR_EQUAL:
                asm.add("\tfcmpg");
                asm.add("\tifgt " + falseLabel);
                break;
            case GREATER:
                asm.add("\tfcmpl");
                asm.add("\tifle " + falseLabel);
                break;
            case GREATER_OR_EQUAL:
                asm.add("\tfcmpl");
                asm.add("\tiflt " + falseLabel);
                break;
        }
        return asm;
    }

    private ArrayList<String> compareDouble(String falseLabel) {
        ArrayList<String> asm = new ArrayList<>();

        switch (type) {
            case LESS:
                asm.add("\tdcmpg");
                asm.add("\tifge " + falseLabel);
                break;
            case LESS_OR_EQUAL:
                asm.add("\tdcmpg");
                asm.add("\tifgt " + falseLabel);
                break;
            case GREATER:
                asm.add("\tdcmpl");
                asm.add("\tifle " + falseLabel);
                break;
            case GREATER_OR_EQUAL:
                asm.add("\tdcmpl");
                asm.add("\tiflt " + falseLabel);
                break;
        }
        return asm;
    }
}
