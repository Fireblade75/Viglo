package nl.saxion.viglo;


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
        VigloCodeVisitor visitor = new VigloCodeVisitor();
        ArrayList<String> prog  = visitor.visit(program).generateCode();
        // Output fixed part of the Jasmin file (except for the name)
        //System.out.println(startProg.replaceAll("\\{\\{name\\}\\}",name));
        // Output compiled part of the jasmin file
        System.out.println(prog.stream().collect(Collectors.joining("\n")));
        // Output footer of jasmin file
        //System.out.println(endProg);
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
