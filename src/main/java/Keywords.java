import java.util.HashMap;
import java.util.Map;

public class Keywords {
  public static final Map<String, TokenTypes> map = new HashMap<>();
  static {
    map.put("and",   TokenTypes.AND);
    map.put("class", TokenTypes.CLASS);
    map.put("else",  TokenTypes.ELSE);
    map.put("false", TokenTypes.FALSE);
    map.put("for",   TokenTypes.FOR);
    map.put("fun",   TokenTypes.FUN);
    map.put("if",    TokenTypes.IF);
    map.put("nul",   TokenTypes.NUL);
    map.put("or",    TokenTypes.OR);
    map.put("print", TokenTypes.PRINT);
    map.put("return",TokenTypes.RETURN);
    map.put("super", TokenTypes.SUPER);
    map.put("this",  TokenTypes.THIS);
    map.put("true",  TokenTypes.TRUE);
    map.put("var",   TokenTypes.VAR);
    map.put("while", TokenTypes.WHILE);
  }
}
