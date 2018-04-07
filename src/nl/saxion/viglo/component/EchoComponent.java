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
        if(expr instanceof IntLiteral) {
            asm.add("\tinvokevirtual java/io/PrintStream.println(I)V");
        } else if(expr instanceof BoolLiteral) {
            asm.add("\tinvokevirtual java/io/PrintStream.println(Z)V");
        } else if(expr instanceof CharLiteral) {
            asm.add("\tinvokevirtual java/io/PrintStream.println(C)V");
        } else if(expr instanceof LongLiteral) {
            asm.add("\tinvokevirtual java/io/PrintStream.println(J)V");
        } else if(expr instanceof FloatLiteral) {
            asm.add("\tinvokevirtual java/io/PrintStream.println(F)V");
        } else if(expr instanceof DoubleLiteral) {
            asm.add("\tinvokevirtual java/io/PrintStream.println(D)V");
        }
        return asm;
    }
}
