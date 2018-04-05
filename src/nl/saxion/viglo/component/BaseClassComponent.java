package nl.saxion.viglo.component;

import java.util.ArrayList;

public abstract class BaseClassComponent implements VigloComponent {

    protected static ArrayList<String> getDefaultMain(String className) {
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
