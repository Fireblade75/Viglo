package nl.saxion.viglo.component;

import java.util.ArrayList;

public class ProgramComponent implements VigloComponent {

    private VigloComponent classComponent;

    public void setClassComponent(VigloComponent classComponent) {
        this.classComponent = classComponent;
    }

    @Override
    public ArrayList<String> generateCode() {
        return new ArrayList<>(classComponent.generateCode());
    }
}
