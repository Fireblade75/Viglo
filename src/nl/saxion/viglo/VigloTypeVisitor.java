package nl.saxion.viglo;

import nl.saxion.viglo.component.VigloComponent;
import nl.saxion.viglo.component.expr.NumberConverter;
import nl.saxion.viglo.type.ClassHeader;
import nl.saxion.viglo.type.TypeConverter;
import nl.saxion.viglo.type.Value;

import java.util.ArrayList;

public class VigloTypeVisitor extends VigloBaseVisitor<String> {

    private Scope scope;
    private GlobalScope globalScope;
    private ClassHeader classHeader;
    private String className;

    public void setClassHeader(ClassHeader classHeader) {
        this.classHeader = classHeader;
    }

    @Override
    public String visitProgram(VigloParser.ProgramContext ctx) {
        if(ctx.classBlock() != null) {
            visit(ctx.classBlock());
        } else {
            visit(ctx.structBlock());
        }
        return null;
    }

    @Override
    public String visitClassBlock(VigloParser.ClassBlockContext ctx) {
        className = "viglo/" + ctx.NAME().getText();
        visit(ctx.rootBlock());
        return "class";
    }

    @Override
    public String visitStructBlock(VigloParser.StructBlockContext ctx) {
        className = "viglo/" + ctx.NAME().getText();
        globalScope = new GlobalScope(className, classHeader);
        visit(ctx.block());
        return "struct";
    }

    @Override
    public String visitRootBlock(VigloParser.RootBlockContext ctx) {
        globalScope = new GlobalScope(className, classHeader);
        for (VigloParser.RootStatementContext rootStatement : ctx.rootStatement()) {
            if(rootStatement.declareFunction() != null) {
                visit(rootStatement);
            }
        }
        return "block";
    }

    @Override
    public String visitDeclareFunction(VigloParser.DeclareFunctionContext ctx) {
        boolean isStatic = ctx.STATIC() != null;
        scope = new Scope(globalScope, isStatic);
        visit(ctx.functionStatement());
        scope.close();
        return "function";
    }

    @Override
    public String visitFunctionStatement(VigloParser.FunctionStatementContext ctx) {
        for(VigloParser.ParamItemContext paramItem : ctx.paramList().paramItem()) {
            visit(paramItem);
        }

        visit(ctx.block());
        return "function";
    }

    @Override
    public String visitParamItem(VigloParser.ParamItemContext ctx) {
        Value value = new Value(ctx.type().getText(), false);
        scope.addValue(ctx.NAME().getText(), value);
        return ctx.type().getText();
    }

    @Override
    public String visitBlock(VigloParser.BlockContext ctx) {
        for(VigloParser.StatementContext statment : ctx.statement()) {
            visit(statment);
        }
        return "block";
    }

    @Override
    public String visitDeclareInferStatement(VigloParser.DeclareInferStatementContext ctx) {
        String type = visit(ctx.exp());
        boolean constant = ctx.CONST() != null;
        scope.addValue(ctx.NAME().getText(), new Value(type, constant));
        return visit(ctx.exp());
    }

    @Override
    public String visitAssignStatement(VigloParser.AssignStatementContext ctx) {
        String label = ctx.variable().getText();
        Value value = scope.getValue(label);
        if(value == null) {
            throw new CompilerException(ctx, "Variable '" + label + "' is not defined");
        }

        String valueType = value.getType(scope);
        String expType = visit(ctx.exp());
        if (!valueType.equals(expType)) {
            if(!TypeConverter.canFreelyCast(expType, valueType)) {
                throw new CompilerException(ctx, "Cannot assign " + expType + " to variable '" + label + "' of type " + valueType);
            }
        }
        return expType;
    }

    @Override
    public String visitMathExpression(VigloParser.MathExpressionContext ctx) {
        return visitTwoNumberExpression(ctx.left, ctx.right);
    }

    @Override
    public String visitCompareExpression(VigloParser.CompareExpressionContext ctx) {
        return visitTwoNumberExpression(ctx.left, ctx.right);
    }

    private String visitTwoNumberExpression(VigloParser.ExpContext left, VigloParser.ExpContext right) {
        String leftType = visit(left);
        String rightType = visit(right);
        if(!TypeConverter.isNumber(leftType)) {
            throw new CompilerException(left, "Expected a number, found "+leftType);
        }
        if(!TypeConverter.isNumber(rightType)) {
            throw new CompilerException(right, "Expected a number, found "+rightType);
        }
        return TypeConverter.combineNumbers(leftType, rightType);
    }

    @Override
    public String visitNotExpression(VigloParser.NotExpressionContext ctx) {
        String expType = visit(ctx.exp());
        if(!expType.equals("bool")) {
            throw new CompilerException(ctx.exp(), "Expected a bool, found "+ expType);
        }
        return "bool";
    }

    @Override
    public String visitLogicExpression(VigloParser.LogicExpressionContext ctx) {
        String leftType = visit(ctx.left);
        String rightType = visit(ctx.right);
        if(!leftType.equals("bool")) {
            throw new CompilerException(ctx.left, "Expected a bool, found "+leftType);
        }
        if(!rightType.equals("bool")) {
            throw new CompilerException(ctx.right, "Expected a bool, found "+rightType);
        }
        return "bool";
    }

    @Override
    public String visitChainExp(VigloParser.ChainExpContext ctx) {
        if(ctx.chainPart().size()>0) {
            throw new CompilerException(ctx, "Chained expressions are not supported, for now.");
        } else {
            return visit(ctx.chainTail());
        }
    }

    @Override
    public String visitVariable(VigloParser.VariableContext ctx) {
        Value value = scope.getValue(ctx.NAME().getText());
        if(value == null) {
            throw new CompilerException(ctx, "Variable '" + ctx.NAME().getText() + "' is not defined");
        }
        return value.getType(scope);
    }

    @Override
    public String visitFunctionCall(VigloParser.FunctionCallContext ctx) {
        FunctionDescriptor function = scope.getFunction(ctx.NAME().getText());
        ArrayList<String> functionParams = function.getParamTypes();
        if(ctx.exp().size() != functionParams.size()) {
            throw new CompilerException(ctx, "Parameters don't match " + function.toString());
        } else {
            for (int i = 0; i < functionParams.size(); i++) {
                String callParam = visit(ctx.exp(i));
                if(!callParam.equals(functionParams.get(i))) {
                    if(!TypeConverter.canFreelyCast(callParam, functionParams.get(i))) {
                        String message = "Expected " + functionParams.get(i) + " found " + callParam;
                        throw new CompilerException(ctx.exp(i), message);
                    }
                }
            }
        }
        return function.getReturnType();
    }

    @Override
    public String visitBracketExpression(VigloParser.BracketExpressionContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public String visitCharLiteral(VigloParser.CharLiteralContext ctx) {
        return "char";
    }

    @Override
    public String visitIntLiteral(VigloParser.IntLiteralContext ctx) {
        return "int";
    }

    @Override
    public String visitFloatLiteral(VigloParser.FloatLiteralContext ctx) {
        return "float";
    }

    @Override
    public String visitLongLiteral(VigloParser.LongLiteralContext ctx) {
        return "long";
    }

    @Override
    public String visitDoubleLiteral(VigloParser.DoubleLiteralContext ctx) {
        return "double";
    }

    @Override
    public String visitBoolLiteral(VigloParser.BoolLiteralContext ctx) {
        return "bool";
    }
}
