package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.Value;
import nl.saxion.viglo.component.VigloComponent;

import java.util.ArrayList;

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
