package nl.saxion.viglo;


import nl.saxion.viglo.type.ClassHeader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    public Main(String[] args) {

        String compileString = readCode();
        String name = "xName";

        // Create lexer and run scanner to create stream of tokens
        CharStream charStream = CharStreams.fromString(compileString);
        VigloLexer lexer = new VigloLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create parser and feed it the tokens
        VigloParser parser = new VigloParser(tokens);
        ParseTree program = parser.program();
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

            System.out.println(prog.stream().collect(Collectors.joining("\n")));
        } catch (CompilerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String readCode() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("code.v")));
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

    public static void main(String[] args) {
        new Main(args);
    }
}
