package com.levking.ivan.traveller.fragments;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.levking.ivan.traveller.R;
import com.levking.ivan.traveller.fragments.dummy.DummyContent;
import com.levking.ivan.traveller.utility.TransferInfo;
import com.mukesh.countrypicker.Country;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmFragment extends Fragment {

    private Button CancelB;
    private Button OkB;
    private EditText commentary;
    private ImageView depFlag;
    private TextView depCountryName;
    private TextView depDate;
    private ImageView destFlag;
    private TextView destCountryName;
    private TextView destDate;
    private TransferInfo transferInfo;

    private OnFragmentInteractionListener mListener;

    public ConfirmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConfirmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmFragment newInstance(@Nullable Bundle args) {
        ConfirmFragment fragment = new ConfirmFragment();
        if(args!=null)fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_confirm, container, false);
        OkB = rootView.findViewById(R.id.button3);
        CancelB = rootView.findViewById(R.id.button2);
        commentary = rootView.findViewById(R.id.editText4);
        depFlag = rootView.findViewById(R.id.imageView8);
        destFlag = rootView.findViewById(R.id.imageView9);
        depCountryName =rootView.findViewById(R.id.textView6);
        depDate =rootView.findViewById(R.id.textView7);
        destCountryName =rootView.findViewById(R.id.textView8);
        destDate =rootView.findViewById(R.id.textView9);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle SavedInstanceState){
        super.onActivityCreated(SavedInstanceState);
        transferInfo = new TransferInfo(getArguments());
        depFlag.setImageResource(transferInfo.depCountry.getFlag());
        destFlag.setImageResource(transferInfo.destCountry.getFlag());
        depCountryName.setText(transferInfo.depCountry.getName());
        destCountryName.setText(transferInfo.destCountry.getName());
        destDate.setText(DateUtils.formatDateTime(getActivity(),transferInfo.destDate.getTime(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        depDate.setText(DateUtils.formatDateTime(getActivity(),transferInfo.depDate.getTime(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        commentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentary.selectAll();
            }
        });

        OkB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                    DummyContent.DummyItem newDummy = createDummy();
                    if(getArguments().getString("id")!=null){
                        DummyContent.removeItem(getArguments().getString("id"));
                        DummyContent.addItem(newDummy);
                    }
                    else DummyContent.addItem(newDummy);
                getFragmentManager().beginTransaction().replace(R.id.content_main, HistoryFragment.newInstance(1)).commit();

            }
        });
        CancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, HistoryFragment.newInstance(1)).commit();
            }
        });
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

    private DummyContent.DummyItem createDummy(){
        Bundle bundle = getArguments();
            DummyContent.DummyItem dummyItem = new DummyContent.DummyItem(
                    Country.getCountryByName(bundle.getString("depCountry")),
                    Country.getCountryByName(bundle.getString("destCountry")),
                    commentary.getText().toString(),
                    bundle.getLong("depDate"),
                    bundle.getLong("destDate"),
                    bundle.getBoolean("state")
            );

        return dummyItem;
    }
}
