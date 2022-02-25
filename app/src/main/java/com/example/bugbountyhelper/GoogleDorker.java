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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.List;

public class GoogleDorker extends AppCompatActivity {
    ImageView SearchDork;
    TextView ExpandRes,CancelButt;
    EditText ManualDork,TargetDomain;
    RadioGroup DorkType,DorkMethod,DorkList;
    RadioButton ChosenType,ChosenMethod,F1,F2,F3;
    String Type,Method,Dork;
    Dialog ChooseType,ShowRes;
    List<String> DorkResList;
    DorkListAdapter Adpt;
    Python py;
    PyObject PyObj,PyDork;
    RecyclerView MiniRes,ExapndedRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_dorker);
        ChooseType=new Dialog(this);
        ShowRes=new Dialog(this);
        Type = new String();
        Method = new String();
        Dork = new String();

        if(! Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        py= Python.getInstance();

        PyObj = py.getModule("googledork");

        SetChooseDialog();
        setRcView();


    }

    private void setRcView() {
        DorkResList=new ArrayList<>();
        MiniRes = findViewById(R.id.Mini_Res_view);
        MiniRes.setLayoutManager(new LinearLayoutManager(this));
        ExapndedRes = ShowRes.findViewById(R.id.ExtResView);
        ExapndedRes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void Setbutton() {
        SearchDork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DorkResList.clear();
                Dork=ManualDork.getText().toString();
                if (Method.equals("Manual Dorking")) {
                    if(ManualDork.getText().length()==0){
                        Toast.makeText(GoogleDorker.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    }else {
                        if (Type.equals("Strong Dorking")) {
                            PyObject pydork = PyObj.callAttr("strongDorking",Dork);
                            Log.d("Test",pydork.asList().toString());
                            for(int i =0;i<pydork.asList().toArray().length;i++){
                                DorkResList.add(String.valueOf(pydork.asList().get(i)));
                            }

                        } else {
                            PyObject pydork = PyObj.callAttr("minimalDorking",Dork);
                            Log.d("Test",pydork.asList().toString());
                            for(int i =0;i<pydork.asList().toArray().length;i++){
                                DorkResList.add(String.valueOf(pydork.asList().get(i)));
                            }
                        }
                    }
                }else {
                    int selectedDId = DorkList.getCheckedRadioButtonId();
                    if(TargetDomain.getText().length()==0 || selectedDId==-1){
                        Toast.makeText(GoogleDorker.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    }else{
                        String FType;
                        if(selectedDId==R.id.FileRel){
                            FType ="Dorks/FileRel.txt";
                        }else  if(selectedDId==R.id.ServerRel){
                            FType="Dorks/ServerRel.txt";
                        }else{
                            FType="Dorks/RandomVul.txt";
                        }
                        PyObject DorkObj=PyObj.callAttr("randomDork",FType);
                        Log.d("Test",FType+"  ==  "+DorkObj.toString());
                        Dork="site:"+TargetDomain.getText().toString()+DorkObj.toString();
                        if (Type.equals("Strong Dorking")) {
                            PyObject pydork = PyObj.callAttr("strongDorking",Dork);
                            Log.d("Test",pydork.asList().toString());
                            for(int i =0;i<pydork.asList().toArray().length;i++){
                                DorkResList.add(String.valueOf(pydork.asList().get(i)));
                            }
                        } else {
                            PyObject pydork = PyObj.callAttr("minimalDorking",Dork);
                            Log.d("Test",pydork.asList().toString());
                            for(int i =0;i<pydork.asList().toArray().length;i++){
                                DorkResList.add(String.valueOf(pydork.asList().get(i)));
                            }
                        }
                    }

                }
                Adpt=new DorkListAdapter(GoogleDorker.this,DorkResList);
                Log.d("TEST", "after adpt");
                MiniRes.setAdapter(Adpt);
                ExpandRes.setVisibility(View.VISIBLE);
            }
        });
        ExpandRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST", "after adpt");
                TextView ExTitle = ShowRes.findViewById(R.id.dorktitle);
                ExTitle.setText(" Dork : "+Dork);
                ExapndedRes.setAdapter(Adpt);
                ShowRes.show();
            }
        });
    }


    private void setInputs() {
        SearchDork=findViewById(R.id.SearchDork);
        ExpandRes=findViewById(R.id.ExpandRes);
        ExpandRes.setVisibility(View.INVISIBLE);

        ManualDork=findViewById(R.id.DorkManual);
        TargetDomain=findViewById(R.id.TargetDomain);

        DorkList=findViewById(R.id.DorkOpt);

        F1=findViewById(R.id.FileRel);
        F2=findViewById(R.id.ServerRel);
        F3=findViewById(R.id.RandomVul);

        MiniRes= findViewById(R.id.Mini_Res_view);
        Toast.makeText(this, Method, Toast.LENGTH_SHORT).show();

        if(Method.equals("Manual Dorking")){
            TargetDomain.setEnabled(false);
            F1.setEnabled(false);
            F2.setEnabled(false);
            F3.setEnabled(false);

        }else{
            ManualDork.setEnabled(false);

        }
        Setbutton();
    }

    private void SetChooseDialog() {
        ExpandRes=findViewById(R.id.ExpandRes);
        ExpandRes.setVisibility(View.INVISIBLE);

        ChooseType.setContentView(R.layout.choose_type_dialog);
        ChooseType.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ChooseType.setCancelable(false);

        ShowRes.setContentView(R.layout.expanded_res_dialog);
        ShowRes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ShowRes.setCancelable(false);

        CancelButt = ShowRes.findViewById(R.id.cancelButt);
        CancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRes.dismiss();
            }
        });


        ChooseType.show();

        TextView GotoDork = ChooseType.findViewById(R.id.headtodorkbutt);
        DorkMethod=ChooseType.findViewById(R.id.dorkMethod);
        DorkType=ChooseType.findViewById(R.id.dorkType);



        GotoDork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedMethodId = DorkMethod.getCheckedRadioButtonId();
                int selectedTpeId = DorkType.getCheckedRadioButtonId();
                if(selectedMethodId==-1 || selectedTpeId==-1){
                    Toast.makeText(GoogleDorker.this, "Select Options", Toast.LENGTH_SHORT).show();
                }else{
                ChosenMethod = (RadioButton) ChooseType.findViewById(selectedMethodId);
                ChosenType= (RadioButton) ChooseType.findViewById(selectedTpeId);
                Method=ChosenMethod.getText().toString();
                Type=ChosenType.getText().toString();
                ChooseType.dismiss();
//                    Toast.makeText(GoogleDorker.this,Method+"---"+Type, Toast.LENGTH_SHORT).show();
                }
                setInputs();
            }
        });
        Toast.makeText(this,"-----------"+ Method, Toast.LENGTH_SHORT).show();

    }

    private void ExpandResDialog() {


    }
}