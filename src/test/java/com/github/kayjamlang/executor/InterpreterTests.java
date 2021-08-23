package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.libs.Library;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Interpreter Tests")
public class InterpreterTests {
    public final static File testsDir = new File("./tests/");

    @TestFactory
    public Stream<DynamicTest> tests() {
        List<DynamicTest> tests = new ArrayList<>();
        File[] testsFiles = testsDir.listFiles();
        if(testsFiles!=null) for (File test : testsFiles) {
            tests.add(DynamicTest.dynamicTest(test.getName(), () -> {
                String[] data = readFile(test).split("--OUTPUT--?\\R");
                String code = data[0];
                String needOutput = data[1];

                Executor executor = new Executor();
                TestLibrary testLibrary = new TestLibrary();

                executor.addLibrary(testLibrary);
                executor.execute(code);

                System.out.println(testLibrary.output);
                assertEquals(needOutput.replaceAll("\\R", "\n"), testLibrary.output.toString());
            }));
        }

        return tests.stream();
    }

    public static String readFile(File file) throws IOException {
        Scanner scanner = new Scanner(Paths.get(file.getPath()).toUri().toURL().openStream());

        StringBuilder output = new StringBuilder();
        while (scanner.hasNextLine()) output.append(scanner.nextLine()).append("\r\n");

        return output.toString();
    }

    public static class TestLibrary extends Library {
        public final StringBuilder output = new StringBuilder();

        public TestLibrary() {
            functions.add(new LibFunction("println", Type.VOID, (mainContext, context) -> {
                output.append(context.getVariable("value").toString()).append("\n");
                return null;
            }, new Argument(Type.ANY, "value")));

            functions.add(new LibFunction("print", Type.VOID, (mainContext, context) -> {
                output.append(context.getVariable("value").toString());
                return null;
            }, new Argument(Type.ANY, "value")));
        }
    }
}
