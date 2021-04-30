# guessTheNumber

![Okno tytułowe aplikacji](https://i.imgur.com/azsBnKi.png)

This is game where user's goal is to guess random generated number is as least round as possible.

![](https://img.shields.io/github/last-commit/Shirobachi/guessTheNumber)
![Intelij 2021.1](https://img.shields.io/badge/Intelij-2021.1-blue)
![SDK 16](https://img.shields.io/badge/SDK-16-blue)
![JAVAFX 16](https://img.shields.io/badge/JAVAFX-16-blue)
![mysql-connector-java 8.0.24](https://img.shields.io/badge/mysql--connector--java-8.0.24-blue)
![](https://img.shields.io/github/languages/top/Shirobachi/guessTheNumber)
![](https://img.shields.io/tokei/lines/github/Shirobachi/guessTheNumber)

---

# Gameplay

1. Player make choice or leave easy level

![](https://i.imgur.com/c9K82MU.png)

2. Click start, so program generate number what they will try to guess

3. Player make a guess so program let know if guess if greater or smaller than the lucky number. At the same time show range where lucky number is.

![](https://i.imgur.com/8R6dUI1.png)

4. After 3 or 5 guesses player can get a free tip what is letting know about number's dividing

![](https://i.imgur.com/3IDn1oe.png)

5. Player make good guess, so game make congrats for them

![](https://i.imgur.com/pIzPyRM.png)

6. Player can add they nickname to the scoreboard

![](https://i.imgur.com/UYlHxeJ.png)

## Scoreboard

The scoreboard's data are holded in database what you can make by

```sql
CREATE TABLE `guessTheNumber` (
 `ID` int(11) NOT NULL AUTO_INCREMENT,
 `Name` varchar(15) NOT NULL,
 `isEasy` tinyint(1) NOT NULL,
 `Points` int(11) NOT NULL,
 PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1
```

The java connecting to DB is in `dbconnection` file

![Image not loaded](https://i.imgur.com/zqqKeKL.png)

## Helping by getting tip

After 3 (on easy mode) or 5 (or hard mode) failure guesses player can ask for help.

![](https://imgur.com/AltrGzY.png)

## Range where lucky number is

At start game make two variables `smaller` and `bigger` both inicialized to `-1`. Every time when user make a guess one of them is updated and range label is refreshed using those too:

![](https://imgur.com/vgOtrsr.png)

## Adding new winner

When player win two components are show up, when player hit enter is checking provided name and if it's good it's sending to database

![](https://imgur.com/Z3YjC9N.png)
