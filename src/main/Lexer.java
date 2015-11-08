package main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {
    public enum TokenType {
        //NUMBER("-?[0-9]+"), BINARYOP("[*|/|+|-]"), WHITESPACE("[ \t\f\r\n]+");

        ACTION("^[a-zA-Z0-9]+"),
        ARGUMENT(".+"),
        WHITESPACE("[ \t\f\r\n]+");
        
        public final String mPattern;
        
        TokenType(String pattern) {
            mPattern = pattern;
        }
    }
    
    public static class Token {
        public TokenType type;
        public String data;
        
        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("(%s %s)", type.name(), data);
        }
    }
    
    public static ArrayList<Token> tokenizer(String input) {
        // The tokens to return
        ArrayList<Token> tokens = new ArrayList<Token>();
        
        // main.Lexer logic begins here
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.mPattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));
        
        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            if (matcher.group(TokenType.ACTION.name()) != null) {
                tokens.add(new Token(TokenType.ACTION, matcher.group(TokenType.ACTION.name())));
            } else if (matcher.group(TokenType.ARGUMENT.name()) != null) {
                tokens.add(new Token(TokenType.ARGUMENT, matcher.group(TokenType.ARGUMENT.name())));
            }
        }
        return tokens;
    }
    
    public static void main(String[] args) {
        //String input = "5 + 10 - 3";
        String input = "open file";

        // Create tokens and print them
        ArrayList<Token> tokens = tokenizer(input);
        for (Token token : tokens)
            System.out.println(token);
    }
}