# Arkanoid Game

A classic Arkanoid/Breakout arcade game clone implemented in Java. This project demonstrates object-oriented programming principles and various design patterns to create a modular and extensible game engine.

## Project Overview

The game features a paddle, a ball, and walls of blocks. The objective is to destroy all the blocks by bouncing the ball off the paddle without letting it fall off the bottom of the screen. The game supports multiple levels with different layouts and difficulties.

### Key Features
*   **Multiple Levels**: Support for defining different level layouts, backgrounds, and difficulties.
*   **Physics Engine**: Custom collision detection and response system.
*   **Score Tracking**: Keeps track of the player's score throughout the game.
*   **Animations**: Smooth 60 FPS gameplay with pause screens and end-game screens.
*   **Sound Effects**: Audio feedback for game events.

## Design Patterns

The project utilizes several standard software design patterns to maintain clean code and separation of concerns:

### 1. Observer Pattern
Used for handling game events, specifically collisions.
*   **Subject**: `Block` (implements `HitNotifier`).
*   **Observer**: `HitListener` interface.
*   **Implementation**: Classes like `BlockRemover`, `BallRemover`, and `ScoreTrackingListener` observe blocks. When a ball hits a block, the block notifies these listeners to perform actions like removing the block, removing the ball, or updating the score.

### 2. Strategy Pattern
Used to define level-specific behaviors and data.
*   **Strategy Interface**: `LevelInformation`.
*   **Context**: `GameLevel`.
*   **Implementation**: Different levels (e.g., `ClassicLevel`) implement `LevelInformation` to provide specific configurations (background, block layout, paddle speed, number of balls) without changing the core game logic in `GameLevel`.

### 3. Factory Pattern
Used to simplify object creation.
*   **Factory**: `BlockFactory`.
*   **Implementation**: Provides static methods to create complex arrangements of blocks (like rows or patterns) with consistent properties, abstracting the instantiation logic from the client code.

### 4. Game Loop Pattern
The core driver of the game's execution.
*   **Implementation**: `AnimationRunner` class.
*   **Details**: The runner executes a loop that handles input, updates game state, and renders the frame at a consistent frame rate (60 FPS). It delegates the specific frame logic to `Animation` objects.

### 5. Composite Pattern
Used to manage game objects uniformly.
*   **Component**: `Sprite` interface.
*   **Composite**: `SpriteCollection`.
*   **Leafs**: `Ball`, `Block`, `Paddle`.
*   **Implementation**: `SpriteCollection` holds a list of `Sprite` objects. When `drawOn` or `timePassed` is called on the collection, it iterates and calls the method on all its children, allowing the game loop to treat a group of objects as a single entity.

### 6. Decorator Pattern
Used to add functionality to animations dynamically.
*   **Component**: `Animation` interface.
*   **Decorator**: `KeyPressStoppableAnimation`.
*   **Implementation**: Wraps an existing `Animation` (like `EndScreen` or `PauseScreen`) and adds the behavior of waiting for a specific key press to exit, without modifying the original animation class.

### 7. Singleton Pattern
Used to ensure a class has only one instance and provide a global point of access to it.
*   **Singleton**: `ResourceManager`.
*   **Implementation**: The `ResourceManager` class has a private constructor and a static `getInstance()` method. It manages shared resources like sound clips to ensure they are loaded only once and reused, saving memory and processing time.

### 8. Facade Pattern
Used to provide a simplified interface to a complex subsystem.
*   **Facade**: `GameFlow`.
*   **Implementation**: The `GameFlow` class encapsulates the complexity of initializing levels, running the game loop, handling level transitions, and displaying the end screen. The main application (`Ass5Game`) interacts with this simple interface rather than managing the individual components of the game cycle.

## Educational Concepts

This project serves as a practical example for learning Object-Oriented Programming (OOP) and software engineering principles. It demonstrates how to build a scalable and maintainable system using standard design patterns.

### OOP Principles
*   **Encapsulation**: 
    *   Fields in classes like `Ball`, `Block`, and `GameLevel` are `private`, preventing direct access from outside.
    *   State is modified only through public methods (e.g., `ball.setVelocity(v)`), ensuring the object maintains a valid state.
