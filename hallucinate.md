# Will
Add
```java
void add(String input) {
    var command = new Command("add");
    command.argument("left").positional().parser(new IntegerParser());
    command.argument("right").positional().parser(new IntegerParser());
    var arguments = command.parse(input);
    int left = arguments.get("left");
    int right = arguments.get("right");
}
```
Sub
```java
void sub(String input) {
    var command = new Command("sub");
    command.argument("left").named().parser(new IntegerParser());
    command.argument("right").named().parser(new IntegerParser());
    var arguments = command.parse(input);
    int left = arguments.get("left");
    int right = arguments.get("right");
}
```
fizzbuzz
```java
void fizzbuzz(String input) {
    var command = new Command("fizzbuzz");
    command.argument("number").parser(new IntegerParser()).require(i -> 1 <= i && i <= 100);
    var arguments = command.parse(input);
    int number = arguments.get("number");
}
```
difficulty
```java
void difficulty(String input) {
    var command = new Command("difficulty");
    command.argument("difficulty").positional().choices("easy", "normal", "hard", "peaceful");
    var arguments = command.parse("input");
    String difficulty = arguments.get("difficulty");
}
```
echo
```java
void echo(String input) {
    var command = new Command("echo");
    command.argument("message").positional().optional().defaultValue("Echo, echo, echo...");
    var arguments = command.parse(input);
    String message = arguments.get("message");
}
```
search
```java
void search(String input) {
    var command = new Command("search");
    command.argument("term");
    command.argument("case-insensitive").named().optional().defaultValue(false);
    var arguments = command.parse(input);
    String term = arguments.get("term");
    bool caseSensitive = arguments.get("case-insensitive");
}
```
weekday
```java
void weekday(String input) {
    var command = new Command("weekday");
    command.argument("date").parser(new CustomParser(LocalDate::parse));
    var arguments = command.parse(input);
    LocalDate date = arguments.get("date");
}
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



