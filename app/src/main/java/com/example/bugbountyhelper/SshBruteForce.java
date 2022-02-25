package com.example.bugbountyhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class SshBruteForce extends AppCompatActivity {
    Dialog Prompt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssh_brute_force);
        EditText IpAddrs,UidInput;
        TextView Password_Output=findViewById(R.id.OutputScreen);
        RadioGroup FileInput=findViewById(R.id.filebuttgroup);

        Prompt = new Dialog(this);

        ImageView PerformButt = findViewById(R.id.performbutt);
        IpAddrs=findViewById(R.id.ipInput);
        UidInput=findViewById(R.id.UIdInput);

        setUpPrompt();
        TextView L_Title = Prompt.findViewById(R.id.Loading_Title);
        TextView L_Content = Prompt.findViewById(R.id.Loading_Content);


        if(! Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("sshbf");

        PerformButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Password_Output.setText(" ");
                Log.d("IdPassTest",pyobj.get("idpass").asList().toString());
                int selectedId = FileInput.getCheckedRadioButtonId();
                RadioButton ChosenFile = (RadioButton) findViewById(selectedId);
                if(IpAddrs.getText().equals("")||UidInput.getText().equals("")||selectedId==-1){
                    Toast.makeText(SshBruteForce.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                }else {
                    if(selectedId==R.id.ebutt){
                        Toast.makeText(SshBruteForce.this, "Easy Selected", Toast.LENGTH_SHORT).show();
                        pyobj.put("input_file","SshPass/LowPass.txt");
                        L_Content.setText("Using Short Password List");
                    }else {
                        Toast.makeText(SshBruteForce.this, "Hard Selected", Toast.LENGTH_SHORT).show();
                        pyobj.put("input_file","SshPass/HighPass.txt");
                        L_Content.setText("Using Long Password List");
                    }

                    L_Title.setText(".. Bruteforce in Progress ..");
                    Prompt.show();

                    Log.d("IdPassTest",ChosenFile.getText().toString());
                    pyobj.put("host", IpAddrs.getText().toString());
                    pyobj.put("username", UidInput.getText().toString());
                    Log.d("IdPassTest","==oo==");

                    PyObject SshBfOpt= pyobj.callAttr("bruteforce");
//                    Log.d("TEST", pyobj.callAttr("bruteforce").toString());
                    String Op="User Id : "+String.valueOf(SshBfOpt.asList().get(0))+"\nPassword : "+SshBfOpt.asList().get(1);
                    Password_Output.setText(Op);
                    Prompt.dismiss();
                }



            }
        });


    }

    private void setUpPrompt() {
        Prompt.setContentView(R.layout.loading_dialog);
        Prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Prompt.setCancelable(false);
    }
}