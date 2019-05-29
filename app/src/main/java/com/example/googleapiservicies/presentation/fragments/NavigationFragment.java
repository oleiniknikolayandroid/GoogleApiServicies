package com.example.googleapiservicies.presentation.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googleapiservicies.R;
import com.example.googleapiservicies.presentation.activities.auth.AuthContract;

import com.example.googleapiservicies.databinding.FragmentNavigationBinding;



public class NavigationFragment extends Fragment  {

    private AuthContract.AuthListener mListener;


    public NavigationFragment() {
        // Required empty public constructor
    }
    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNavigationBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_navigation, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AuthContract.AuthListener) {
            mListener = ( AuthContract.AuthListener) context;
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

    public void getOpenFragment(AuthContract.AuthFlow flow){
        switch (flow){
            case GOOGLE:
                mListener.openScreen(AuthContract.AuthFlow.GOOGLE);
                break;
            case FACEBOOK:
                mListener.openScreen(AuthContract.AuthFlow.FACEBOOK);
                break;
        }
    }
}