package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Value;

import java.util.ArrayList;

public class LongLiteral extends ExprComponent {

    long longValue;

    public LongLiteral(long longValue) {
        super(new Value("int", false));
        this.longValue = longValue;
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tldc2_w " + longValue);
        return asm;
    }
}
