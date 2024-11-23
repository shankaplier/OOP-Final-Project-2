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