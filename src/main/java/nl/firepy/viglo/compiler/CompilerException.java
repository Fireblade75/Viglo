package nl.firepy.viglo.compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class CompilerException extends RuntimeException {
    public CompilerException( ParserRuleContext ctx, String s ) {
        super(buildMessage(ctx, s));
    }

    private static String buildMessage( ParserRuleContext ctx, String msg ) {
        Token firstToken = ctx.getStart();
        int pos = firstToken.getCharPositionInLine();
        int line = firstToken.getLine();

        return String.format("Line %d:%d => %s", line, pos, msg);
    }
}
