package nl.saxion.viglo;


import nl.saxion.viglo.component.ClassComponent;
import nl.saxion.viglo.component.ProgramComponent;
import nl.saxion.viglo.component.StructComponent;
import nl.saxion.viglo.component.VigloComponent;

public class VigloCodeVisitor extends VigloBaseVisitor<VigloComponent> {

    private ImportContext importContext = new ImportContext();

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
}
