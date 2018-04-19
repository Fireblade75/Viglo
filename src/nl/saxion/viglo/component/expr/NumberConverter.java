package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.type.StdType;
import nl.saxion.viglo.type.TypeConverter;

import java.util.ArrayList;

public class NumberConverter {

    private Scope scope;

    public NumberConverter(Scope scope) {
        this.scope = scope;
    }

    public ArrayList<String> loadNumber(ExprComponent leftExpr, ExprComponent rightExpr, String resultType) {
        ArrayList<String> asm = new ArrayList<>();
        if (resultType.equals("int")) {
            asm.addAll(loadInt(leftExpr));
            asm.addAll(loadInt(rightExpr));
        } else if (resultType.equals("long")) {
            asm.addAll(loadLong(leftExpr));
            asm.addAll(loadLong(rightExpr));
        } else if (resultType.equals("float")) {
            asm.addAll(loadFloat(leftExpr));
            asm.addAll(loadFloat(rightExpr));
        } else if (resultType.equals("double")) {
            asm.addAll(loadDouble(leftExpr));
            asm.addAll(loadDouble(rightExpr));
        }
        return asm;
    }

    public static ArrayList<String> loadExpr(ExprComponent expr, String expType, String assignType) {
        ArrayList<String> asm = expr.generateCode();
        if(!assignType.equals(expType)) {
            if(TypeConverter.isNumber(assignType) && TypeConverter.isNumber(expType)) {
                asm.add(NumberConverter.castNumber(expType, assignType));
            } else {
                throw new UnsupportedOperationException("Cannot cast non numerical values");
            }
        }
        return asm;
    }

    public static String castNumber(String currentType, String resultType) {
        if(resultType.equals(StdType.INT)) {
            switch (currentType) {
                case StdType.LONG: return "\tl2i";
                case StdType.FLOAT: return "\tf2i";
                case StdType.DOUBLE: return "\td2i";
            }
        } else if(resultType.equals(StdType.LONG)) {
            switch (currentType) {
                case StdType.INT: return "\ti2l";
                case StdType.FLOAT: return "\tf2l";
                case StdType.DOUBLE: return "\td2l";
            }
        } else if(resultType.equals(StdType.FLOAT)) {
            switch (currentType) {
                case StdType.INT: return "\ti2f";
                case StdType.LONG: return "\tl2f";
                case StdType.DOUBLE: return "\td2f";
            }
        } else if(resultType.equals(StdType.DOUBLE)) {
            switch (currentType) {
                case StdType.INT: return "\ti2d";
                case StdType.LONG: return "\tl2d";
                case StdType.FLOAT: return "\tf2d";
            }
        }
        throw new UnsupportedOperationException("Could not cast number");
    }

    public ArrayList<String> loadInt(ExprComponent expr) {
        return new ArrayList<>(expr.generateCode());
    }

    public ArrayList<String> loadLong(ExprComponent expr) {
        if (expr.getValue().getType(scope).equals("int")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\ti2l");
            return asm;
        } else {
            return new ArrayList<>(expr.generateCode());
        }
    }

    public ArrayList<String> loadFloat(ExprComponent expr) {
        if (expr.getValue().getType(scope).equals("int")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\ti2f");
            return asm;
        }
        if (expr.getValue().getType(scope).equals("long")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\tl2f");
            return asm;
        } else {
            return new ArrayList<>(expr.generateCode());
        }
    }

    public ArrayList<String> loadDouble(ExprComponent expr) {
        if (expr.getValue().getType(scope).equals("int")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\ti2d");
            return asm;
        }
        if (expr.getValue().getType(scope).equals("long")) {
            ArrayList<String> asm = new ArrayList<>(expr.generateCode());
            asm.add("\tl2d");
            return asm;
        }
        if (expr.getValue().getType(scope).equals("float")) {
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
