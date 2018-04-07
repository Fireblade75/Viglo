package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Value;

import java.util.ArrayList;

public class DoubleLiteral extends ExprComponent {

    private double doubleValue;

    public DoubleLiteral(double doubleValue) {
        super(new Value("double", false));
        this.doubleValue = doubleValue;
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tldc2_w " + doubleValue);
        return asm;
    }
}
