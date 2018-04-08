package nl.saxion.viglo.component;

import java.util.ArrayList;

public class ClassComponent extends BaseClassComponent {
    private String className;
    private RootBlockComponent block;

    public ClassComponent(String className, RootBlockComponent block) {
        this.className = className;
        this.block = block;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> code = new ArrayList<>();
        code.add(".class public "+className);
        code.add(".super java/lang/Object\n");
        code.addAll(block.generateCode());
        if(!block.hasSimpleConstructor()) {
            code.addAll(getDefaultConstructor());
        }
        code.addAll(getDefaultMain(className));

        return code;
    }

    private ArrayList<String> getDefaultConstructor() {
        ArrayList<String> code = new ArrayList<>();
        code.add("; standard initializer");
        code.add(".method public <init>()V");
        code.add("\taload_0");
        code.add("\tinvokenonvirtual java/lang/Object/<init>()V");
        code.add("\treturn");
        code.add(".end method\n");
        return code;
    }
}
