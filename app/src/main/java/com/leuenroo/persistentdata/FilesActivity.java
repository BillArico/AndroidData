package com.leuenroo.persistentdata;


import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FilesActivity extends AppCompatActivity {
    private final String TAG = "FilesActivity";

    @BindView(R.id.filesetEmail)
    EditText mEmail;

    @BindView(R.id.filesetName)
    EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        ButterKnife.bind(this);
        try {
            writeToInternalFile("");
            String fileContents = readFromInternalFile();
            Log.d(TAG, "File contents = " + fileContents);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void readData(View view) throws IOException {
        mName.setText(   readFromInternalFile()    );
    }

    public void saveData(View view) {


        try {
            writeToInternalFile( mEmail.getText().toString() +" "+ mName.getText().toString() );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToInternalFile(String str) throws IOException {
        FileOutputStream outputStream = openFileOutput("FileNameHere", Context.MODE_PRIVATE);
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println("You wrote in the file "+ str);
        writer.close();
    }

    private String readFromInternalFile() throws IOException {
        FileInputStream inputStream = openFileInput("FileNameHere");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        }
        finally {
            reader.close();
        }

        return stringBuilder.toString();
    }

}