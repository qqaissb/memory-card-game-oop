# Memory Card Matching Game

A Java implementation of a memory card matching game built across three programming paradigms: Procedural, Object-Oriented, and Event-Driven.

## About

All cards start hidden. The player flips two cards per turn — if they match, they stay revealed; if not, they flip back. The game ends when all pairs are found. Cards are based on a Uno card theme with color and letter attributes.

The Fisher-Yates shuffling algorithm is used to randomize card order every game.

## Paradigms Implemented

### Procedural
- Global variables, loops, and conditionals
- Linear flow with reusable methods
- ~160 lines of code
- Limitation: no Uno mode, no graphics, no class support

### Object-Oriented
- Classes: `Card`, `UnoCard`, `Deck`, `UnoDeck`, `Main`
- Encapsulation with getters/setters
- Inheritance: `UnoCard` extends `Card`
- Method overriding and overloading
- Uno mode supported via ArrayLists
- Limitation: console-only, no graphics

### Event-Driven
- Built with Java Swing (`JFrame`, `JButton`)
- Listeners: `ActionListener`, `KeyListener`, `MouseListener`, `MouseMotionListener`
- Full GUI: clickable cards, visual Uno colors, secret button
- ~530 lines of code

## Algorithm

**Fisher-Yates Shuffle** — randomizes card order before each game.
```
for i from 0 to last index:
    j = random integer from 0 to i
    swap cards[i] and cards[j]
```

## Development

- **IDE:** Eclipse
- **Language:** Java
- **Naming conventions:** PascalCase for classes, camelCase for variables and methods
- **Error handling:** try-catch for invalid user input
- **Debugging:** Eclipse breakpoints used throughout development

## Error Types Handled

| Type | Description |
|------|-------------|
| Syntax | Caught by compiler before execution |
| Logical | Wrong output despite running — fixed via debugging |
| Runtime | Crash during execution (e.g. array out of bounds, invalid input) |
