package com.shiddhant.files.ehouseholdservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    public static final String POST_URL = "http://e-household-service.000webhostapp.com/logindata.php";

    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupButton;
    @Bind(R.id.btn_singin_facebook) Button _facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ///////////LOGIN PROCESS/////////
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        ///////////SIGNUP PROCESS/////////
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(i, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        //////////FACEBOOK PROCESS//////////
        _facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),FacebookLoginActivity.class);
                startActivity(i);

            }
        });




    }

    private void login() {
        if (isvaldiate()) {
            return;
        }
        _loginButton.setEnabled(true);


        final String username = _usernameText.getText().toString();
        final String password = _passwordText.getText().toString();

        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...", "Loggin in...", false, false);


        StringRequest request = new StringRequest(Request.Method.POST, POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("shashi_message",""+response);
                if (response.equals("success")) {
                    loading.dismiss();
                    Intent i = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(i);

                }else{
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(),"Username or password may be incorrect",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

    private boolean isvaldiate() {
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();


        if (username.isEmpty() || username.length()<4) {
            _usernameText.setError("must need more then 3 charecter!");
            _usernameText.requestFocus();
            return true;
        } else {
            _usernameText.setError(null);
        }



        if (password.isEmpty() || password.length() < 5) {
            _passwordText.setError("must be greater than 5  charcter ");
            _passwordText.requestFocus();
            return true;
        } else {
            _passwordText.setError(null);
        }


        return false;
    }
}
