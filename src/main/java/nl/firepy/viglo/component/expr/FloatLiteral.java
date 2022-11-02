package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.type.StdType;
import nl.firepy.viglo.type.Value;

import java.util.ArrayList;

public class FloatLiteral extends ExprComponent {

    float floatValue;

    public FloatLiteral(float floatValue) {
        super(new Value(StdType.FLOAT, false));
        this.floatValue = floatValue;
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tldc " + floatValue);
        return asm;
    }
}
