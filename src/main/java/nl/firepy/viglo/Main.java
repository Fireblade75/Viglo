package nl.firepy.viglo;


import nl.firepy.viglo.compiler.CompilerException;
import nl.firepy.viglo.compiler.VigloCompiler;
import nl.firepy.viglo.error.SyntaxErrorException;
import java.io.*;

public class Main {

    private String sourceFileName;
    private String destFileName = null;

    private VigloCompiler vigloCompiler = new VigloCompiler();

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
        try {
            String compileString = readCode();
            String code = vigloCompiler.compileViglo(compileString);

            if(destFileName == null) {
                System.out.println(code);
            } else {
                writeCode(code);
            }
            
        } catch(CompilerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(SyntaxErrorException e) {
            System.err.println(e.getMessage());
        } catch(IOException e) {
            System.err.println(e);
        }
    }

    private String readCode() throws IOException {
        try (var reader = new BufferedReader(new FileReader(new File(sourceFileName)))) {
            String line;
            StringBuilder code = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                code.append(line).append("\n");
            }
            return code.toString();
        }
    }

    private void writeCode(String code) throws IOException {
        try (var writer = new FileWriter(new File(destFileName))) {
            writer.write(code);
        }
    }

    public static void main(String[] args) {
        new Main(args);
    }
}
