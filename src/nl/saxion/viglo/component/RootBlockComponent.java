package nl.saxion.viglo.component;

import nl.saxion.viglo.Scope;

public class RootBlockComponent extends BlockComponent {
    private String className;
    private boolean simpleConstructor = false;

    public RootBlockComponent(Scope scope, String className) {
        super(scope);
        this.className = className;
    }

    public void add(VigloComponent component) {
        super.add(component);
        if(component instanceof FunctionComponent) {
            FunctionComponent function = (FunctionComponent) component;
            if(function.getName().equals(className)) {
                function.setConstructor();
                if(!function.hasParams()) {
                    simpleConstructor = true;
                }
            } else {
                getScope().addFunction(function);
            }
        }
    }

    public boolean hasSimpleConstructor() {
        return simpleConstructor;
    }
}
