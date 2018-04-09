package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.component.BlockComponent;
import nl.saxion.viglo.component.ParamList;
import nl.saxion.viglo.component.VigloComponent;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class FunctionExpression implements VigloComponent {

    private ParamList paramList;
    private BlockComponent block;
    private String returnType;
    private Scope scope;

    public FunctionExpression(ParamList paramList, String returnType, Scope scope) {
        this.paramList = paramList;
        this.returnType = returnType;
        this.scope = scope;

        for(ParamList.ParamListItem param : paramList.items()) {
            scope.addValue(param.getName(), new Value(param.getType(), false));
        }
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

    public void setBlock(BlockComponent block) {
        this.block = block;
    }
}
