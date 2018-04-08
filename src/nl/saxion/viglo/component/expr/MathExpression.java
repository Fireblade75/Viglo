package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class MathExpression extends ExprComponent {

    private ExprComponent leftExpr;
    private ExprComponent rightExpr;
    private Scope scope;
    private MathExpressionType type;

    private enum MathExpressionType { PLUS, MINUS, MULTIPLY, DIVIDE }

    public MathExpression(ExprComponent leftExpr, ExprComponent rightExpr, String symbol, Scope scope) {
        super(new Value(superType(leftExpr, rightExpr, scope), true));
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
        if(resultType.equals("int")) {
            asm.addAll(loadInt(leftExpr));
            asm.addAll(loadInt(rightExpr));
        } else if(resultType.equals("long")) {
            asm.addAll(loadLong(leftExpr));
            asm.addAll(loadLong(rightExpr));
        } else if(resultType.equals("float")) {
            asm.addAll(loadFloat(leftExpr));
            asm.addAll(loadFloat(rightExpr));
        } else if(resultType.equals("double")) {
            asm.addAll(loadDouble(leftExpr));
            asm.addAll(loadDouble(rightExpr));
        }

        if(type == MathExpressionType.PLUS) {
            if(resultType.equals("int")) {
                asm.add("\tiadd");
            } else if(resultType.equals("long")) {
                asm.add("\tladd");
            } else if(resultType.equals("float")) {
                asm.add("\tfadd");
            } else if(resultType.equals("double")) {
                asm.add("\tdadd");
            }
        } else if(type == MathExpressionType.MINUS) {
            if(resultType.equals("int")) {
                asm.add("\tisub");
            } else if(resultType.equals("long")) {
                asm.add("\tlsub");
            } else if(resultType.equals("float")) {
                asm.add("\tfsub");
            } else if(resultType.equals("double")) {
                asm.add("\tdsub");
            }
        } else if(type == MathExpressionType.MULTIPLY) {
            if(resultType.equals("int")) {
                asm.add("\timul");
            } else if(resultType.equals("long")) {
                asm.add("\tlmul");
            } else if(resultType.equals("float")) {
                asm.add("\tfmul");
            } else if(resultType.equals("double")) {
                asm.add("\tdmul");
            }
        } else if(type == MathExpressionType.DIVIDE) {
            if(resultType.equals("int")) {
                asm.add("\tidiv");
            } else if(resultType.equals("long")) {
                asm.add("\tldiv");
            } else if(resultType.equals("float")) {
                asm.add("\tfdiv");
            } else if(resultType.equals("double")) {
                asm.add("\tddiv");
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return asm;
    }

    private ArrayList<String> loadInt(ExprComponent expr) {
        return new ArrayList<>(expr.generateCode());
    }

    private ArrayList<String> loadLong(ExprComponent expr) {
        if(expr.getValue().getType(scope).equals("int")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\ti2l");
            return asm;
        } else {
            return new ArrayList<>(expr.generateCode());
        }
    }

    private ArrayList<String> loadFloat(ExprComponent expr) {
        if(expr.getValue().getType(scope).equals("int")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\ti2f");
            return asm;
        } if(expr.getValue().getType(scope).equals("long")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\tl2f");
            return asm;
        } else {
            return new ArrayList<>(expr.generateCode());
        }
    }

    private ArrayList<String> loadDouble(ExprComponent expr) {
        if(expr.getValue().getType(scope).equals("int")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\ti2d");
            return asm;
        } if(expr.getValue().getType(scope).equals("long")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\tl2d");
            return asm;
        } if(expr.getValue().getType(scope).equals("float")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\tf2d");
            return asm;
        } else {
            return new ArrayList<>(expr.generateCode());
        }
    }

    private static String superType(ExprComponent leftExpr, ExprComponent rightExpr, Scope scope) {
        String leftType = leftExpr.getValue().getType(scope);
        String rightType = rightExpr.getValue().getType(scope);
        if(leftType.equals("double") || rightType.equals("double")) {
            return "double";
        } else if(leftType.equals("float") || rightType.equals("float")) {
            return "float";
        } else if(leftType.equals("long") || rightType.equals("long")) {
            return "long";
        } else {
            return "int";
        }
    }
}