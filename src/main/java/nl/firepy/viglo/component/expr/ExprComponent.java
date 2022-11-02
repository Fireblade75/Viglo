package nl.firepy.viglo.component.expr;

import nl.firepy.viglo.type.Value;
import nl.firepy.viglo.component.VigloComponent;

public abstract class ExprComponent implements VigloComponent {

    private Value value;

    public ExprComponent(Value value) {
        this.value = value;
    }

    abstract boolean isStatic();

    public Value getValue() {
        return value;
    }
}
