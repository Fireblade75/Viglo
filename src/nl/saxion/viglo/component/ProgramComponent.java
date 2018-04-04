package nl.saxion.viglo.component;

import java.util.ArrayList;

public class ProgramComponent implements VigloComponent {

    private VigloComponent classComponent;

    public void setClassComponent(VigloComponent classComponent) {
        this.classComponent = classComponent;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>(classComponent.generateCode());
        return asm;
    }
}
