Rock-paper-scissors (-lizard-Spock)
===================================
  - [About this project](#about-this-project)
    - [Minimum Viable Product](#minimum-viable-product)
  - [How to build the project or a distribution](#how-to-build-the-project-or-a-distribution)
  - [How to play the game](#how-to-play-the-game)
    - [Precondition](#precondition)
    - [Usage](#usage)
    - [Examples](#examples)
      - [Play the classic game (player vs computer)](#play-the-classic-game-player-vs-computer)
      - [Let the computer play against itself (computer vs computer)](#let-the-computer-play-against-itself-computer-vs-computer)
      - [Play the lizard-Spock game variant (player vs computer)](#play-the-lizard-spock-game-variant-player-vs-computer)
  - [How to implement and add other game variants](#how-to-implement-and-add-other-game-variants)
  - [Outstanding improvements](#outstanding-improvements)
  - [UML Class Diagram](#uml-class-diagram)
  - [Project statistics](#project-statistics)
  - [Code coverage](#code-coverage)
  - [Project dependencies](#project-dependencies)
  - [Choosing Spock+Groovy over JUnit+Hamcrest+Mockito](#choosing-spockgroovy-over-junithamcrestmockito)

About this project
------------------
This application let's you play the famous [Rock-paper-scissors](https://en.wikipedia.org/wiki/Rock-paper-scissors) game
and one of its variants called [Rock-paper-scissors-lizard-Spock](https://en.wikipedia.org/wiki/Rock-paper-scissors#Additional_weapons).

It is implemented in [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (requirement), tested with [Spock](https://github.com/spockframework/spock) using
[Groovy](http://www.groovy-lang.org/), and build by [Gradle](https://gradle.org/). Why? [See below](#choosing-spockgroovy-over-junithamcrestmockito)!

### Minimum Viable Product
I would consider the [MVP](https://en.wikipedia.org/wiki/Minimum_viable_product) to be a version that ships only the
classic Rock-paper-scissors game. This means also that there would be:
* no `GameRegistry` for registering game variants
* no usage message depending on the game variants (see class `CommandLineParser`)
* no support for multiple game rules per game item (see class `GameRules`)

How to build the project or a distribution
-------------------------------------------
Depending on if you want to
* merely compile the project and execute its tests (`test`),
* create a locally installed distribution (`installDist`), or
* make distributables as TAR and ZIP files (`assemble`)

you need to run the Gradle Wrapper on your command line with the according tasks:
```
./gradlew clean test|installDist|assemble
```

How to play the game
--------------------

### Preconditions
1. Make sure your JAVA_HOME environment variable points to your
[Java SE](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installation.

2. Make sure you have a locally installed Rock-paper-scissors distribution under:
```
./build/install/rockPaperScissors
```

If there is none, create it by running:
```
./gradlew clean installDist
```

### Usage
Run the following script on your command line:
```
./build/install/rockPaperScissors/bin/rockPaperScissors
```

You will then see the following output:
```
usage: rockPaperScissors [<game variant>] <game item>
Game variants and game items:
  classic: [computer, rock, paper, scissors]
  lizard-spock: [computer, rock, paper, scissors, lizard, spock]

If the game variant is omitted, 'classic' will be used as default.
```

### Examples

#### Play the classic game (player vs computer)
You want to play the classic game with "scissors" as your game item:
```
./build/install/rockPaperScissors/bin/rockPaperScissors scissors
```

#### Let the computer play against itself (computer vs computer)
See how good the computer is at beating itself ;):
```
./build/install/rockPaperScissors/bin/rockPaperScissors computer
```

#### Play the lizard-Spock game variant (player vs computer)
Yeah, our "paper" disproves Spock:
```
./build/install/rockPaperScissors/bin/rockPaperScissors lizard-spock paper
```

How to implement and add other game variants
--------------------------------------------
Game variants can be implemented by subclassing `Game` and adding `Enum`s as game items. See e.g. the `LizardSpockGame`
and its additional game items in `LizardSpockGameItem`.

If you want to support the computer vs. computer gaming mode, your `Enum` has to contain a `computer` game item.

The game variant needs than to be added in the `GameRegistry`.

Outstanding improvements
------------------------
* provide a nice GUI
* consider using [Commons CLI](https://commons.apache.org/proper/commons-cli/) for parsing the command line
* make the item the computer chooses more random
* let [Lombok](https://projectlombok.org/) generate getters/setters, `equals()`, `hashCode()`, `toString()`, ...

UML Class Diagram
-----------------
![Rock Paper Scissors Class Diagram](src/main/resources/images/rock-paper-scissors-class-diagram.png?raw=true)

(Generated using [PlantUML](http://plantuml.com).)

Project statistics
------------------
...can be generated by using:
```
./gradlew stats
```

The current statistics are:
```
+----------------------+-------+-------+
| Name                 | Files |  LOC  |
+----------------------+-------+-------+
| Java Sources         |    13 |   501 |
| Groovy Test Sources  |    10 |   708 |
+----------------------+-------+-------+
| Totals               |    23 |  1209 |
+----------------------+-------+-------+
```

Code coverage
-------------
A Java Code Coverage ([JaCoCo](http://www.eclemma.org/jacoco/index.html)) HTML report can be generated by using:
```
./gradlew [clean test] jacocoTestReport
```

The report can then be displayed by opening the file:
```
./build/reports/jacoco/test/html/index.html
```

If the file is not there, you need to run `./gradlew [clean] test jacocoTestReport`.

Note: As my IDE has generated the `equals()`, `hashCode()`, and `toString()` methods I have not tested those explicitly. Thus,
the code coverage will be below 100% most often.

The current code coverage is:

![Rock Paper Scissors Code Coverage](src/main/resources/images/rock-paper-scissors-code-coverage.png?raw=true)

Project dependencies
--------------------
...can be queried by using:
```
./gradlew -q dependencies
```

The current dependencies are (besides Java SE 7, or higher):
```
+--- org.codehaus.groovy:groovy-all:2.4.4
+--- org.spockframework:spock-core:1.0-groovy-2.4
|    +--- org.codehaus.groovy:groovy-all:2.4.1 -> 2.4.4
|    \--- junit:junit:4.12
|         \--- org.hamcrest:hamcrest-core:1.3
+--- cglib:cglib-nodep:3.1
\--- com.blogspot.toomuchcoding:spock-subjects-collaborators-extension:1.1.0
     +--- org.codehaus.groovy:groovy-all:2.3.8 -> 2.4.4
     +--- junit:junit:4.12 (*)
     \--- org.spockframework:spock-core:0.7-groovy-2.0 -> 1.0-groovy-2.4 (*)

\--- org.jacoco:org.jacoco.ant:0.7.5.201505241946
     +--- org.jacoco:org.jacoco.core:0.7.5.201505241946
     |    \--- org.ow2.asm:asm-debug-all:5.0.1
     +--- org.jacoco:org.jacoco.report:0.7.5.201505241946
     |    +--- org.jacoco:org.jacoco.core:0.7.5.201505241946 (*)
     |    \--- org.ow2.asm:asm-debug-all:5.0.1
     \--- org.jacoco:org.jacoco.agent:0.7.5.201505241946
```

Choosing Spock+Groovy over JUnit+Hamcrest+Mockito
-------------------------------------------------
If you are not familiar with it, writing tests with Spock in Groovy for a Java production code base may seem strange at first.

As now two programming languages are in the game, complexity has increased and you have additional dependencies.

BUT: This investment will pay out immediately as your productivity in both maintaining and extending the test code directly
goes up. Your tests become easier to read, easier to understand, and easier to write. All the boilerplate code and clutter
disappears and you can just focus on what you want to achieve - nothing stands between you and the programming language /
test library!

As Groovy is [almost a superset](http://www.groovy-lang.org/differences.html) of Java, it can be
[read and written](http://www.groovy-lang.org/style-guide.html) by any Java developer.

Besides demonstrating the things described I want to show how a migration from Java to Groovy could look like - by
starting writing tests in it. I have experienced that professionally several times and it often worked out so well that
the production code later was also migrated to Groovy, step by step. Most of the Groovy/ex-Java developers I have met feel
that Groovy embodies the Java they always wanted to have!