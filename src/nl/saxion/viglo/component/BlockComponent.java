package nl.saxion.viglo.component;

import nl.saxion.viglo.Scope;

import java.util.ArrayList;

public class BlockComponent implements VigloComponent {

    private ArrayList<VigloComponent> vigloComponents = new ArrayList<>();
    private Scope scope;

    public BlockComponent(Scope scope) {
        this.scope = scope;
    }

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

    public Scope getScope() {
        return scope;
    }
}
