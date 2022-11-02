package nl.firepy.viglo.component;

import nl.firepy.viglo.component.VigloComponent;

import java.util.ArrayList;

public class Variable implements VigloComponent {
    private String label;
    private String type;

    public Variable(String label, String type) {
        this.label = label;
        this.type = type;
    }

    @Override
    public ArrayList<String> generateCode() {
        return null;
    }
}
