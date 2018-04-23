package nl.saxion.viglo;


import nl.saxion.viglo.error.SyntaxError;
import nl.saxion.viglo.error.SyntaxErrorListener;
import nl.saxion.viglo.type.ClassHeader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {

    private String sourceFileName;
    private String destFileName = null;

    public Main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar viglo.jar v-file");
            System.out.println("   Or: java -jar viglo.jar -o j-file v-file");
        } else {
            sourceFileName = args[0];
            boolean err = false;
            if (args.length >= 3) {
                if (args[1].equals("-o")) {
                    destFileName = args[2];
                } else {
                    System.err.println("Unsupported operation: " + args[1]);
                    err = true;
                }
            }

            if (!err) {
                compileCode();
            }
        }
    }

    public void compileCode() {
        String compileString = readCode();

        // Create lexer and run scanner to create stream of tokens
        CharStream charStream = CharStreams.fromString(compileString);
        VigloLexer lexer = new VigloLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create parser and feed it the tokens
        VigloParser parser = new VigloParser(tokens);
        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree program = parser.program();

        if (errorListener.hasSyntaxErrors()) {
            for (SyntaxError syntaxError : errorListener.getSyntaxErrors()) {
                System.out.println("Error: " + syntaxError);
            }
        } else {
            VigloMethodVisitor headerBuilder = new VigloMethodVisitor();
            VigloTypeVisitor typeChecker = new VigloTypeVisitor();
            VigloCodeVisitor visitor = new VigloCodeVisitor();

            try {
                headerBuilder.visit(program);
                ClassHeader classHeader = headerBuilder.getClassHeader();

                typeChecker.setClassHeader(classHeader);
                visitor.setClassHeader(classHeader);
                typeChecker.visit(program);
                ArrayList<String> prog = visitor.visit(program).generateCode();

                String code = prog.stream().collect(Collectors.joining("\n"));

                if(destFileName == null) {
                    System.out.println(code);
                } else {
                    writeCode(code);
                }
            } catch (CompilerException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String readCode() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(sourceFileName)));
            String line;
            StringBuilder code = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                code.append(line).append("\n");
            }
            return code.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private void writeCode(String code) {
        try {
            FileWriter writer = new FileWriter(new File(destFileName));
            writer.write(code);
        } catch (IOException e) {
            System.err.println("Could not write to file: " + destFileName);
        }
    }

    public static void main(String[] args) {
        new Main(args);
    }
}
