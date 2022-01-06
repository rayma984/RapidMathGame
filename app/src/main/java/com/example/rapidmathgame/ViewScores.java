package com.example.rapidmathgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ViewScores extends AppCompatActivity {

    ListView lstScores;
    ArrayAdapter<String> strAdapter;
    Spinner spTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);

        lstScores = (ListView) findViewById(R.id.lstScores);
        strAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lstScores.setAdapter(strAdapter);

        spTime = (Spinner) findViewById(R.id.spScoreTime);
    }

    public void getLocal(View view){
        //read from the Scores.txt file and populate the listview with entries from that file
        try {
            //open the file for reading
            FileInputStream fis = getApplicationContext().openFileInput(getString(R.string.filename)); //code from android documentation
            InputStreamReader Streamreader = new InputStreamReader(fis, StandardCharsets.UTF_8);

            //reset the listview
            strAdapter.clear();

            //read individual lines and add them to the listView
            BufferedReader reader = new BufferedReader(Streamreader);
            String line = reader.readLine();

            //if the file is empty
            if(line == null){
                debug("No Scores Recorded");
            }

            while(line != null){
                strAdapter.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            debug("No local scores");
        } catch (IOException e) {
            debug("Trouble Fetching Scores");
        }

    }

    public void debug(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void getGlobal(View view){
        strAdapter.clear();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SCORES");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //loop through each score on the database and add it to the listview--
                if(task.getResult().getChildren() != null){
                    for (DataSnapshot child: task.getResult().getChildren()){
                        String entry = child.getValue(String.class);
                        strAdapter.add(entry);
                    }
                }
                else{
                    debug("Trouble Reaching the Global Scores");
                }
            }
        });
    }
}