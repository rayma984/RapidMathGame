package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);

        lstScores = (ListView) findViewById(R.id.lstScores);
        strAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lstScores.setAdapter(strAdapter);
    }

    public void getLocal(View view){
        //read from the Scores.txt file and populate the listview with entries from that file
        try {
            //open the file for reading
            FileInputStream fis = getApplicationContext().openFileInput(getString(R.string.filename)); //code from android documentation
            InputStreamReader Streamreader = new InputStreamReader(fis, StandardCharsets.UTF_8);

            //read individual lines and add them to the listView
            BufferedReader reader = new BufferedReader(Streamreader);
            String line = reader.readLine();
            while(line != null){
                strAdapter.add(line);

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            debug("File not Found");
        } catch (IOException e) {
            debug("IOException");
        }

    }

    public void debug(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}