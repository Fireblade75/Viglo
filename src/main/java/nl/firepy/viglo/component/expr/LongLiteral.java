package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.type.StdType;
import nl.firepy.viglo.type.Value;

import java.util.ArrayList;

public class LongLiteral extends ExprComponent {

    long longValue;

    public LongLiteral(long longValue) {
        super(new Value(StdType.LONG, false));
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
