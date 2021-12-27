package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
            //get the name
            session.makePlayer(name);

            //upload the score to the firebase
            if (chbxOnline.isChecked()) {

            }

            //store the data in a file
            if (chbxLocal.isChecked()) {
                String output = session.getPlayerName() + " : " + session.getPlayerScore();
                writeToFile(getString(R.string.filename), output);
            }
        }
    }

    public void debug(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void writeToFile(String fileName, String line){
        File scoresFile = new File(getApplicationContext().getFilesDir(), fileName);
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(fileName, Context.MODE_APPEND);
            line += "\n";
            fos.write(line.getBytes(StandardCharsets.UTF_8));
            fos.close();
            debug("Score Recorded");
        } catch (FileNotFoundException e) {
            debug("File not found");
        } catch (IOException e) {
            debug("IOException");
        }
    }

}