package com.example.bugbountyhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;

public class PortScanner extends AppCompatActivity {
    Dialog Prompt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_scanner);

        Prompt = new Dialog(this);
        ImageView ScanButt = findViewById(R.id.scanbutt);
        EditText Range = findViewById(R.id.PortRange);
        EditText ipaddr=findViewById(R.id.ipaddr);

        ArrayList<PortModel> PModel=new ArrayList<>();
        RecyclerView PortView = findViewById(R.id.IPRcV);
        PortView.setLayoutManager(new LinearLayoutManager(this));

        setUpPrompt();
        TextView L_Title = Prompt.findViewById(R.id.Loading_Title);
        TextView L_Content = Prompt.findViewById(R.id.Loading_Content);

        if(! Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();

        PyObject pyobj = py.getModule("port");

        ScanButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PModel.clear();

                L_Title.setText("Scanning Ports ...");
                L_Content.setText("-- / --");
                Prompt.show();

                if(ipaddr.getText().toString().equals("") || Range.getText().toString().equals("")||Range.getText().toString().split("-").length == 1) {
                    Toast.makeText(PortScanner.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    Prompt.dismiss();
                }
                else {
                    String[] st= Range.getText().toString().split("-");
                    for (int i = Integer.valueOf(st[0]);i<=Integer.valueOf(st[1]);i++){
//                        Ld.setContent(String.valueOf(i)+"/"+st[1]);
                        L_Content.setText(String.valueOf(i)+" / "+st[1]);
                    PyObject objport = pyobj.callAttr("scan", ipaddr.getText().toString(),i);
                    Log.d("TEST", String.valueOf(objport.asList()));

                    if(String.valueOf(objport.asList().get(1)).equals("OPEN")) {
                        PModel.add(new PortModel(String.valueOf(i), String.valueOf(objport.asList().get(1)), String.valueOf(objport.asList().get(2)),String.valueOf(objport.asList().get(3)), R.drawable.open));
                    }else{
                        PModel.add(new PortModel(String.valueOf(i), String.valueOf(objport.asList().get(1)), String.valueOf(objport.asList().get(2)),String.valueOf(objport.asList().get(3)), R.drawable.lock));
                    }
                    Log.d("TEST", PModel.toString());
                    //                   Toast.makeText(PortScanner.this, objport.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
        Log.d("TEST", "before adpt"+PModel.toString());
        if(PModel.size()>0){
            PortView.setBackgroundResource(0);
        }
        PortRCAdapter adpt=new PortRCAdapter(PortScanner.this,PModel);
        Log.d("TEST", "after adpt");
        PortView.setAdapter(adpt);
        Prompt.dismiss();
        Log.d("TEST", "done");
            }
        });


    }
    private void setUpPrompt() {
        Prompt.setContentView(R.layout.loading_dialog);
        Prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Prompt.setCancelable(false);
    }
}