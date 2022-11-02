package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.type.StdType;
import nl.firepy.viglo.type.Value;

import java.util.ArrayList;

public class NullLiteral extends ExprComponent {
    public NullLiteral() {
        super(new Value(StdType.OBJECT, false));
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\taconst_null");
        return asm;
    }
}
