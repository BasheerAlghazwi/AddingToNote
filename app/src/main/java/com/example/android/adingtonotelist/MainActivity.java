package com.example.android.adingtonotelist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final ArrayList<String> myarrayList = new ArrayList<String>();
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);


        Map<String, ?> map = getPreferences(this.MODE_PRIVATE).getAll();

        if(map.isEmpty()) {
            for(int i = 0; i < (Integer) map.get("SIZE"); i++) {
                myarrayList.add((String) map.get("NOTE" + i));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.todo_layout, myarrayList);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setMessage("Do you want to remove this?\n\n" + myarrayList.get(position) + " will be removed!").setTitle("DELETE");

                        builder.setTitle("DELETE");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myarrayList.remove(position);

                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this, R.layout.todo_layout, myarrayList);

                                list.setAdapter(adapter1);
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                        return true;
                    }
                }
        );
    }

    public void addOnPressed(View v) {
        Intent i = new Intent(this, AddActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        myarrayList.add(data.getStringExtra("NOTE"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.todo_layout, myarrayList);

        list.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences.Editor e = getPreferences(this.MODE_PRIVATE).edit();
        Map<String, ?> map = getPreferences(this.MODE_PRIVATE).getAll();

        if(map.isEmpty()) {
            e.putString("INITIAL_LAUNCH", "String");
        }

        for(int i = 0; i < myarrayList.size(); i++) {
            e.putString("Note" + i, myarrayList.get(i));
        }

        e.putInt("SIZE", myarrayList.size());

        e.apply();
    }
}