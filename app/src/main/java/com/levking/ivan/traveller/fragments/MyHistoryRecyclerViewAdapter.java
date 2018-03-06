package com.levking.ivan.traveller.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.levking.ivan.traveller.R;
import com.levking.ivan.traveller.fragments.HistoryFragment.OnListFragmentInteractionListener;
import com.levking.ivan.traveller.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyHistoryRecyclerViewAdapter.ViewHolder>
{

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final MyHistoryRecyclerViewAdapter extra = this;
    private final FragmentManager manager;
    private boolean onBind;

    public MyHistoryRecyclerViewAdapter(FragmentManager manager,List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.manager=manager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        if(!holder.mItem.state)holder.rowLayout.setBackgroundResource(R.drawable.round_corner_finished);
        else holder.rowLayout.setBackgroundResource(R.drawable.round_corners_planned);
        holder.depCountry.setText(mValues.get(position).depCountryName +  " ");
        holder.destCountry.setText(mValues.get(position).destCountryName);
        holder.depCountryFlag.setImageResource(mValues.get(position).depCountry.getFlag());
        holder.destCountryFlag.setImageResource(mValues.get(position).destCountry.getFlag());
        onBind=true;
        holder.checkBox.setChecked(holder.mItem.state);
        onBind=false;
        holder.delFromHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValues.remove(position);
//                extra.notifyItemChanged(position, holder.mItem);
                extra.notifyDataSetChanged();
            }
        });
        holder.editFromHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle listItemInfo = new Bundle();
                DummyItem dummyItem = mValues.get(position);
                listItemInfo.putLong("depDate",dummyItem.depDate);
                listItemInfo.putLong("destDate",dummyItem.destDate);
                listItemInfo.putString("depCountry",dummyItem.depCountry.getName());
                listItemInfo.putString("destCountry",dummyItem.destCountry.getName());
                listItemInfo.putString("id", dummyItem.id);
                listItemInfo.putBoolean("state", dummyItem.state);
                manager.beginTransaction().replace(R.id.content_main, CountryFragment.newInstance(listItemInfo)).commit();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    //manager.beginTransaction().replace(R.id.content_main, DialogListItemFragment.newInstance()).commit();
                    DialogListItemFragment dialogListItemFragment = new DialogListItemFragment();
                    dialogListItemFragment.setDisplayItem(mValues.get(position));
                    dialogListItemFragment.show(manager, "dialog");
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!onBind) {
                    holder.mItem.state = !holder.mItem.state;
//                    extra.notifyItemChanged(position, holder.mItem);
                    extra.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView depCountry;
        public final TextView destCountry;
        public final ImageView depCountryFlag;
        public final ImageView destCountryFlag;
        public final ImageButton delFromHistoryButton;
        public final ImageButton editFromHistoryButton;
        public final ConstraintLayout rowLayout;
        public final CheckBox checkBox;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            depCountry = (TextView) view.findViewById(R.id.id);
            destCountry = (TextView) view.findViewById(R.id.content);
            depCountryFlag = (ImageView) view.findViewById(R.id.imageView4);
            destCountryFlag = (ImageView) view.findViewById(R.id.imageView5);
            delFromHistoryButton = (ImageButton) view.findViewById(R.id.imageButton3);
            editFromHistoryButton = (ImageButton) view.findViewById(R.id.imageButton4);
            rowLayout = (ConstraintLayout)view.findViewById(R.id.row_layout);
            checkBox = (CheckBox)view.findViewById(R.id.checkbox1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + destCountry.getText() + "'";
        }
    }

}
