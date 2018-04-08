package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.FunctionDescriptor;
import nl.saxion.viglo.Scope;
import nl.saxion.viglo.type.FunctionValue;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class FunctionCall extends ExprComponent {

    private Scope scope;
    private String name;
    private ArrayList<ExprComponent> exprComponents;

    public FunctionCall(Scope scope, String name, ArrayList<ExprComponent> exprComponents) {
        super(new FunctionValue(name));
        this.scope = scope;
        this.name = name;
        this.exprComponents = exprComponents;
    }

    @Override
    boolean isStatic() {
        return false;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        FunctionDescriptor function = scope.getFunction(name);
        asm.add("\taload_0");
        for(ExprComponent exprComponent : exprComponents) {
            asm.addAll(exprComponent.generateCode());
        }
        asm.add("\tinvokevirtual " + function.getJasminCall());
        return asm;
    }
}
