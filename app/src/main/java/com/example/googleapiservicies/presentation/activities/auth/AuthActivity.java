package com.example.googleapiservicies.presentation.activities.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.request.RequestOptions;
import com.example.googleapiservicies.R;
import com.example.googleapiservicies.databinding.ActivityAuthBinding;
import com.example.googleapiservicies.model.User;
import com.example.googleapiservicies.presentation.activities.auth.Adapter.AuthPagerAdapter;
import com.example.googleapiservicies.presentation.fragments.FacebookFragment;
import com.example.googleapiservicies.presentation.fragments.GoogleFragment;
import com.example.googleapiservicies.presentation.fragments.NavigationFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class AuthActivity extends AppCompatActivity implements AuthContract.AuthListener {

    private static final int RC_SIGN_IN = 0;
    private static final int OPEN_GOOGLE_FRAGMENT = 1;
    private static final int OPEN_FACEBOOK_FRAGMENT = 2;
    private static final String TAG = "AuthActivity";
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private AuthContract.AuthCallback mFlowCallback;
    private CallbackManager authCallBackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private AuthPagerAdapter mAuthAdapter;
    ViewPager mPager;

    static final int PAGE_COUNT = 3;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
       // openScreen(AuthContract.AuthFlow.DEFAULT);
        mPager = (ViewPager) findViewById(R.id.pager);
        mAuthAdapter = new AuthPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAuthAdapter);
//        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.d(TAG, "onPageSelected, position = " + position);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset,
//                                       int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        authCallBackManager = CallbackManager.Factory.create();
        binding.loginBtnAuthFacebook.setReadPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE));
        binding.loginBtnAuthFacebook.registerCallback(authCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        authCallBackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);
                User user = new User();
                user.setmEmail(account.getEmail());
                user.setmName(account.getGivenName());
                user.setnSurnme(account.getFamilyName());
                user.setmUrl(String.valueOf(account.getPhotoUrl()));
                if (mFlowCallback != null) mFlowCallback.showData(user);
                mFlowCallback = null;
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    User user = new User();
                    user.setmEmail(email);
                    user.setmName(first_name);
                    user.setnSurnme(last_name);
                    user.setmUrl(image_url);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                    if (mFlowCallback != null) mFlowCallback.showData(user);
                    mFlowCallback = null;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parametrs = new Bundle();
        parametrs.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parametrs);
        request.executeAsync();
    }

    @Override
    public void socialAuth(AuthContract.AuthFlow type, AuthContract.AuthCallback callback) {
        switch (type) {
            case GOOGLE:
                mFlowCallback = callback;
                // todo use function with logic startActivityForResult login Google
                Intent googleAuthIntent = new Intent(AuthActivity.this, GoogleFragment.class);
                startActivityForResult(googleAuthIntent, OPEN_GOOGLE_FRAGMENT);
                break;
            case FACEBOOK:
                mFlowCallback = callback;
                // todo use function with logic startActivityForResult login Facebook
                Intent facebookAuthIntent = new Intent(AuthActivity.this, FacebookFragment.class);
                startActivityForResult(facebookAuthIntent, OPEN_FACEBOOK_FRAGMENT);
                break;
        }
    }

    @Override
    public void openScreen(AuthContract.AuthFlow type) {
        switch (type) {
            case FACEBOOK:
                getOpenScreen(FacebookFragment.newInstance());
                break;
            case GOOGLE:
                getOpenScreen(GoogleFragment.newInstance());
                break;
            case DEFAULT:
                getOpenScreen(NavigationFragment.newInstance());
                break;
        }
    }

    private void getOpenScreen(Fragment fragment) {
        //get fragment manager
        getSupportFragmentManager()
                .beginTransaction()// open transaction for bind fragment
                .addToBackStack("Auth") // for back stack
                .replace(R.id.auth_container, fragment) // get arial for bind fragment (R.id.auth_container) and fragment
                .commit();
    }
}