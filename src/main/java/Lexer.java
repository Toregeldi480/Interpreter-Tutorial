import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();
  private final int length;
  private int start = 0;
  private int current = 0;
  private int line = 1;

  public Lexer(String source) {
    this.source = source;
    length = source.length();
  }

  public List<Token> scanTokens() {
    while (!isEOF()) {
      start = current;
      char c = peek();

      switch (c) {
        case '(': addToken(TokenTypes.LEFT_PAREN); break;
        case ')': addToken(TokenTypes.RIGHT_PAREN); break;
        case '{': addToken(TokenTypes.LEFT_BRACE); break;
        case '}': addToken(TokenTypes.RIGHT_BRACE); break;
        case ',': addToken(TokenTypes.COMMA); break;
        case '.': addToken(TokenTypes.DOT); break;
        case '-': addToken(TokenTypes.MINUS); break;
        case '+': addToken(TokenTypes.PLUS); break;
        case ';': addToken(TokenTypes.SEMICOLON); break;
        case '*': addToken(TokenTypes.STAR); break;
        case '!': if (matchNext('=')) {current++; addToken(TokenTypes.NOT_EQUAL);} 
                  else addToken(TokenTypes.NOT);
                  break;
        case '=': if (matchNext('=')) {current++; addToken(TokenTypes.EQUAL_EQUAL);} 
                  else addToken(TokenTypes.EQUAL);
                  break;
        case '<': if (matchNext('=')) {current++; addToken(TokenTypes.LESS_EQUAL);} 
                  else addToken(TokenTypes.LESS);
                  break;
        case '>': if (matchNext('=')) {current++; addToken(TokenTypes.GREATER_EQUAL);} 
                  else addToken(TokenTypes.GREATER);
                  break;
        case '/': slash(); break;
        case '"': string(); break; 
        case ' ':
        case '\r':
        case '\t': break;
        case '\n': line++; break;
        default:
          if (isDigit(c)) number();
          else if (isAlpha(c)) identifier();
          else Main.error(line, "Unexpected character");
          break;
      }

      current++;
    }

    tokens.add(new Token(TokenTypes.EOF, "", null, line));
    return tokens;
  }

  private void addToken(TokenTypes type) {
    addToken(type, null);
  }

  private void addToken(TokenTypes type, Object literal) {
    String text = source.substring(start, current + 1);
    tokens.add(new Token(type, text, literal, line));
  }

  private boolean matchNext(char expected) {
    if (isEOF()) return false;
    return peekNext() == expected;
  }

  private boolean isDigit(Character c) {
    return c >= '0' && c <= '9';
  }

  private boolean isAlpha(Character c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
  }

  private boolean isAlphaNumeric(Character c) {
    return isAlpha(c) || isDigit(c);
  }

  private boolean isEOF() {
    return current >= length;
  }

  private Character peek() {
    if (current >= length) Main.error(line, "Something");
    return source.charAt(current);
  }

  private Character peekNext() {
    if (current + 1 >= length) Main.error(line, "Something");
    return source.charAt(current + 1);
  }

  private void string() {
    current++;
    while (!isEOF() && peek() != '"') {
      if (peek() == '\n') line++;
      if (current == length - 1) Main.error(line, "Unterminated string");
      current++;
    }
    String value = source.substring(start + 1, current);
    addToken(TokenTypes.STRING, value);
  }

  private void slash() {
    if (matchNext('/'))
      while (peek() != '\n' && !isEOF()) current++;
    else if (matchNext('*'))
      while (current < length - 2 && !source.substring(current, current + 2).equals("*/")) current++;
    else
      addToken(TokenTypes.SLASH);
  }

  private void number() {
    while (!isEOF() && isDigit(peekNext())) current++;
    if (peekNext() == '.') {
      current++;
      while (!isEOF() && isDigit(peekNext())) current++;
    }
    addToken(TokenTypes.NUMBER, Double.parseDouble(source.substring(start, current + 1)));
  }

  private void identifier() {
    while (isAlphaNumeric(peekNext())) current++;
    String text = source.substring(start, current + 1);
    TokenTypes type = Keywords.map.get(text);
    if (type == null) type = TokenTypes.IDENTIFIER;
    addToken(type);
  }
}
