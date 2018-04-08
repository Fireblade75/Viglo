package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.type.Value;
import nl.saxion.viglo.component.VigloComponent;

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
