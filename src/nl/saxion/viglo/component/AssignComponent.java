package nl.saxion.viglo.component;

import nl.saxion.viglo.component.expr.*;

import java.util.ArrayList;

public class AssignComponent implements VigloComponent {

    private ExprComponent expr;
    private int localId;

    public AssignComponent(ExprComponent expr, int localId) {
        this.expr = expr;
        this.localId = localId;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.addAll(expr.generateCode());
        if(expr instanceof IntLiteral || expr instanceof BoolLiteral || expr instanceof CharLiteral) {
            asm.add("\tistore " + localId);
        } else if(expr instanceof LongLiteral) {
            asm.add("\tlstore " + localId);
        } else if(expr instanceof FloatLiteral) {
            asm.add("\tfstore " + localId);
        } else if(expr instanceof DoubleLiteral) {
            asm.add("\tdstore " + localId);
        }
        return asm;
    }
}
