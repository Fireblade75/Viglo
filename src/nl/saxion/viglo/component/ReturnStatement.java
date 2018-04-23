package nl.saxion.viglo.component;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.component.expr.ExprComponent;
import nl.saxion.viglo.component.expr.NumberConverter;
import nl.saxion.viglo.type.TypeConverter;

import java.util.ArrayList;

public class ReturnStatement implements VigloComponent {

    private ExprComponent expr;
    private Scope scope;
    private String functionType;

    public ReturnStatement(ExprComponent expr, Scope scope, String functionType) {
        this.expr = expr;
        this.scope = scope;
        this.functionType = functionType;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        String expType = expr.getValue().getType(scope);
        asm.addAll(NumberConverter.loadExpr(expr, expType, functionType));
        switch (TypeConverter.toRawType(functionType)) {
            case "int":
                asm.add("\tireturn");
                break;
            case "long":
                asm.add("\tlreturn");
                break;
            case "float":
                asm.add("\tfreturn");
                break;
            case "double":
                asm.add("\tdreturn");
                break;
            default:
                asm.add("\treturn");
        }
        return asm;
    }
}
