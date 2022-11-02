package nl.firepy.viglo.component;

import nl.firepy.viglo.compiler.scope.Scope;
import nl.firepy.viglo.component.expr.FunctionExpression;
import nl.firepy.viglo.type.TypeConverter;
import nl.firepy.viglo.type.Value;

import java.util.ArrayList;

public class FunctionComponent implements VigloComponent {

    private final FunctionExpression function;
    private final Scope scope;
    private final boolean isStatic;
    private String name;
    private boolean constructor = false;

    public FunctionComponent(String name, FunctionExpression function, Scope scope, boolean isStatic) {
        this.name = name;
        this.function = function;
        this.scope = scope;
        this.isStatic = isStatic;
    }


    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        BlockComponent blockComponent = function.getBlock();

        int locals = function.getScope().getLocals();
        String returnType = TypeConverter.rawToJasmin(function.getReturnType());
        String paramTypes = function.getParamList().asJasminList();
        String functionName = constructor ? "<init>" : name;

        String functionLine = ".method " + (isStatic ? "static public " : "public ");

        asm.add(functionLine + functionName + "(" + paramTypes + ")" + returnType);
        asm.add("\t.limit stack 10");
        asm.add("\t.limit locals " + locals);

        if(constructor) {
            asm.add("\taload_0");
            asm.add("\tinvokenonvirtual java/lang/Object/<init>()V");
        }

        asm.addAll(blockComponent.generateCode());

        if (function.getReturnType().equals("void")) {
            asm.add("\treturn");
        }
        asm.add(".end method\n");
        return asm;
    }


    public String getName() {
        return name;
    }

    public void setConstructor() {
        this.constructor = true;
    }

    public boolean hasParams() {
        return !function.getParamList().isEmpty();
    }

    public String getReturnType() {
        return function.getReturnType();
    }

    public ParamList getParamTypes() {
        return function.getParamList();
    }
}
