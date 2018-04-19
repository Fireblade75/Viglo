package nl.saxion.viglo.error;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This file describes an error listener for syntax errors recognized by ANTLR
 * It was made for the antlr intellij plugin and adapted to work in the Viglo Compiler
 * https://github.com/antlr/intellij-plugin-v4
 *
 * Licence: BSD 3-Clause "New" or "Revised" License
 * Copyright (c) 2013, Terence Parr
 */
public class SyntaxErrorListener extends BaseErrorListener {
    private final List<SyntaxError> syntaxErrors = new ArrayList<>();

    public SyntaxErrorListener() {
    }

    public List<SyntaxError> getSyntaxErrors() {
        return syntaxErrors;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {
        syntaxErrors.add(new SyntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e));
    }
    @Override
    public String toString() {
        return Utils.join(syntaxErrors.iterator(), "\n");
    }

    public boolean hasSyntaxErrors() {
        return syntaxErrors.size() > 0;
    }
}