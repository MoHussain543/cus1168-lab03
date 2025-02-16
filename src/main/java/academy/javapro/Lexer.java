package academy.javapro;

import java.util.*;
import java.util.regex.*;

public class Lexer {
    private static final Pattern[] PATTERNS = {
            Pattern.compile("\\s+"),                                       // whitespace
            Pattern.compile("\\b(if|else|for|while|int|float|String)\\b"), // keywords
            Pattern.compile("\\b\\d+(\\.\\d+)?\\b"),                      // literals
            Pattern.compile("==|<=|>=|!=|&&|\\|\\||[+\\-*/=<>!]"),        // operators
            Pattern.compile("[;,.(){}\\[\\]]"),                           // punctuation
            Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b")               // identifiers
    };

    private static final String[] TYPES = {
            "WHITESPACE",
            "KEYWORD",
            "LITERAL",
            "OPERATOR",
            "PUNCTUATION",
            "IDENTIFIER"
    };

    private String input;
    private List<String[]> tokens;
    private int position;

    /**
     * TODO: Initialize the lexer with the input string
     * 1. Store the input string in the 'input' field
     * 2. Initialize the tokens list as a new ArrayList
     * 3. Set the initial position to 0
     * @param input The source code string to be tokenized
     */
    public Lexer(String input) {
        this.input = input;
        this.tokens = new ArrayList<>();
        this.position = 0;
    }

    /**
     * TODO: Process the input string and break it into tokens
     * Steps to implement:
     * 1. Create a while loop that continues while position < input.length()
     * 2. Get the remaining input using substring(position)
     * 3. Try to match each pattern in PATTERNS array:
     *    - Create a matcher using pattern.matcher(remainingInput)
     *    - Use matcher.lookingAt() to check if it matches at current position
     *    - If match found:
     *      a. Get the matched text using matcher.group()
     *      b. If not whitespace, add new token to tokens list
     *      c. Update position by adding length of matched text
     * 4. If no pattern matches, throw RuntimeException for invalid input
     */
    public void tokenize() {
        while (position < input.length()) {
            String remainingInput = input.substring(position);
            boolean matched = false;

            for (int i = 0; i < PATTERNS.length; i++) {
                Matcher matcher = PATTERNS[i].matcher(remainingInput);
                if (matcher.lookingAt()) {
                    String matchedText = matcher.group();
                    if (i != 0) { // skip whitespace
                        tokens.add(new String[]{TYPES[i], matchedText});
                    }
                    position += matchedText.length();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                throw new RuntimeException("Unexpected character at position: " + position);
            }
        }
    }

    /**
     * TODO: Return the list of tokens
     * 1. Return the tokens list containing all found tokens
     * 2. Each token should be a String array with two elements:
     *    - First element: Token type (from TYPES array)
     *    - Second element: Token value (the actual text)
     * @return List<String[]> The list of tokens
     */
    public List<String[]> getTokens() {
        return tokens;
    }

    public static void main(String[] args) {
        String code1 = "int x = 10;";
        Lexer lexer1 = new Lexer(code1);
        lexer1.tokenize();
        System.out.println("Test Case 1: Basic Variable Declaration");
        for (String[] token : lexer1.getTokens()) {
            System.out.println(token[0] + ": " + token[1]);
        }

        String code2 = "if(x > 5){}";
        Lexer lexer2 = new Lexer(code2);
        lexer2.tokenize();
        System.out.println("\nTest Case 2: If Statement");
        for (String[] token : lexer2.getTokens()) {
            System.out.println(token[0] + ": " + token[1]);
        }
    }
}
