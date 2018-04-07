package nl.saxion.viglo;


import nl.saxion.viglo.component.*;
import nl.saxion.viglo.component.expr.*;

public class VigloCodeVisitor extends VigloBaseVisitor<VigloComponent> {

    private ImportContext importContext = new ImportContext();
    private Scope scope = new Scope();

    @Override
    public VigloComponent visitProgram(VigloParser.ProgramContext ctx) {
        ProgramComponent programComponent = new ProgramComponent();

        for(VigloParser.ImportStatementContext importStatement : ctx.importStatement()) {
            visitImportStatement(importStatement);
        }

        if(ctx.classBlock() != null) {
            programComponent.setClassComponent(visitClassBlock(ctx.classBlock()));
        } else {
            programComponent.setClassComponent(visitStructBlock(ctx.structBlock()));
        }

        return programComponent;
    }

    @Override
    public VigloComponent visitImportStatement(VigloParser.ImportStatementContext ctx) {
        importContext.addImport(ctx.IMPORT_LITERAL().getText());
        return null;
    }

    @Override
    public VigloComponent visitClassBlock(VigloParser.ClassBlockContext ctx) {
        String className = ctx.NAME().getText();
        return new ClassComponent(className);
    }

    @Override
    public VigloComponent visitStructBlock(VigloParser.StructBlockContext ctx) {
        String className = ctx.NAME().getText();
        BlockComponent blockComponent = visitBlock(ctx.block());
        return new StructComponent(className, blockComponent);
    }

    @Override
    public BlockComponent visitBlock(VigloParser.BlockContext ctx) {
        scope = new Scope(scope);
        BlockComponent blockComponent = new BlockComponent(scope);
        for(VigloParser.StatementContext statement : ctx.statement()) {
            blockComponent.add(visit(statement));
        }
        scope.close();
        scope = scope.getParent();
        return blockComponent;
    }

    @Override
    public VigloComponent visitDeclareStatement(VigloParser.DeclareStatementContext ctx) {
        String label = ctx.NAME().getText();
        boolean constant = ctx.varKey.getText().equals("const");
        if(ctx.type()!=null) {
            Value value = new Value(ctx.type().getText(), constant);
            scope.addValue(label, value);
            return new EmptyComponent();
        } else {
            ExprComponent expr = (ExprComponent) visit(ctx.exp());
            Value value = expr.getValue();
            scope.addValue(label, value);
            return new AssignComponent(expr, scope.getIndex(label));
        }
    }

    @Override
    public VigloComponent visitChainExp(VigloParser.ChainExpContext ctx) {
        if(ctx.chainPart().size()>0) {
            throw new UnsupportedOperationException();
        } else {
            return visit(ctx.chainTail());
        }
    }

    @Override
    public VigloComponent visitVariable(VigloParser.VariableContext ctx) {
        String label = ctx.NAME().getText();
        return new VariableComponent(scope.getValue(label), scope.getIndex(label));
    }

    @Override
    public VigloComponent visitAssignStatement(VigloParser.AssignStatementContext ctx) {
        ExprComponent expr = (ExprComponent) visit(ctx.exp());
        String label = ctx.variable().getText();
        return new AssignComponent(expr, scope.getIndex(label));
    }

    @Override
    public VigloComponent visitEchoStatement(VigloParser.EchoStatementContext ctx) {
        ExprComponent expr = (ExprComponent) visit(ctx.exp());
        return new EchoComponent(expr);
    }

    @Override
    public NotExprComponent visitNotExpression(VigloParser.NotExpressionContext ctx) {
        ExprComponent childComponent = (ExprComponent) visit(ctx.exp());
        return new NotExprComponent(childComponent, scope);
    }

    @Override
    public VigloComponent visitLogicExpression(VigloParser.LogicExpressionContext ctx) {
        ExprComponent leftExpr = (ExprComponent) visit(ctx.left);
        ExprComponent rightExpr = (ExprComponent) visit(ctx.right);
        String op = ctx.logic_operator().getText();
        return new LogicExpression(leftExpr, rightExpr, op, scope);
    }

    @Override
    public VigloComponent visitMathExpression(VigloParser.MathExpressionContext ctx) {
        ExprComponent leftExpr = (ExprComponent) visit(ctx.left);
        ExprComponent rightExpr = (ExprComponent) visit(ctx.right);
        String op = ctx.math_operator().getText();
        return new MathExpression(leftExpr, rightExpr, op);
    }

    @Override
    public VigloComponent visitBracketExpression(VigloParser.BracketExpressionContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public IntLiteral visitIntLiteral(VigloParser.IntLiteralContext ctx) {
        int val = Integer.parseInt(ctx.INT_LITERAL().getText());
        return new IntLiteral(val);
    }

    @Override
    public BoolLiteral visitBoolLiteral(VigloParser.BoolLiteralContext ctx) {
        boolean val = Boolean.parseBoolean(ctx.BOOL_LITERAL().getText());
        return new BoolLiteral(val);
    }

    @Override
    public CharLiteral visitCharLiteral(VigloParser.CharLiteralContext ctx) {
        char val = ctx.CHAR_STRING().getText().charAt(1);
        return new CharLiteral(val);
    }

    @Override
    public FloatLiteral visitFloatLiteral(VigloParser.FloatLiteralContext ctx) {
        float val = Float.parseFloat(ctx.FLOAT_LITERAL().getText());
        return new FloatLiteral(val);
    }

    @Override
    public DoubleLiteral visitDoubleLiteral(VigloParser.DoubleLiteralContext ctx) {
        double val = Float.parseFloat(ctx.DOUBLE_LITERAL().getText());
        return new DoubleLiteral(val);
    }

    @Override
    public VigloComponent visitLongLiteral(VigloParser.LongLiteralContext ctx) {
        String longStr = ctx.LONG_LITERAL().getText();
        longStr = longStr.substring(0, longStr.length() - 1);
        long val = Long.parseLong(longStr);
        return new LongLiteral(val);
    }
}
