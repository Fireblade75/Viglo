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
        code.add(".class public DefaultCode");
        code.add(".super java/lang/Object");

        return new ArrayList<String>();
    }
}
