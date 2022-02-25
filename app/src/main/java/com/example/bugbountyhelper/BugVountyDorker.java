package com.example.bugbountyhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.List;

public class BugVountyDorker extends AppCompatActivity {
    List<String> DorkResList;
    DorkListAdapter Adpt;
    Python py;
    PyObject PyObj,PyDork;
    String Dork;
    EditText ManualDork;
    Switch AutoSwitch;
    Boolean Auto;
    ImageView SearchButt;
    RecyclerView BBRcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_vounty_dorker);

        ManualDork=findViewById(R.id.dorkManual);
        AutoSwitch=findViewById(R.id.autoswitch);
        SearchButt = findViewById(R.id.searchDorkButt);
        BBRcView = findViewById(R.id.BBRcV);
        Dork = new String();

        AutoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ManualDork.setEnabled(false);
                    Auto=true;
                    // The toggle is enabled
                } else {
                    ManualDork.setEnabled(true);
                    Auto=false;
                    // The toggle is disabled
                }
            }
        });

        DorkResList=new ArrayList<>();
        BBRcView.setLayoutManager(new LinearLayoutManager(this));

        if(! Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        py= Python.getInstance();

        PyObj = py.getModule("googledork");

        SearchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DorkResList.clear();
                if (Auto==false) {
                    Dork = ManualDork.getText().toString();
                    if (ManualDork.getText().length() == 0) {
                        Toast.makeText(BugVountyDorker.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    } else {
                        PyObject pydork = PyObj.callAttr("strongDorking", Dork);
                        Log.d("Test", pydork.asList().toString());
                        for (int i = 0; i < pydork.asList().toArray().length; i++) {
                            DorkResList.add(String.valueOf(pydork.asList().get(i)));
                        }
                    }
                } else {
                    String FType = "Dorks/BugBounty.txt";
                    PyObject DorkObj = PyObj.callAttr("randomDork", FType);
                    Log.d("Test", FType + "  ==  " + DorkObj.toString());
                    Dork = DorkObj.toString();
                    Toast.makeText(BugVountyDorker.this, "Dork : "+Dork, Toast.LENGTH_SHORT).show();
                    PyObject pydork = PyObj.callAttr("strongDorking", Dork);
                    Log.d("Test", pydork.asList().toString());
                    if(pydork.asList().size()>0){
                        BBRcView.setBackgroundResource(0);
                    }
                    for (int i = 0; i < pydork.asList().toArray().length; i++) {
                        DorkResList.add(String.valueOf(pydork.asList().get(i)));
                    }
                }
                Adpt = new DorkListAdapter(BugVountyDorker.this, DorkResList);
                Log.d("TEST", "after adpt");
                BBRcView.setAdapter(Adpt);
            }
        });


    }
}