package nl.saxion.viglo.component;

import nl.saxion.viglo.component.expr.*;

import java.util.ArrayList;

public class EchoComponent implements VigloComponent {

    private ExprComponent expr;

    public EchoComponent(ExprComponent expr) {
        this.expr = expr;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tgetstatic java/lang/System/out Ljava/io/PrintStream;");
        asm.addAll(expr.generateCode());
        switch (expr.getValue().getType()) {
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
