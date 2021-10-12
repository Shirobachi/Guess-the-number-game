package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

public class Controller implements Initializable {
    @FXML private Button start;
    @FXML private Button check;
    @FXML private Labeled from;
    @FXML private Labeled to;
    @FXML private Labeled info;
    @FXML private TextField guess;
    @FXML private Labeled weak;
    @FXML private Labeled afterMessage;
    @FXML private Labeled tip;
    @FXML private Button easy;
    @FXML private Button hard;
    @FXML private TextField submitName;
    @FXML private Button submit;
    @FXML private Labeled scoreboard;

    Integer luckyNumber;
    Integer point = 0;
    Integer bigger = -1, smaller = -1;
    Boolean isEasy = true;

    public void start() {

        start.setVisible(false);
        from.setVisible(true);
        to.setVisible(true);
        check.setVisible(true);
        guess.setVisible(true);
        easy.setVisible(false);
        hard.setVisible(false);


        if(isEasy){
            from.setText("From 1");
            to.setText("to 100");
            luckyNumber = (int) ((Math.random() * 99) + 1);
        }
        else{
            from.setText("From 1");
            to.setText("to 1000");
            luckyNumber = (int) ((Math.random() * 999) + 1);
        }

        point = 0;
        bigger = -1;
        smaller = -1;

        afterMessage.setVisible(false);
        guess.setText("");

        info.setText("Make a guess!");
        System.out.println(luckyNumber.toString()); //TODO
    }

    public void _refreshTip() {
        String message = "";

        if (smaller == -1 && bigger != -1)
            message = "The number > " + bigger.toString();
        else if (smaller != -1 && bigger == -1)
            message = "The number < " + smaller.toString();
        else
            message = "The number ∈ ( " + bigger.toString() + "; " + smaller.toString() + " )";

        tip.setText(message);
    }

    public void check() {
        Integer guessedNumer = 0;

        //check if number!
        try {
            guessedNumer = Integer.parseInt(guess.getText());
        } catch (NumberFormatException e) {
            info.setText("You're crazy..");
        }

        //check if >= 0 && <= 100
        if ((guessedNumer < 1 || guessedNumer > 100) && isEasy)
            info.setText("This number should be b/w 1 and 100..");
        else if ((guessedNumer < 1 || guessedNumer > 1000) && !isEasy)
            info.setText("This number should be b/w 1 and 1000..");
        else {
            point++;

            if ((point >= 3 && isEasy) || (point >= 5 && !isEasy))
                weak.setVisible(true);
            if (guessedNumer > luckyNumber) {
                info.setText("A bit too much!");
                if (guessedNumer > smaller && smaller != -1)
                    info.setText("This number is stupid!");
                else
                    smaller = guessedNumer;
                _refreshTip();
            } else if (guessedNumer < luckyNumber) {
                info.setText("It's too little!");
                if (guessedNumer < bigger && bigger != -1)
                    info.setText("This number is stupid!");
                else
                    bigger = guessedNumer;
                _refreshTip();
            } else {
                info.setText("YEAAH I meant that number!");
                weak.setVisible(false);
                from.setVisible(false);
                to.setVisible(false);
                guess.setVisible(false);
                check.setVisible(false);

                String message = "";
                message += "Yeah you did it in ";
                message += point;
                message += point == 1 ? " round!\n" : " rounds!\n";
                message += "The " + luckyNumber + " is commensurate by \n";

                Integer count = 0;
                for (int i = 1; i < 100; i++)
                    if (luckyNumber % i == 0) {
                        message += i + ", ";
                        count++;
                    }

                message = message.substring(0, message.length() - 2) + "\n";

                afterMessage.setVisible(true);
                afterMessage.setText(message);
                tip.setText("");

                start.setVisible(true);
                start.setLayoutY(250);

                easy.setVisible(true);
                hard.setVisible(true);

                submit.setVisible(true);
                submitName.setVisible(true);
            }
        }
    }

    public void tipMe() {
        for (int i = 2; i < luckyNumber; i++) {
            if (luckyNumber % i == 0) {
                info.setText("This number is dividable by " + i);
                break;
            }
            if (i == luckyNumber - 1)
                info.setText("You not deserved for that..");
        }
    }

     public void addNewRecord() throws SQLException {

        dbConnection link = new dbConnection();
        Connection linkDB = link.getConnection();

        String q = "INSERT INTO guessTheNumber SET name = \"";
        q += submitName.getText();
        q += "\", Points = ";
        q += point.toString();
        q += ", isEasy = ";
        q += isEasy;

        Statement statement = linkDB.createStatement();
        statement.executeUpdate(q);
    }

     public void getRecord() throws SQLException {

        dbConnection link = new dbConnection();
        Connection linkDB = link.getConnection();

        String q = "SELECT Name, Points from guessTheNumber WHERE isEasy = ";
        q += isEasy;
        q += " order by Points limit 10";

        //TODO: show person: 1 3 5

        Statement statement = linkDB.createStatement();
        ResultSet results =  statement.executeQuery(q);

        String message = "";
        while(results.next()){
            message += results.getString(1);
            message += ": ";
            message += results.getString(2);
            message += "\n";
        }

        scoreboard.setText(message);
    }

    public void changeLevel(boolean toHard){
        easy.setUnderline(!easy.isUnderline());
        hard.setUnderline(!hard.isUnderline());
        isEasy = !isEasy;

        try {
                getRecord();
            } catch (SQLException throwables) {
                info.setText("DB connection == false!");
            }
    }

    public void changeToEasy() {
        changeLevel(false);
    }

    public void changeToHard() {
        changeLevel(true);
    }

    public void submitOnClick(){
        if(submitName.getText().length() <= 0 )
            info.setText("Wrong name, too short!");
        else if(submitName.getText().length() > 15)
            info.setText("Wrong name, too looong!");
        else{
            try {
                addNewRecord();
                submitName.setVisible(false);
                submit.setVisible(false);
                getRecord();
            } catch (SQLException throwables) {
                info.setText("DB connection == false!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getRecord();
        }
        catch (SQLException throwables) {
            info.setText("DB connection == false!");
        }

        afterMessage.setText("Welcome my friend, \nAre you ready for a super kiper babik game?" );
    }
}
