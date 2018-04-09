package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.type.TypeConverter;

import java.util.ArrayList;

public class NumberConverter {

    private Scope scope;

    public NumberConverter(Scope scope) {
        this.scope = scope;
    }

    public ArrayList<String> loadNumber(ExprComponent leftExpr, ExprComponent rightExpr, String resultType) {
        ArrayList<String> asm = new ArrayList<>();
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
        return asm;
    }

    public ArrayList<String> loadInt(ExprComponent expr) {
        return new ArrayList<>(expr.generateCode());
    }

    public ArrayList<String> loadLong(ExprComponent expr) {
        if(expr.getValue().getType(scope).equals("int")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\ti2l");
            return asm;
        } else {
            return new ArrayList<>(expr.generateCode());
        }
    }

    public ArrayList<String> loadFloat(ExprComponent expr) {
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

    public ArrayList<String> loadDouble(ExprComponent expr) {
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

    public static String superType(ExprComponent leftExpr, ExprComponent rightExpr, Scope scope) {
        String leftType = leftExpr.getValue().getType(scope);
        String rightType = rightExpr.getValue().getType(scope);
        return TypeConverter.combineNumbers(leftType, rightType);
    }
}
