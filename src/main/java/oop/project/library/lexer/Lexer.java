package oop.project.library.lexer;
import oop.project.library.scenarios.Result;

import java.util.HashMap;
import java.util.Map;

public class Lexer {

    public static Result<Map<String, Object>> parse(String input)
    {
        Map<String, Object> LexedArguments = new HashMap<>();
        int TokenCount = 0;
        String[] Tokens = input.split(" ");
        for (int i = 0; i < Tokens.length; i++)
        {
            //If the token contains 3 or more flags
            if(Tokens[i].startsWith("---"))
            {
                return new Result.Failure<>("Invalid argument : " + Tokens[i] + "contains more than 2 '-'");
            }
            //If the token that's being read is a flag
            else if (Tokens[i].startsWith("--")) {
                //Check if the token is already in our map of tokens
                if (LexedArguments.containsKey(Tokens[i])) {
                    //If the token is present throw an error
                    return new Result.Failure<>("The token" + Tokens[i] + "has been already initialized. Do not initialize it again.");
                } else {
                    //The token does not exist check the next value
                    //If the next token is a flag
                    if (i < Tokens.length - 1 && Tokens[i + 1].startsWith("-")) {
                        //The next token is a flag which is incorrect
                        return new Result.Failure<>("The token" + Tokens[i] + "is followed by another flag" + Tokens[i + 1] + "instead of an argument.");
                    }
                    //If the next token is not a flag
                    else if (i < Tokens.length - 1 && !Tokens[i + 1].startsWith("-")) {
                        String flagName = Tokens[i].substring(2);
                        String flagValue = Tokens[i + 1];
                        LexedArguments.put(flagName, flagValue);
                        TokenCount++;
                        i++;
                    } else {
                        return new Result.Failure<>("the flag" + Tokens[i] + "was not given an argument, Please provide an argument");
                    }

                }
            }
            //If token starts with "-" or not
            else
            {
                String Token = Tokens[i];
                //If the token starts with "-" then remove it
                if (Token.startsWith("-")) {
                    if (Token.matches("-?\\d+"))
                    {
                        Token = Token.substring(1);
                    }
                }
                LexedArguments.put(String.valueOf(TokenCount), Token);
                TokenCount++;
            }
        }
        return new Result.Success<>(LexedArguments);
    }
}
