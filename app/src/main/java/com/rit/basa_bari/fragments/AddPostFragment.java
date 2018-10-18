package com.rit.basa_bari.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rit.basa_bari.R;
import com.rit.basa_bari.post_activity.AdsFlatPostActivity;
import com.rit.basa_bari.post_activity.AdsHostelPostActivity;
import com.rit.basa_bari.post_activity.AdsMessPostActivity;
import com.rit.basa_bari.post_activity.AdsSub_letPostActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPostFragment extends Fragment {
    private  static final String TAG=AddPostFragment.class.getSimpleName();
    LinearLayout messLinearLayout,hostelLinearLayout,FlatLinearLayout,subletLinearLayout;



    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_add_post, container, false );
        messLinearLayout=view.findViewById( R.id.messLinearLayout );
        hostelLinearLayout=view.findViewById( R.id.hostelLinearLayout );
        FlatLinearLayout=view.findViewById( R.id.flatLinearLayout );
        subletLinearLayout=view.findViewById( R.id.subletLinearLayout );


        messLinearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), AdsMessPostActivity.class ) );
            }
        } );
        hostelLinearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), AdsHostelPostActivity.class ) );

            }
        } );


        FlatLinearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), AdsFlatPostActivity.class ) );
            }
        } );

        subletLinearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), AdsSub_letPostActivity.class ) );
            }
        } );


        Log.e(TAG,"value");
        return view;
    }

}
