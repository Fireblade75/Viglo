package nl.firepy.viglo.component;

import nl.firepy.viglo.compiler.scope.Scope;
import nl.firepy.viglo.component.expr.*;

import java.util.ArrayList;

public class EchoStatement implements VigloComponent {

    private ExprComponent expr;
    private Scope scope;

    public EchoStatement(ExprComponent expr, Scope scope) {
        this.expr = expr;
        this.scope = scope;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tgetstatic java/lang/System/out Ljava/io/PrintStream;");
        asm.addAll(expr.generateCode());
        switch (expr.getValue().getType(scope)) {
            case "int":
                asm.add("\tinvokevirtual java/io/PrintStream.println(I)V");
                break;
            case "bool":
                asm.add("\tinvokevirtual java/io/PrintStream.println(Z)V");
                break;
            case "char":
                asm.add("\tinvokevirtual java/io/PrintStream.println(C)V");
                break;
            case "long":
                asm.add("\tinvokevirtual java/io/PrintStream.println(J)V");
                break;
            case "float":
                asm.add("\tinvokevirtual java/io/PrintStream.println(F)V");
                break;
            case "double":
                asm.add("\tinvokevirtual java/io/PrintStream.println(D)V");
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return asm;
    }
}
