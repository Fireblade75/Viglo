package nl.saxion.viglo.component.expr;

import nl.saxion.viglo.component.VigloComponent;

import java.util.ArrayList;

public class EmptyComponent implements VigloComponent {
    @Override
    public ArrayList<String> generateCode() {
        return new ArrayList<>();
    }
}
