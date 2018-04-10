package nl.saxion.viglo.component.branching;

import nl.saxion.viglo.Scope;
import nl.saxion.viglo.component.BlockComponent;
import nl.saxion.viglo.component.VigloComponent;
import nl.saxion.viglo.component.expr.EmptyComponent;
import nl.saxion.viglo.component.expr.ExprComponent;

import java.util.ArrayList;
import java.util.LinkedList;

public class IfComponent implements VigloComponent {

    private ExprComponent expr;
    private BlockComponent ifBlock, elseBlock;
    private Scope scope;
    private ArrayList<ElseIfBlock> elseIfBlocks = new ArrayList<>();

    public IfComponent(ExprComponent expr, BlockComponent block, Scope scope) {
        this.expr = expr;
        this.ifBlock = block;
        this.scope = scope;
    }

    public void addElseBlock(BlockComponent block) {
        this.elseBlock = block;
    }

    public void addElseIfBlock(ElseIfBlock elseIfBlock) {
        elseIfBlocks.add(elseIfBlock);
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        LinkedList<String> labels = new LinkedList<>();

        // Generate labels
        if(elseBlock != null) {
            labels.push(scope.getLabel("else"));
        }
        for(ElseIfBlock elseIfBlock : elseIfBlocks) {
            labels.push(scope.getLabel("elseif"));
        }
        labels.push(scope.getLabel("endif"));

        // Generate code
        asm.addAll(expr.generateCode());
        asm.add("\tifeq " + labels.peek());
        asm.addAll(ifBlock.generateCode());
        if(elseIfBlocks.size() > 0 || elseBlock != null) {
            asm.add("\tgoto " + labels.peekLast());
        }

        asm.add(labels.pop() + ":");
        for(ElseIfBlock elseIfBlock : elseIfBlocks) {
            asm.addAll(elseIfBlock.expr.generateCode());
            asm.add("\tifeq " + labels.peek());
            asm.addAll(elseIfBlock.block.generateCode());
            if(labels.size()>1) {
                asm.add("\tgoto " + labels.peekLast());
            }
            asm.add(labels.pop() + ":");
        }
        if(elseBlock != null) {
            asm.addAll(elseBlock.generateCode());
            asm.add(labels.pop() + ":");
        }

        return asm;
    }

    public static class ElseIfBlock extends EmptyComponent {
        private ExprComponent expr;
        private BlockComponent block;

        public ElseIfBlock(ExprComponent expr, BlockComponent block) {
            this.expr = expr;
            this.block = block;
        }
    }
}
