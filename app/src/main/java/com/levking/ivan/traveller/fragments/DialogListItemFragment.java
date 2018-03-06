package com.levking.ivan.traveller.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.levking.ivan.traveller.R;
import com.levking.ivan.traveller.fragments.dummy.DummyContent;
import com.mukesh.countrypicker.Country;


public class DialogListItemFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private static DummyContent.DummyItem displayItem;
    private TextView depCountryName;
    private TextView depDate;
    private ImageView depFlag;
    private TextView destCountryName;
    private TextView destDate;
    private ImageView destFlag;
    private TextView details;

    public DialogListItemFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DialogListItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogListItemFragment newInstance() {
        DialogListItemFragment fragment = new DialogListItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_list_item, container, false);
        depCountryName = rootView.findViewById(R.id.depCountryName);
        depFlag = rootView.findViewById(R.id.depFlag);
        depDate = rootView.findViewById(R.id.depDate);
        destCountryName = rootView.findViewById(R.id.destCountryName);
        destFlag = rootView.findViewById(R.id.destFlag);
        destDate = rootView.findViewById(R.id.destDate);
        details = rootView.findViewById(R.id.details);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle SavedInstanceState){
        super.onActivityCreated(SavedInstanceState);
        depFlag.setImageResource(displayItem.depCountry.getFlag());
        depCountryName.setText(displayItem.depCountry.getName());
        depDate.setText(DateUtils.formatDateTime(getActivity(),displayItem.depDate,DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        destFlag.setImageResource(displayItem.destCountry.getFlag());
        destCountryName.setText(displayItem.destCountry.getName());
        destDate.setText(DateUtils.formatDateTime(getActivity(),displayItem.destDate,DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        details.setText(displayItem.details);
    }

    @Override
    public void onResume(){
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        super.onResume();
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

    public static void setDisplayItem(DummyContent.DummyItem dummyItem){
        displayItem = dummyItem;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
