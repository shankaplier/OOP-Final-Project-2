# Will
Add
```java
var command = new Command()
        .positional(
                "left",
                new Argument(new IntegerParser())
        ).positional(
                "right",
                new Argument(new IntegerParser())
        );
var arguments = command.run(input);
int left = arguments.get("left");
int right = arguments.get("right");
```
Sub
```java
var command = new Command()
        .named(
                "left",
                new Argument(new IntegerParser())
        ).named(
                "right",
                new Argument(new IntegerParser())
        );
var arguments = command.run(input);
int left = arguments.get("left");
int right = arguments.get("right");
```
fizzbuzz
```java
var command = new Command()
        .positional(
                "number",
                new Argument(new IntegerParser())
                        .require(x -> 1 <= x && x <= 100)
        );
var arguments = command.run(input);
int number = arguments.get("number");
```
difficulty
```java
var command = new Command()
        .positional(
                "difficulty",
                new Argument()
                        .choices("easy", "normal", "hard", "peaceful")
        );
var arguments = command.run(input);
String difficulty = arguments.get("difficulty");
```
echo
```java
var command = new Command()
        .positional(
                "message",
                new Argument()
                        .optional("Echo, echo, echo...")
        );
var arguments = command.run(input);
String message = arguments.get("message");
```
search
```java
var command = new Command()
        .positional(
                "term",
                new Argument()
        ).named(
                "case-insensitive",
                new Argument(new BooleanParser())
                        .optional(false)
        );
var arguments = command.run(input);
String term = arguments.get("term");
bool caseInsensitive = arguments.get("case-insensitive");
```
weekday
```java
var command = new Command()
        .positional(
                "date",
                new ArgumentParser(new CustomParser(LocalDate::parse))
        );
var arguments = command.run(input);
LocalDate date = arguments.get("date");
```
# Shashank

# Will

# Shashank
Add function
```java
private static Result<Map<String, Object>> add(String arguments) {
    var commandObject = new Command("add");
    commnandObject.addArgument("number1").typeof("Integer").condition((i) -> 1 <= i && i >= 100 );
    commandObject.addArgument("number2").typeof("Integer").condition((i) -> 1 <= i && i >= 100 );;
    commandObject.input(arguments);
    Int number1 = commandObject.get("number1");
    Int number2 = commandObject.get("number2");
    return new result.Success<>(Map.of("left", number1, "right", number2));
}
```
Sub function
```java
private static Result<Map<String, Object>> sub(String arguments) {
    var commandObject = new Command("sub");
    commnandObject.addArgument("number1").typeof("Integer");
    commandObject.addArgument("number2").typeof("Integer");
    commandObject.input(arguments);
    Int number1 = commandObject.get("number1");
    Int number2 = commandObject.get("number2");
    return new result.Success<>(Map.of("left", number1, "right", number2));
    }
```

fizzbuzz function
```java
private static Result<Map<String, Object>> fizzbuzz(String arguments) {
    var commandObject = new Command("fizzbuzz");
    commnandObject.addArgument("number1").typeof("integer").condition((i) -> 0 <= i && i <= 100);
    commandObject.input(arguments);
    Int fizz = commandObject.get("number1");
    return new Result.Success<>(Map.of("number", fizz));
    }
```

difficulty function
```java
private static Result<Map<String, Object>> difficulty(String arguments) {
    var commandObject = new Command("difficulty");
    commnandObject.addArgument("difficulty").typeof("string").condition((i) -> i == "easy" || i == "normal" || i == "hard" || i == "peaceful");
    commandObject.input(arguments);
    String difficulty = commandObject.get("difficulty");
    return new Result.Success<>(Map.of("difficulty", difficulty));
    }
```

echo function
```java
private static Result<Map<String, Object>> echo(String arguments) {
    var commandObject = new Command("echo");
    commandObject.addArgument("message").typeof("String").optional().default("Echo, echo, echo...");
    message = commandObject.get("message");
    return new Result.Success<>(Map.of("message", message));
    }
```

search function
```java
private static Result<Map<String, Object>> search(String arguments) {
    var commandObject = new Command("search");
    commandObject.addArgument("term").typeof("String").positional();
    commandObject.addArgument("case-insensitive").typeof(Boolean).optional().default(False)
    term = commandObject.get("term");
    sensitivity = commandObject.get("case-insensitive");
    return new Result.Success<>(Map.of("term", term, "case-insensitive", sensitivity));
    
```

weekday function

```java
import java.time.LocalDate;

private static Result<Map<String, Object>> weekday(String arguments) {
    var commandObject = new Command("weekday");
    commandObject.addArgument("date").typeof('string').positional();
    commandObject.input(arguments);
    LocalDate date = LocalDate.parse(commandObject.get("date"));
    return new Result.Success<>(Map.of("date", date));
    }
}

```



