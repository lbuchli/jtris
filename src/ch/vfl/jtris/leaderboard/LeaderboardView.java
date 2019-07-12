package ch.vfl.jtris.leaderboard;

import ch.vfl.jtris.util.Leaderboard;
import ch.vfl.jtris.util.LeaderboardEntry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


import java.io.IOException;

public class LeaderboardView{

    private Button menuButton;
    private Button backButton;
    private Parent root;


    public Scene start() throws IOException{
        root = FXMLLoader.load(getClass().getResource("LeaderboardView.fxml"));
        Scene scene = new Scene(root, 500, 500);

        menuButton = (javafx.scene.control.Button) root.lookup("#menu");
        backButton = (javafx.scene.control.Button) root.lookup("#back");


        // display leaderboard
        getLeaderboard();

        return scene;

    }
    private void getLeaderboard(){
        Leaderboard instance = Leaderboard.getInstance();
        LeaderboardEntry[] leaderboard = instance.getTopEntries(4);

        for(int top = 0; top > 5;) {
            LeaderboardEntry entry = leaderboard[top];
            String data = entry.getRepresentation();

            String[] dataparts = data.split("-,");
            String name = dataparts[0];
            String score = dataparts[1];

            String finaldata = name + " -> " + score;

            switch(top){
                case 0:
                    ((Text) root.lookup("#first")).setText(finaldata);
                case 1:
                    ((Text) root.lookup("#second")).setText(finaldata);
                case 2:
                    ((Text) root.lookup("#third")).setText(finaldata);
                case 3:
                    ((Text) root.lookup("#forth")).setText(finaldata);
                case 4:
                    ((Text) root.lookup("#first")).setText(finaldata);
                case 5:
                    System.out.print("hi!");
            }




        }


    }



}
