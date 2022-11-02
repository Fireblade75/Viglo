package nl.firepy.viglo.component;

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
        int locals = blockComponent.getScope().getLocals() + 1;
        ArrayList<String> asm = new ArrayList<>();
        asm.add("; struct initializer");
        asm.add(".method public <init>()V");
        asm.add("\t.limit stack 10");
        asm.add("\t.limit locals " + locals);
        asm.add("\taload_0");
        asm.add("\tinvokenonvirtual java/lang/Object/<init>()V");

        asm.addAll(blockComponent.generateCode());

        asm.add("\treturn");
        asm.add(".end method\n");
        return asm;
    }
}
