package nl.saxion.viglo.component;

import nl.saxion.viglo.type.ClassHeader;
import nl.saxion.viglo.type.TypeConverter;

import java.util.ArrayList;
import java.util.Map;

public class ClassComponent extends BaseClassComponent {
    private String className;
    private RootBlockComponent block;
    private ClassHeader classHeader;

    public ClassComponent(String className, RootBlockComponent block, ClassHeader classHeader) {
        this.className = className;
        this.block = block;
        this.classHeader = classHeader;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add(".class public "+className);
        asm.add(".super java/lang/Object\n");
        if(classHeader.countFields() > 0) {
            for (Map.Entry<String, String> field : classHeader.getFields().entrySet()) {
                String type = TypeConverter.rawToJasmin(field.getValue());
                asm.add(".field public " + field.getKey() + " " + type);
            }
            asm.add("");
        }

        asm.addAll(block.generateCode());
        if(!block.hasSimpleConstructor()) {
            asm.addAll(getDefaultConstructor());
        }
        asm.addAll(getDefaultMain(className));

        return asm;
    }

    private ArrayList<String> getDefaultConstructor() {
        ArrayList<String> code = new ArrayList<>();
        code.add("; standard initializer");
        code.add(".method public <init>()V");
        code.add("\taload_0");
        code.add("\tinvokenonvirtual java/lang/Object/<init>()V");
        code.add("\treturn");
        code.add(".end method\n");
        return code;
    }
}
