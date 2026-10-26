public class Token {
  private final TokenTypes type;
  private final String lexeme;
  private final Object literal;
  private final int line;

  public Token(TokenTypes type, String lexeme, Object literal, int line) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.line = line;
  }

  public String toString() {
    return type + " " + lexeme + " " + literal;
  }
}
