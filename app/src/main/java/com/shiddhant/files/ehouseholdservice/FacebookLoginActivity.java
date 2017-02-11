package com.shiddhant.files.ehouseholdservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton _loginButton;
    AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_login);


        //////////FACEBOOK PLUGIN PROCESS//////////
        //_loginButton.setReadPermissions(Arrays.asList("public_profile,email"));
        _loginButton = (LoginButton) findViewById(R.id.login_button);
        //_loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();

        _loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(getApplicationContext(),loginResult+"",Toast.LENGTH_LONG).show();


                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try{
                            String name = object.getString("name");
                            String id = object.getString("id");
                            String email = object.getString("email");
                            Toast.makeText(getApplicationContext(),name+" "+id+" "+email,Toast.LENGTH_LONG).show();
                        }catch (Exception e){

                        }



                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("fields","id,name,email,gender");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();


                ////////////LOGOUT PROCESSS///////////
                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                        if(currentAccessToken == null){
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                    }
                };

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

