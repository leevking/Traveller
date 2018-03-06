package com.levking.ivan.traveller.utility;

import android.os.Bundle;

import com.mukesh.countrypicker.Country;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ivan on 05.03.18.
 */

public class TransferInfo {

    public Country depCountry;
    public Country destCountry;
    public Date depDate;
    public Date destDate;
    public boolean state;

    public TransferInfo(){
        this.depCountry = Country.getCountryByName("Russia");
        this.destCountry = Country.getCountryByName("Germany");
        this.depDate = new Date(Calendar.getInstance().getTime().getTime());
        this.destDate = new Date(Calendar.getInstance().getTime().getTime()+14*24*60*60*1000);
    }

    public TransferInfo(Country depCountry, Country destCountry, Date depDate, Date destDate, boolean state){
        this.depCountry = depCountry;
        this.destCountry = destCountry;
        this.depDate = depDate;
        this.destDate = destDate;
        this.state = state;
    }


    public TransferInfo(Bundle args){
        this.depCountry =Country.getCountryByName(args.getString("depCountry"));
        this.destCountry =Country.getCountryByName(args.getString("destCountry"));
        this.depDate =new Date(args.getLong("depDate"));
        this.destDate=new Date(args.getLong("destDate"));
        this.state=args.getBoolean("state");
    }
}

