package nl.saxion.viglo.component;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.component.expr.ExprComponent;

import java.util.ArrayList;

public class ReturnStatement implements VigloComponent {

    private ExprComponent expr;
    private Scope scope;

    public ReturnStatement(ExprComponent expr, Scope scope) {
        this.expr = expr;
        this.scope = scope;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>(expr.generateCode());
        switch (expr.getValue().getRawType(scope)) {
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
