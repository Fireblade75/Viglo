package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class CharLiteral extends ExprComponent {

    private char charValue;

    public CharLiteral(char charValue) {
        super(new Value("char", false));
        this.charValue = charValue;
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tbipush " + (int)(charValue));
        return asm;
    }
}
