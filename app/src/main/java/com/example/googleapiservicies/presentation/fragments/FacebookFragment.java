package com.example.googleapiservicies.presentation.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googleapiservicies.R;
import com.example.googleapiservicies.databinding.FragmentFacebookBinding;
import com.example.googleapiservicies.model.User;
import com.example.googleapiservicies.presentation.activities.auth.AuthContract;

public class FacebookFragment extends Fragment implements AuthContract.AuthCallback {

    private AuthContract.AuthListener mListener;
    private FragmentFacebookBinding mBinding;

    public FacebookFragment() {
        // Required empty public constructor
    }

    public static FacebookFragment newInstance() {
        return new FacebookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_facebook, container, false);
        mBinding.setHandler(this);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AuthContract.AuthListener) {
            mListener = (AuthContract.AuthListener) context;
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

    @Override
    public void showData(User user) {
        mBinding.setUser(user);
    }

    public void facebookReturn() {
        mListener.openScreen(AuthContract.AuthFlow.DEFAULT);
    }

    public void getSocialAuth() {
        if (mListener != null) mListener.socialAuth(AuthContract.AuthFlow.FACEBOOK, this);
    }
}