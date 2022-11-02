package nl.firepy.viglo.lang;

import org.junit.jupiter.api.Test;

import nl.firepy.viglo.compiler.VigloCompiler;

public class VCounter {
    
    VigloCompiler target = new VigloCompiler();

    private final String exampleProgram = """
        class Counter {
            func Counter(): void => {
                twice(5)
            }

            static func twice(val: double): void => {
                echo val * 2
            }
        }
    """;

    @Test
    void compileBasicStruct() {
        String result = target.compileViglo(exampleProgram);
        System.out.println(result);
        
    }

}