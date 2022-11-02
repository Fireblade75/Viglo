package nl.firepy.viglo.compiler.scope;

import nl.firepy.viglo.type.ClassHeader;

public class GlobalScope {

    private String className;
    private ClassHeader classHeader;


    public GlobalScope(String className, ClassHeader classHeader) {
        super();
        this.className = className;
        this.classHeader = classHeader;
    }

    public ClassHeader getClassHeader() {
        return classHeader;
    }
}
