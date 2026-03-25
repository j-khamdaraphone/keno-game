# Keno Game

A JavaFX-based implementation of the classic Keno lottery game. This project creates an interactive GUI that allows players to select numbers, play multiple draws, and track their scores.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Building the Project](#building-the-project)
- [Running the Game](#running-the-game)
- [How to Play](#how-to-play)
- [Technologies](#technologies)
- [Course Information](#course-information)

## Overview

This is a fun implementation of the Keno lottery game using JavaFX. Players select numbers (spots) and then watch as random numbers are drawn. The game calculates winnings based on how many selected numbers match the drawn numbers. The project demonstrates object-oriented programming principles and GUI development with JavaFX.

## Features

- **Interactive GUI** - User-friendly interface built with JavaFX
- **Number Selection** - Select your lucky numbers for each game
- **Multiple Draws** - Play multiple consecutive draws in a single session
- **Score Tracking** - Keep track of your total winnings
- **Game Rules Display** - View Keno rules and odds
- **Random Number Generation** - Automatic random number selection option
- **Welcome Screen** - Introduction screen with game instructions

## Requirements

- Java 11 or higher
- Maven 3.6.0 or higher
- JavaFX SDK 19.0.2.1 or compatible version

## Project Structure

```
JavaFX_MavenTemplate_VS1/
├── pom.xml                          # Maven configuration and dependencies
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── JavaFXTemplate.java       # Main application entry point
│   │   │   ├── KenoGame.java              # Core game logic
│   │   │   ├── KenoMenu.java              # Main menu screen
│   │   │   ├── Welcome.java               # Welcome/splash screen
│   │   │   ├── GamePlayScreen.java        # Main game play interface
│   │   │   ├── GamePlaySpot.java          # Individual number spot component
│   │   │   ├── Odds.java                  # Odds and payouts display
│   │   │   ├── Rules.java                 # Game rules display
│   │   │   └── FontLoader.java            # Custom font utilities
│   │   └── resources/                # Resource files (images, fonts, etc.)
│   └── test/
│       └── java/
│           └── MyTest.java           # Unit tests
└── target/                           # Compiled output
```

## Building the Project

1. Navigate to the project directory:
   ```bash
   cd JavaFX_MavenTemplate_VS1
   ```

2. Build the project using Maven:
   ```bash
   mvn clean compile
   ```

3. Run tests (optional):
   ```bash
   mvn test
   ```

## Running the Game

### Using Maven:

```bash
mvn javafx:run
```

Or compile and run directly:

```bash
mvn clean javafx:run
```

### From the Command Line (after building):

```bash
java -jar target/FXTemplate-0.0.1-SNAPSHOT.jar
```

## How to Play

1. **Welcome Screen** - Start the game and review the welcome message
2. **Select Spots** - Choose how many numbers you want to play (1-10 spots)
3. **Pick Numbers** - Select your lucky numbers from 1-80, or use "Random Pick" for automatic selection
4. **Play Draws** - Specify how many draws you want to play
5. **Watch the Game** - Numbers will be drawn and matched against your selections
6. **See Results** - View your score and any winnings
7. **View Odds** - Check the odds and payout table to understand your chances

## Technologies

- **Language**: Java 11
- **GUI Framework**: JavaFX 19.0.2.1
- **Build Tool**: Maven 4.0.0
- **Testing**: JUnit Jupiter 5.9.1
- **IDE**: Visual Studio Code or IntelliJ IDEA

## Dependencies

- `javafx-controls` - JavaFX UI controls
- `javafx-fxml` - JavaFX FXML support
- `junit-jupiter` - Unit testing framework

## Course Information

- **Course**: CS 342 - Object-Oriented Programming
- **Semester**: Fall 2025
- **Assignment**: Keno Game Project

---

**Enjoy playing Keno!** 🎲
