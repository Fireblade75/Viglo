package nl.firepy.viglo.component;

import nl.firepy.viglo.compiler.scope.Scope;

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
            if(function.getName().equals(getSimpleClassName(className))) {
                function.setConstructor();
                if(!function.hasParams()) {
                    simpleConstructor = true;
                }
            }
        }
    }

    public String getSimpleClassName(String className) {
        String[] path = className.split("/");
        return path[path.length -1];
    }

    public boolean hasSimpleConstructor() {
        return simpleConstructor;
    }
}
