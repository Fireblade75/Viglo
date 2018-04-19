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

    public FunctionDescriptor(String name, String className, ArrayList<String> paramTypes, String returnType) {
        this.name = name;
        this.className = className;
        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }

    public FunctionDescriptor(FunctionComponent function, String className) {
        this.returnType = function.getReturnType();
        this.name = function.getName();
        this.className = className;
        ParamList paramList = function.getParamTypes();
        for(ParamList.ParamListItem item : paramList.items()) {
            paramTypes.add(item.getType());
        }
    }

    /**
     * Create a String representing the raw parameters,
     * this is used to represent the parameters in Jasmin
     * @return the parameters for the Jasmin file
     */
    private String getParamString() {
        StringBuilder sb = new StringBuilder();
        for(String param : paramTypes) {
            sb.append(TypeConverter.rawToJasmin(param));
        }
        return sb.toString();
    }

    /**
     * Get the Jasmin name of the function
     * @return the name of the function ready to be called in a Jasmin source file
     */
    public String getJasminCall() {
        return "viglo/" + className + "/" + name + "(" + getParamString() + ")" + TypeConverter.rawToJasmin(returnType);
    }

    /**
     * Get the return type of the function
     * @return the return type of the function
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Get the name of the function
     * @return the name of the function
     */
    public String getName() {
        return name;
    }

    /**
     * Get all parameters as a list of types
     * @return the list of paramters
     */
    public ArrayList<String> getParamTypes() {
        return paramTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("function (");
        for (int i = 0; i < paramTypes.size(); i++) {
            sb.append(paramTypes.get(i));
            if(i != paramTypes.size() - 1) {
                sb.append(",");
            }
        }
        return sb.append(")").toString();
    }
}
