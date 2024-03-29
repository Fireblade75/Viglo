package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.type.StdType;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class DoubleLiteral extends ExprComponent {

    private double doubleValue;

    public DoubleLiteral(double doubleValue) {
        super(new Value(StdType.DOUBLE, false));
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
