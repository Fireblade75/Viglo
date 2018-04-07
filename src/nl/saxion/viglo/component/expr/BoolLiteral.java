package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Value;

import java.util.ArrayList;

public class BoolLiteral extends ExprComponent {

    private boolean boolValue;

    public BoolLiteral(boolean boolValue) {
        super(new Value("bool", false));
        this.boolValue = boolValue;
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\ticonst_" + (boolValue ? "1" : "0"));
        return asm;
    }
}
