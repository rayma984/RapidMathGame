package com.example.rapidmathgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostGame extends AppCompatActivity {

    EditText txtName;
    CheckBox chbxOnline;
    CheckBox chbxLocal;
    GameSession session;
    TextView lblScore;
    String mode;

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
        mode = getIntent().getStringExtra("mode");
        lblScore.setText("Score: " + session.getPlayerScore());
    }

    public void submit(View view){
        //if the player does not want to submit their score, send them back to the start screen
        if(!chbxLocal.isChecked() && !chbxOnline.isChecked()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //get the name
        String name = txtName.getText().toString();
        Pattern p = Pattern.compile("[a-z]{5}|[A-Z]{5}");
        Matcher m = p.matcher(name);

        //invalid input name. Display the error message and reset the EditText
        if(!m.matches()){
            String msg = "Invalid Name";
            debug(msg);
            txtName.setText("");
        }//valid name input
        else {
            //get the name (capitalized)
            name = name.toUpperCase();

            session.makePlayer(name);
            String output = session.toString() + " - " + mode;
            //upload the score to the firebase
            if (chbxOnline.isChecked()) {
                //set the reference to the proper time section
                String timeSection = String.valueOf(session.getTime());
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(timeSection);

                //add the new entry to the firebase
                addEntry(timeSection, output);

                debug("Score Uploaded");
            }

            //store the data in a file
            if (chbxLocal.isChecked()) {
                writeToFile(getString(R.string.filename), output);
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void debug(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void addEntry(String timeSection, String output){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(timeSection);
        ArrayList<String> entries = new ArrayList<>();

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //loop through each score on the database and add it to the arraylist
                if(task.getResult().getChildren() !=null){
                    for (DataSnapshot child: task.getResult().getChildren()){
                        String entry = child.getValue(String.class);
                        entries.add(entry);
                    }
                }
                else{
                    debug("Trouble Reaching the Global Scores");
                }
                //add the new entry, sort, and upload it to the firebase
                entries.add(output);
                sortEntries(entries);
                ref.setValue(entries);
            }
        });
    }

    //bubble sort the entries (into descending order)
    private void sortEntries(ArrayList<String> entries){
        for(int i = 0; i< entries.size() -1; i++){
            for(int j = 0; j < entries.size() -1 -i; j++){
                //define our terms of comparison
                String s1 = entries.get(j).split(" ")[0];
                String s2 = entries.get(j+1).split(" ")[0];
                int n1 = Integer.parseInt(s1);
                int n2 = Integer.parseInt(s2);

                if(n1 < n2){
                    //reverse the orders of n1 and n2
                    String temp = entries.get(j);
                    entries.set(j, entries.get(j+1));
                    entries.set(j+1, temp);
                }
            }
        }
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
            debug("Cant access scores----");
        } catch (IOException e) {
            debug("Issue Recording Score");
        }
    }

}