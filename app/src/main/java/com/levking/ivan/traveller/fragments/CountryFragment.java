package com.levking.ivan.traveller.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.levking.ivan.traveller.R;
import com.levking.ivan.traveller.utility.TransferInfo;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

//TODO: refactor extremely bad code on listeners, try make MVP

public class CountryFragment extends Fragment {

    ImageView depCountryFlag;
    ImageView destCountryFlag;
    TextView depCountryName;
    TextView destCountryName;
    ImageButton depDateButton;
    ImageButton destDateButton;
    TextView depDate;
    TextView destDate;
    Button nextButton;
    Calendar dateAndTime = Calendar.getInstance();
    TransferInfo transferInfo;

    private OnFragmentInteractionListener mListener;

    public CountryFragment() {
        super();
    }

    public static CountryFragment newInstance(@Nullable Bundle bundle ) {
        CountryFragment fragment = new CountryFragment();
        if(bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_country, container, false);
        depCountryFlag = rootView.findViewById(R.id.imageView3);
        destCountryFlag = rootView.findViewById(R.id.imageView2);
        depCountryName = rootView.findViewById(R.id.textView5);
        destCountryName = rootView.findViewById(R.id.textView4);
        depDateButton = rootView.findViewById(R.id.imageButton);
        destDateButton = rootView.findViewById(R.id.imageButton2);
        depDate = rootView.findViewById(R.id.editText);
        destDate = rootView.findViewById(R.id.editText2);
        nextButton = rootView.findViewById(R.id.button);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        depCountryFlag.setOnClickListener(new DepViewOnClickListener());
        depCountryName.setOnClickListener(new DepViewOnClickListener());
        destCountryFlag.setOnClickListener(new DestViewOnClickListener());
        destCountryName.setOnClickListener(new DestViewOnClickListener());
        depDateButton.setOnClickListener(new DateDepButtonOnClickListener());
        destDateButton.setOnClickListener(new DateDestButtonOnClickListener());
        depDate.setOnClickListener(new DateDepButtonOnClickListener());
        destDate.setOnClickListener(new DateDestButtonOnClickListener());

        transferInfo = new TransferInfo();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle transferInfo = setTransferInfoToConfirm();
                if(transferInfo.getLong("depDate")>transferInfo.getLong("destDate"))showDialog();
                else getFragmentManager().beginTransaction().replace(R.id.content_main, ConfirmFragment.newInstance(setTransferInfoToConfirm())).commit();
            }
        });
        depDate.setText(DateUtils.formatDateTime(getActivity(),Calendar.getInstance().getTime().getTime(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        destDate.setText(DateUtils.formatDateTime(getActivity(),Calendar.getInstance().getTime().getTime()+24*14*60*60*1000,DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        if(getArguments()!=null){
            transferInfo = new TransferInfo(getArguments());
            depCountryName.setText(transferInfo.depCountry.getName());
            destCountryName.setText(transferInfo.destCountry.getName());
            depCountryFlag.setImageResource(transferInfo.depCountry.getFlag());
            destCountryFlag.setImageResource(transferInfo.destCountry.getFlag());
            this.depDate.setText(DateUtils.formatDateTime(getActivity(), transferInfo.depDate.getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            this.destDate.setText(DateUtils.formatDateTime(getActivity(), transferInfo.destDate.getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
        }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class DepViewOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(final View view){
            final CountryPicker picker = getShengenCountryList();
            picker.setListener(new CountryPickerListener() {
                @Override
                public void onSelectCountry(String s, String s1, String s2, int i) {
                    depCountryFlag.setImageResource(i);
                    depCountryName.setText(s);
                    transferInfo.depCountry = Country.getCountryByName(s);
                    picker.dismiss();
                }
            });
            picker.show(getFragmentManager(),"COUNTRY_PICKER");
        }
    }

    private class DestViewOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(final View view){
            final CountryPicker picker = getShengenCountryList();
            picker.setListener(new CountryPickerListener() {
                @Override
                public void onSelectCountry(String s, String s1, String s2, int i) {
                    destCountryFlag.setImageResource(i);
                    destCountryName.setText(s);
                    transferInfo.destCountry = Country.getCountryByName(s);
                    picker.dismiss();
                }
            });
            picker.show(getFragmentManager(),"COUNTRY_PICKER");
        }
    }

    private class DateDepButtonOnClickListener implements ImageButton.OnClickListener{

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                transferInfo.depDate = dateAndTime.getTime();
                depDate.setText(DateUtils.formatDateTime(getActivity(),dateAndTime.getTimeInMillis(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            }
        };


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            new DatePickerDialog(getActivity(),dateSetListener,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private class DateDestButtonOnClickListener implements ImageButton.OnClickListener{

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                transferInfo.destDate = dateAndTime.getTime();
                destDate.setText(DateUtils.formatDateTime(getActivity(),dateAndTime.getTimeInMillis(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            }
        };


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            new DatePickerDialog(getActivity(),dateSetListener,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH)).show();

        }
    }

    private CountryPicker getShengenCountryList(){
        CountryPicker picker = CountryPicker.newInstance("Select Country");
        Country[] countries = {Country.getCountryByName("Austria"),
        Country.getCountryByName("Belgium"),
        Country.getCountryByName("Czech Republic"),
        Country.getCountryByName("Denmark"),
        Country.getCountryByName("Estonia"),
        Country.getCountryByName("Finland"),
        Country.getCountryByName("France"),
        Country.getCountryByName("Germany"),
        Country.getCountryByName("Greece"),
        Country.getCountryByName("Hungary"),
        Country.getCountryByName("Iceland"),
        Country.getCountryByName("Italy"),
        Country.getCountryByName("Latvia"),
        Country.getCountryByName("Liechtenstein"),
        Country.getCountryByName("Lithuania"),
        Country.getCountryByName("Luxembourg"),
        Country.getCountryByName("Malta"),
        Country.getCountryByName("Netherlands"),
        Country.getCountryByName("Norway"),
        Country.getCountryByName("Poland"),
        Country.getCountryByName("Portugal"),
        Country.getCountryByName("Slovakia"),
        Country.getCountryByName("Slovenia"),
        Country.getCountryByName("Spain"),
        Country.getCountryByName("Sweden"),
        Country.getCountryByName("Switzerland")
        };
        picker.setCountriesList(Arrays.asList(countries));
        return picker;
    }

    private Bundle setTransferInfoToConfirm(){
        Bundle bundle = new Bundle();
        bundle.putString("depCountry", this.transferInfo.depCountry.getName() );
        bundle.putString("destCountry", this.transferInfo.destCountry.getName());
        bundle.putLong("depDate",this.transferInfo.depDate.getTime());
        bundle.putLong("destDate",this.transferInfo.destDate.getTime());
        bundle.putBoolean("state",this.transferInfo.state);
        if(getArguments()!=null)bundle.putString("id",getArguments().getString("id"));
        return bundle;
    }

//    private class TransferInfo{
//
//        public Country depCountry;
//        public Country destCountry;
//        public Date depDate;
//        public Date destDate;
//        public boolean state;
//
//        public TransferInfo(){
//            this.depCountry = Country.getCountryByName("Russia");
//            this.destCountry = Country.getCountryByName("Germany");
//            this.depDate = new Date(Calendar.getInstance().getTime().getTime());
//            this.destDate = new Date(Calendar.getInstance().getTime().getTime()+14*24*60*60*1000);
//        }
//
//        public TransferInfo(Country depCountry, Country destCountry, Date depDate, Date destDate, boolean state){
//            this.depCountry = depCountry;
//            this.destCountry = destCountry;
//            this.depDate = depDate;
//            this.destDate = destDate;
//            this.state = state;
//        }
//    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Set correct date");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
