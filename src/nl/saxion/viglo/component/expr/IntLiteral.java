package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class IntLiteral extends ExprComponent {

    private int intValue;

    public IntLiteral(int intValue) {
        super(new Value("int", false));
        this.intValue = intValue;
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tbipush " + intValue);
        return asm;
    }
}