*   **Polymorphism**: 
    *   The `SpriteCollection` iterates over a list of `Sprite` objects. It calls `timePassed()` and `drawOn()` on each, without knowing if the sprite is a `Ball`, `Block`, or `Paddle`.
    *   The `GameEnvironment` treats all obstacles as `Collidable`, allowing the physics engine to handle collisions uniformly.
*   **Abstraction**: 
    *   Complex subsystems are hidden behind simple interfaces. The `AnimationRunner` runs an `Animation` loop without knowing the details of the specific animation (Game, Pause Screen, End Screen).
    *   `LevelInformation` abstracts the configuration of a level, so `GameLevel` can execute any level layout without code changes.
*   **Inheritance vs. Composition**: 
    *   The project favors **Composition over Inheritance**. For example, `GameLevel` *has* a `SpriteCollection` and a `GameEnvironment` rather than inheriting from them.
    *   Interfaces are used extensively to define capabilities (`Collidable`, `Sprite`, `HitListener`) rather than relying on a deep class hierarchy.

### SOLID Principles
*   **Single Responsibility Principle (SRP)**: 
    *   Each class has a single purpose. `Block` holds the state of a block. `BlockRemover` is responsible *only* for removing blocks when hit. `ScoreManager` is responsible *only* for tracking the score.
*   **Open/Closed Principle (OCP)**: 
    *   The game is open for extension. You can add a new type of `HitListener` (e.g., one that plays a sound or spawns a power-up) and attach it to blocks without modifying the `Block` class or the collision logic.
    *   New levels can be added by implementing `LevelInformation` without changing `GameLevel`.
*   **Liskov Substitution Principle (LSP)**:
    *   Any implementation of `Sprite` (e.g., `Ball`, `Paddle`) can be substituted into the `SpriteCollection` and the game will continue to function correctly.
*   **Interface Segregation Principle (ISP)**: 
    *   Interfaces are small and specific. `Collidable` defines collision behavior, while `Sprite` defines rendering behavior. An object can be a `Sprite` (like a background image) without being `Collidable`, avoiding unnecessary method implementations.
*   **Dependency Inversion Principle (DIP)**: 
    *   High-level modules depend on abstractions. `GameLevel` depends on the `LevelInformation` interface, not on specific level classes like `ClassicLevel`. This allows easy swapping of levels.

### Java Concepts
*   **Interfaces**: 
    *   Used to define contracts (`Sprite`, `Collidable`, `HitListener`). This decouples the implementation from the usage, allowing for flexible code.
*   **Collections Framework**: 
    *   `java.util.List`, `ArrayList`, and `LinkedList` are used to manage dynamic collections of game objects (sprites, collidables, listeners).
    *   `java.util.Map` is used in `ResourceManager` for caching resources.
*   **Event Handling (Listener Pattern)**: 
    *   The project implements a custom event system. Objects (Subjects) like `Block` maintain a list of `HitListener`s (Observers) and notify them upon specific events (collisions).
*   **Generics**: 
    *   Used for type safety in collections (e.g., `List<HitListener>`, `List<Sprite>`), preventing runtime type errors.
*   **JavaDoc**: 
    *   The code is documented using standard JavaDoc comments, explaining the purpose of classes and methods, which is a best practice for maintainable code.

## Project Structure

*   `src/arkanoid/game`: Core game logic (GameLevel, GameEnvironment, GameFlow).
*   `src/arkanoid/sprites`: Visual game objects (Ball, Block, Paddle).
*   `src/arkanoid/physics`: Physics engine (CollisionInfo, Velocity, Line, Point).
*   `src/arkanoid/levels`: Level definitions and factories.
*   `src/arkanoid/listeners`: Event listeners for game logic.
*   `src/arkanoid/animation`: Animation framework.
*   `src/arkanoid/ui`: User interface elements.

## How to Run

(Assuming standard Ant build or IDE setup)

1.  Compile the project.
2.  Run the main class `arkanoid.app.Ass5Game`.
