package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.component.BlockComponent;
import nl.saxion.viglo.component.ParamList;
import nl.saxion.viglo.component.VigloComponent;

import java.util.ArrayList;

public class FunctionExpression implements VigloComponent {

    private ParamList paramList;
    private BlockComponent block;
    private String returnType;
    private Scope scope;

    public FunctionExpression(ParamList paramList, BlockComponent block, String returnType, Scope scope) {
        this.paramList = paramList;
        this.block = block;
        this.returnType = returnType;
        this.scope = scope;
    }

    @Override
    public ArrayList<String> generateCode() {
        return null;
    }

    public Scope getScope() {
        return scope;
    }

    public BlockComponent getBlock() {
        return block;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public String getReturnType() {
        return returnType;
    }
}
