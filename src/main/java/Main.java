import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
  private static boolean hasError = false;

  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.err.println("Usage: main <filename>");
      System.exit(64);
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
  }

  public static void error(int line, String message) {
    System.err.println("Line[" + line + "]: " + message);
    hasError = true;
  }

  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path)); 
    run(new String(bytes, Charset.defaultCharset()));

    if (hasError) System.exit(65);
  }

  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    while (true) {
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null) break;
      run(line);
      hasError = false;
    }
  }

  private static void run(String source) throws IOException {
    Lexer lexer = new Lexer(source);
    List<Token> tokens = lexer.scanTokens();

    for (Token token : tokens) {
      System.out.println(token);
    }
  }
}
