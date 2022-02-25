package com.example.bugbountyhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class LoadingDialog {

    private Activity act;
    private AlertDialog Ad;
    private View v;
    private AlertDialog.Builder builder;

    LoadingDialog(Activity myAct){
        act=myAct;
    }

    void startLoadingDialog(){
       builder = new AlertDialog.Builder(act);

        LayoutInflater inflater =act.getLayoutInflater();
        v=inflater.inflate(R.layout.loading_dialog,null);
        builder.setView(v);

        builder.setCancelable(false);

        Ad=builder.create();
        Ad.show();

    }
    void dismissDialog(){
        Ad.dismiss();
    }
}
