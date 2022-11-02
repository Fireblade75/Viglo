package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.compiler.FunctionDescriptor;
import nl.firepy.viglo.compiler.scope.Scope;
import nl.firepy.viglo.type.FunctionValue;

import java.util.ArrayList;

public class FunctionCall extends ExprComponent {

    private Scope scope;
    private String name;
    private ArrayList<ExprComponent> exprList;

    public FunctionCall(Scope scope, String name, ArrayList<ExprComponent> exprList) {
        super(new FunctionValue(name));
        this.scope = scope;
        this.name = name;
        this.exprList = exprList;
    }

    @Override
    boolean isStatic() {
        return false;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        FunctionDescriptor function = scope.getFunction(name);
        ArrayList<String> paramTypes = function.getParamTypes();
        asm.add("\taload_0");
        for(int i = 0; i < exprList.size(); i++) {
            ExprComponent expr = exprList.get(i);
            String exprType = expr.getValue().getType(scope);
            String paramType = paramTypes.get(i);

            asm.addAll(NumberConverter.loadExpr(expr, exprType, paramType));
        }
        if(function.isStatic()) {
            asm.add("\tinvokestatic " + function.getJasminCall());
        } else {
            asm.add("\tinvokevirtual " + function.getJasminCall());
        }
        return asm;
    }
}
