package nl.saxion.viglo;

import nl.saxion.viglo.component.FunctionComponent;
import nl.saxion.viglo.component.ParamList;
import nl.saxion.viglo.type.TypeConverter;

import java.util.ArrayList;

public class FunctionDescriptor {
    private String name;
    private String className;
    private String returnType;
    private ArrayList<String> paramTypes = new ArrayList<>();

    public FunctionDescriptor(FunctionComponent function, String className) {
        this.returnType = function.getReturnType();
        this.name = function.getName();
        this.className = className;
        ParamList paramList = function.getParamTypes();
        for(ParamList.ParamListItem item : paramList.items()) {
            paramTypes.add(item.getType());
        }
    }

    private String getParamString() {
        StringBuilder sb = new StringBuilder();
        for(String param : paramTypes) {
            sb.append(TypeConverter.rawToJasmin(param));
        }
        return sb.toString();
    }

    public String getJasminCall() {
        return className + "/" + name + "(" + getParamString() + ")" + TypeConverter.rawToJasmin(returnType);
    }

    public String getReturnType() {
        return returnType;
    }
}
