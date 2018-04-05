package nl.saxion.viglo.component;

import java.util.ArrayList;

public class StructComponent extends BaseClassComponent {

    private String className;
    private BlockComponent blockComponent;

    public StructComponent(String className, BlockComponent blockComponent) {
        this.className = className;
        this.blockComponent = blockComponent;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> code = new ArrayList<>();
        code.add(".class public "+className);
        code.add(".super java/lang/Object\n");
        code.addAll(createConstructor());
        code.addAll(getDefaultMain(className));
        return code;
    }

    private ArrayList<String> createConstructor() {
        ArrayList<String> code = new ArrayList<>();
        code.add("; struct initializer");
        code.add(".method public <init>()V");
        code.add("\taload_0");
        code.add("\tinvokenonvirtual java/lang/Object/<init>()V");

        code.addAll(blockComponent.generateCode());

        code.add("\treturn");
        code.add(".end method\n");
        return code;
    }
}
