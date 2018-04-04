package nl.saxion.viglo;


import nl.saxion.viglo.component.*;

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
        return new StructComponent();
    }

    @Override
    public VigloComponent visitDeclareStatement(VigloParser.DeclareStatementContext ctx) {
        Value value;
        String label = ctx.NAME().getText();
        boolean constant = ctx.varKey.getText().equals("const");
        if(ctx.type()!=null) {
            value = new Value(null, ctx.type().getText(), constant);
        } else {
            //TODO assign value
            value = null;
        }
        scope.addValue(label, value);
        return super.visitDeclareStatement(ctx);
    }

    @Override
    public VigloComponent visitBlock(VigloParser.BlockContext ctx) {
        scope = new Scope(scope);
        BlockComponent blockComponent = new BlockComponent();
        for(VigloParser.StatementContext statement : ctx.statement()) {
            blockComponent.add(visit(statement));
        }
        scope = scope.getParent();
        return blockComponent;
    }
}
