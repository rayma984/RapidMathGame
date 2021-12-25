package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostGame extends AppCompatActivity {

    EditText txtName;
    CheckBox chbxOnline;
    CheckBox chbxLocal;
    GameSession session;
    TextView lblScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);

        //set the UI variables
        txtName = findViewById(R.id.txtName);
        chbxOnline = findViewById(R.id.checkOnline);
        chbxLocal = findViewById(R.id.chbxLocal);
        lblScore = findViewById(R.id.lblScore);

        //get the info from the previous activity
        session = (GameSession) getIntent().getSerializableExtra("session");
        lblScore.setText("Score: " + session.getPlayerScore());
    }

    public void submit(View view){
        //if the player does not want to submit their score
        if(!chbxLocal.isChecked() && !chbxOnline.isChecked()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //get the name
        String name = txtName.getText().toString();
        Pattern p = Pattern.compile("[a-z]{3}|[A-Z]{3}");
        Matcher m = p.matcher(name);

        //invalid input name. Display the error message and reset the EditText
        if(!m.matches()){
            String msg = "Invalid Name";
            debug(msg);
            txtName.setText("");
        }//valid name input
        else {
            //upload the score to the firebase
            if (chbxOnline.isChecked()) {

            }

            //store the data in a file
            if (chbxLocal.isChecked()) {
                File scoresFile = new File("Scores.txt");
                //create the file if it doesnt already exist
                try {
                    if(!scoresFile.createNewFile()){

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileWriter writer = new FileWriter(scoresFile, true);

                    //write the data
                    session.makePlayer(name);
                    writer.write(session.getPlayerName() + " SCORE: " + session.getPlayerScore());
                    writer.close();
                } catch (IOException e) {
                    debug("Error with files");
                }

            }
        }
    }

    public void debug(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}