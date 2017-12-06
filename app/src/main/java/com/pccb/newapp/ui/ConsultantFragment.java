package com.pccb.newapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pccb.newapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultantFragment extends Fragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private OnFragmentInteractionListener mListener;

    public ConsultantFragment() {
        // Required empty public constructor
    }

    public static ConsultantFragment newInstance() {
        ConsultantFragment fragment = new ConsultantFragment();
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consultant, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // App Logo
        //toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        //toolbar.setTitle("投资顾问");
        // Sub Title
        //toolbar.setSubtitle("Sub title");
        //setSupportActionBar(toolbar);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
}
