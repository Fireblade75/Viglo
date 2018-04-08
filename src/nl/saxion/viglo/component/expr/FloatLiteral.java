package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class FloatLiteral extends ExprComponent {

    float floatValue;

    public FloatLiteral(float floatValue) {
        super(new Value("float", false));
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
