package com.example.bugbountyhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {
Dialog Prompt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView PassCrackButt,PortScanButt,DorkButt,SshBFButt,BBDorkButt,XSSButt,SubButt;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Prompt = new Dialog(this);
        PassCrackButt = findViewById(R.id.passcarckhold);
        PortScanButt = findViewById(R.id.portscanhold);
        DorkButt = findViewById(R.id.dorkbutthold);
        BBDorkButt = findViewById(R.id.sshhold2);
        SubButt = findViewById(R.id.subdomainhold);
        XSSButt = findViewById(R.id.xsshold);
        SshBFButt = findViewById(R.id.sshhold);

        if(! Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }


        Python py = Python.getInstance();

//        PyObject pyobj = py.getModule("sshbf");
        DorkButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DorkIntent = new Intent(MainActivity.this, GoogleDorker.class);
                MainActivity.this.startActivity(DorkIntent);
            }
        });

        SshBFButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SshIntent = new Intent(MainActivity.this, SshBruteForce.class);
//              myIntent.putExtra("key", value); //Optional parameters

                MainActivity.this.startActivity(SshIntent);
            }
        });
        XSSButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent XssIntent = new Intent(MainActivity.this, XSS_Finder.class);
//              myIntent.putExtra("key", value); //Optional parameters

                MainActivity.this.startActivity(XssIntent);
            }
        }); SubButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SubIntent = new Intent(MainActivity.this, SubdomainEnum.class);
//              myIntent.putExtra("key", value); //Optional parameters

                MainActivity.this.startActivity(SubIntent);
            }
        }); PassCrackButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HashIntent = new Intent(MainActivity.this, HashCracker.class);
//              myIntent.putExtra("key", value); //Optional parameters

                MainActivity.this.startActivity(HashIntent);
            }
        });
        BBDorkButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BBDorkIntent = new Intent(MainActivity.this, BugVountyDorker.class);
//              myIntent.putExtra("key", value); //Optional parameters

                MainActivity.this.startActivity(BBDorkIntent);
            }
        });
        PortScanButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PortIntent = new Intent(MainActivity.this, PortScanner.class);
//              myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(PortIntent);
            }
        });


    }

    private void openDialog() {

        Prompt.setContentView(R.layout.loading_dialog);
        Prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Prompt.setCancelable(false);

        TextView L_Title = Prompt.findViewById(R.id.Loading_Title);
        TextView L_Content = Prompt.findViewById(R.id.Loading_Content);

        Prompt.show();
    }
}