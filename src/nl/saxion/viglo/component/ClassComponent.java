package nl.saxion.viglo.component;

import java.util.ArrayList;

public class ClassComponent implements VigloComponent {
    private String className;

    public ClassComponent(String className) {
        this.className = className;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> code = new ArrayList<>();
        code.add(".class public "+className);
        code.add(".super java/lang/Object\n");
        code.addAll(getDefaultConstructor());
        code.addAll(getDefaultMain());

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

    private ArrayList<String> getDefaultMain() {
        ArrayList<String> code = new ArrayList<>();
        code.add("; standard main");
        code.add(".method public static main([Ljava/lang/String;)V");
        code.add("\t.limit stack 10");
        code.add("\t.limit locals 10");
        code.add("\tnew "+className);
        code.add("\tdup");
        code.add("\tinvokespecial "+className + "/<init>()V");
        code.add("\treturn");
        code.add(".end method\n");

        return code;
    }
}
