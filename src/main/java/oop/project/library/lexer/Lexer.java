package oop.project.library.lexer;
import oop.project.library.scenarios.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class Lexer {

    public List<String> PositionalArguments = new ArrayList<>();
    public Map<String, String> NamedArguments = new HashMap<>();

    public Lexer(String input)
    {
        int TokenCount = 0;
        String[] tokenList = input.split(" ");
        if(tokenList[0] == "")
        {
            return;
        }
        for (int i = 0; i < tokenList.length; i++)
        {
            int numberOfFlags = characterCounter('-', tokenList[i]);
            //If the token contains 3 or more '-'s
            if(numberOfFlags >= 3)
            {
                throw new LexerException("Invalid flag : " + tokenList[i] + " contains more than 2 '-'.");
            }
            //If the token that's being read has 2 '-'s
            else if (numberOfFlags == 2)
            {
                String flagName = tokenList[i].substring(2);
                if (flagName.isEmpty())
                {
                    throw new LexerException("Invalid argument : The flag is " + tokenList[i] + " is not named. Please name the flag.");
                }
                //Check if the token is already in our map of tokens
                if (PositionalArguments.contains(flagName) || NamedArguments.containsKey(flagName)) {
                    //If the token is present throw an error
                    throw new LexerException("The flag " + tokenList[i] + " has been given a value. Do not give it a value again.");
                } else {
                    //The token does not exist check the next value
                    //If the next token is a flag
                    if (i < tokenList.length - 1 && characterCounter('-', tokenList[i + 1]) >= 2) {
                        //The next token is a flag which is incorrect
                        throw new LexerException("The flag " + tokenList[i] + " is given a value of " + tokenList[i + 1] + " which has more than 1 '-'. Please give a value that has no more than 1 '-'.");
                    }
                    //If the next token is not a flag
                    else if (i < tokenList.length - 1 && characterCounter('-', tokenList[i + 1]) <= 1) {

                        String flagValue = tokenList[i+1];
                        //If the token starts with "-" then remove it
                        if (flagValue.startsWith("-")) {
                            if (!flagValue.matches("-?\\d+.?\\d*"))
                            {
                                //Maybe change this
                                flagValue = flagValue.substring(1);
                            }
                        }
                        if (flagValue.equals("-"))
                        {
                            throw new LexerException("The flag " + tokenList[i] + " was not given an argument, Please provide an argument");
                        }
                        NamedArguments.put(flagName, flagValue);
                        TokenCount++;
                        i++;
                    } else {
                        throw new LexerException("the flag " + tokenList[i] + " was not given an argument, Please provide an argument");
                    }
                }
            }
            //If token starts with "-" or not
            else
            {
                String Token = tokenList[i];
                //If the token starts with "-" then remove it
                if (Token.startsWith("-")) {
                    if (!Token.matches("-?\\d+.?\\d*"))
                    {
                        Token = Token.substring(1);
                    }
                }
                if (Token.equals("-"))
                {
                    throw new LexerException("Please provide a non empty literal");
                }
                PositionalArguments.add(Token);
                TokenCount++;
            }
        }
    }

//    public static Map<String, Object> parse(String input) throws LexerException
//    {
//        Map<String, Object> LexedArguments = new HashMap<>();
//        int TokenCount = 0;
//        String[] tokenList = input.split(" ");
//        if(tokenList[0] == "")
//        {
//            return LexedArguments;
//        }
//        for (int i = 0; i < tokenList.length; i++)
//        {
//            int numberOfFlags = characterCounter('-', tokenList[i]);
//            //If the token contains 3 or more '-'s
//            if(numberOfFlags >= 3)
//            {
//                throw new LexerException("Invalid flag : " + tokenList[i] + " contains more than 2 '-'.");
//            }
//            //If the token that's being read has 2 '-'s
//            else if (numberOfFlags == 2)
//            {
//                String flagName = tokenList[i].substring(2);
//                if (flagName.isEmpty())
//                {
//                    throw new LexerException("Invalid argument : The flag is " + tokenList[i] + " is not named. Please name the flag.");
//                }
//                //Check if the token is already in our map of tokens
//                if (LexedArguments.containsKey(flagName)) {
//                    //If the token is present throw an error
//                    throw new LexerException("The flag " + tokenList[i] + " has been given a value. Do not give it a value again.");
//                } else {
//                    //The token does not exist check the next value
//                    //If the next token is a flag
//                    if (i < tokenList.length - 1 && characterCounter('-', tokenList[i + 1]) >= 2) {
//                        //The next token is a flag which is incorrect
//                        throw new LexerException("The flag " + tokenList[i] + " is given a value of " + tokenList[i + 1] + " which has more than 1 '-'. Please give a value that has no more than 1 '-'.");
//                    }
//                    //If the next token is not a flag
//                    else if (i < tokenList.length - 1 && characterCounter('-', tokenList[i + 1]) <= 1) {
//
//                        String flagValue = tokenList[i+1];
//                        //If the token starts with "-" then remove it
//                        if (flagValue.startsWith("-")) {
//                            if (!flagValue.matches("-?\\d+.?\\d*"))
//                            {
//                                //Maybe change this
//                                flagValue = flagValue.substring(1);
//                            }
//                        }
//                        if (flagValue.equals("-"))
//                        {
//                            throw new LexerException("The flag " + tokenList[i] + " was not given an argument, Please provide an argument");
//                        }
//                        LexedArguments.put(flagName, flagValue);
//                        TokenCount++;
//                        i++;
//                    } else {
//                        throw new LexerException("the flag " + tokenList[i] + " was not given an argument, Please provide an argument");
//                    }
//                }
//            }
//            //If token starts with "-" or not
//            else
//            {
//                String Token = tokenList[i];
//                //If the token starts with "-" then remove it
//                if (Token.startsWith("-")) {
//                    if (!Token.matches("-?\\d+.?\\d*"))
//                    {
//                        Token = Token.substring(1);
//                    }
//                }
//                if (Token.equals("-"))
//                {
//                    throw new LexerException("Please provide a non empty literal");
//                }
//                LexedArguments.put(String.valueOf(TokenCount), Token);
//                TokenCount++;
//            }
//        }
//        return LexedArguments;
//    }

    private static int characterCounter(Character character, String textToBeSearched)
    {
        int numberOfTimesAppeared = 0;
        for(int i = 0; i < textToBeSearched.length(); i++)
        {
            if (textToBeSearched.charAt(i) == character)
            {
                numberOfTimesAppeared++;
            }
            else
            {
                break;
            }
        }
        return numberOfTimesAppeared;
    }

    public int lexedArgumentsLength()
        {
            return PositionalArguments.size() + NamedArguments.size();
        }
}
