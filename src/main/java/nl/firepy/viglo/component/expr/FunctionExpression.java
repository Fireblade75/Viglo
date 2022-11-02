package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.compiler.scope.Scope;
import nl.firepy.viglo.component.BlockComponent;
import nl.firepy.viglo.component.ParamList;
import nl.firepy.viglo.component.VigloComponent;
import nl.firepy.viglo.type.Value;

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
