package nl.saxion.viglo.component;

import java.util.ArrayList;

public class BlockComponent implements VigloComponent {

    private ArrayList<VigloComponent> vigloComponents = new ArrayList<>();

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        for(VigloComponent vigloComponent : vigloComponents) {
            asm.addAll(vigloComponent.generateCode());
        }
        return asm;
    }

    public void add(VigloComponent component) {
        vigloComponents.add(component);
    }
}
