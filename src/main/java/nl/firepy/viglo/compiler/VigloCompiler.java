package nl.firepy.viglo.compiler;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import nl.firepy.viglo.error.SyntaxError;
import nl.firepy.viglo.error.SyntaxErrorException;
import nl.firepy.viglo.error.SyntaxErrorListener;
import nl.firepy.viglo.type.ClassHeader;

public class VigloCompiler {
    
    public String compileViglo(String sourceCode) throws CompilerException {
        // Create lexer and run scanner to create stream of tokens
        CharStream charStream = CharStreams.fromString(sourceCode);
        VigloLexer lexer = new VigloLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create parser and feed it the tokens
        VigloParser parser = new VigloParser(tokens);
        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree program = parser.program();

        if (errorListener.hasSyntaxErrors()) {
            throw new SyntaxErrorException(errorListener.getSyntaxErrors());
        } else {
            VigloMethodVisitor headerBuilder = new VigloMethodVisitor();
            VigloTypeVisitor typeChecker = new VigloTypeVisitor();
            VigloCodeVisitor visitor = new VigloCodeVisitor();

                headerBuilder.visit(program);
                ClassHeader classHeader = headerBuilder.getClassHeader();

                typeChecker.setClassHeader(classHeader);
                visitor.setClassHeader(classHeader);
                typeChecker.visit(program);
                ArrayList<String> prog = visitor.visit(program).generateCode();

                String code = prog.stream().collect(Collectors.joining("\n"));

                return code;
        }
    }
}
