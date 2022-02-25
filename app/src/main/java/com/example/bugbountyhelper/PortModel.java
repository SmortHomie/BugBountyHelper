package com.example.bugbountyhelper;

import android.graphics.drawable.Drawable;

import java.time.chrono.IsoChronology;

public class PortModel {
    String port_no,Banner;
    String PortDetails,IsOpen;
    int D;

    public PortModel(String port_no, String IsOpen, String PortDetails,String Banner, int D){
        this.port_no=port_no;
        this.IsOpen= IsOpen;
        this.Banner=Banner;
        this.PortDetails=PortDetails;
        this.D=D;
    }

}
