# Object-Oriented CLI Chess Engine

An Object-Oriented Chess game engine implemented in Java featuring piece movement strategy patterns, game logging observers, board state evaluations, custom exception handling, and JSON data parsing.

## Core Features

- **Object-Oriented Architecture:** Clear class abstractions for `Board`, `Piece`, `Move`, `Player`, `Account`, and `User`.
- **Strategy & Design Patterns:** Dynamic move validation rules per piece type (`KingMoveStrategy`, `RookMoveStrategy`, `BishopMoveStrategy`, etc.), Factory pattern for piece creation, and Observer pattern for game logging.
- **Data Parsing:** Utility routines to load player accounts and initial game states directly from JSON configuration files (`accounts.json`, `games.json`).
- **Custom Exception Handling:** Robust validation throwing specific errors (`InvalidMoveException`, `InvalidCommandException`) for illegal moves or CLI inputs.

## Compilation & Execution

1. Compile all Java source files:

bash
javac -d bin src/*.java


2. Run the application:

bash
java -cp bin Main


## License

MIT License
