package nl.firepy.viglo.component.branching;

import nl.firepy.viglo.compiler.scope.Scope;
import nl.firepy.viglo.component.BlockComponent;
import nl.firepy.viglo.component.VigloComponent;
import nl.firepy.viglo.component.expr.ExprComponent;

import java.util.ArrayList;

public class WhileComponent implements VigloComponent {

    private ExprComponent exrp;
    private BlockComponent block;
    private Scope scope;

    public WhileComponent(ExprComponent exrp, BlockComponent block, Scope scope) {
        this.exrp = exrp;
        this.block = block;
        this.scope = scope;
    }

    @Override
    public ArrayList<String> generateCode() {
        String startLabel = scope.getLabel("start_while");
        String endLabel = scope.getLabel("end_while");
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tnop");
        asm.add(startLabel + ":");
        asm.addAll(exrp.generateCode());
        asm.add("\tifeq " + endLabel);
        asm.addAll(block.generateCode());
        asm.add("\tgoto " + startLabel);
        asm.add(endLabel + ":");
        return asm;
    }
}
