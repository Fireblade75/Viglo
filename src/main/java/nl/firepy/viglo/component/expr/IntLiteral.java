package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.type.StdType;
import nl.firepy.viglo.type.Value;

import java.util.ArrayList;

public class IntLiteral extends ExprComponent {

    private int intValue;

    public IntLiteral(int intValue) {
        super(new Value(StdType.INT, false));
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
