package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.type.StdType;
import nl.firepy.viglo.type.Value;

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
