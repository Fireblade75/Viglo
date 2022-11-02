package nl.firepy.viglo.component;

import nl.firepy.viglo.compiler.CompilerException;
import nl.firepy.viglo.compiler.scope.Scope;
import nl.firepy.viglo.component.expr.*;
import nl.firepy.viglo.type.FieldValue;
import nl.firepy.viglo.type.TypeConverter;
import nl.firepy.viglo.type.Value;

import java.util.ArrayList;

public class AssignStatement implements VigloComponent {

    private ExprComponent expr;
    private Scope scope;
    private int localId;
    private FieldValue fieldValue;
    private String assignType;

    public AssignStatement(ExprComponent expr, Scope scope, String label) {
        this.expr = expr;
        this.scope = scope;
        Value value = scope.getValue(label);
        assignType = value.getRawType(scope);
        if(value instanceof FieldValue) {
            this.fieldValue = (FieldValue) value;
        } else{
            this.localId = scope.getIndex(label);
        }
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        String expType = expr.getValue().getRawType(scope);

        if(fieldValue != null) {
            asm.add("\taload_0");
            asm.addAll(NumberConverter.loadExpr(expr, expType, assignType));
            asm.add("\tputfield " + fieldValue.getPath());
        } else {
            asm.addAll(NumberConverter.loadExpr(expr, expType, assignType));
            switch (assignType) {
                case "int":
                    asm.add("\tistore " + localId);
                    break;
                case "long":
                    asm.add("\tlstore " + localId);
                    break;
                case "float":
                    asm.add("\tfstore " + localId);
                    break;
                case "double":
                    asm.add("\tdstore " + localId);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return asm;
    }
}
