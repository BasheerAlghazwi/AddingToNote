package com.example.android.adingtonotelist;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class AddActivity extends AppCompatActivity {

    private TextView NOTE;
    private EditText LINK;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtodo);

        NOTE = findViewById(R.id.NOTE);
        LINK = findViewById(R.id.link);
    }

    @Override
    public void onBackPressed() {
        if(!NOTE.getText().toString().trim().equals(null) && !LINK.getText().toString().trim().equals(null)) {
            Intent data = new Intent();
            data.putExtra("NOTE", NOTE.getText().toString().trim());
            data.putExtra("LINK", LINK.getText().toString().trim());
            setResult(RESULT_OK, data);
            finish();
        }
        finish();
    }
}