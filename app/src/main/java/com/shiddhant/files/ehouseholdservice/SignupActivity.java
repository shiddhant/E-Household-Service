package com.shiddhant.files.ehouseholdservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
public class SignupActivity extends AppCompatActivity {
    private static final String POST_URL = "http://e-household-service.000webhostapp.com/postdata.php";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_fullname) TextView _fullname;
    @Bind(R.id.input_address) TextView _address;
    @Bind(R.id.input_email) TextView _emailText;
    @Bind(R.id.input_phone_no) TextView _phone_no;
    @Bind(R.id.input_username) TextView _username;
    @Bind(R.id.input_password) TextView _password;
    @Bind(R.id.btn_signup) TextView _signupButton;
    @Bind(R.id.link_login) TextView _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivityForResult(i,REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup() {
        if(validate()){
            return;
        }

        _signupButton.setEnabled(true);

        if(isSignupSuccess()){
            final ProgressDialog progressDialog = ProgressDialog.show(this,"Please wait...","Starting register process...",false,false);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    progressDialog.dismiss();
                }
            },3000);
        }else{
            isSignupfailed();
        }

    }

    private void isSignupfailed() {

    }

    private boolean isSignupSuccess() {
        String email = _emailText.getText().toString();
        String phone_no = _phone_no.getText().toString();
        String username = _username.getText().toString();
        String password = _password.getText().toString();

        insert(email,phone_no,username,password);


        return false;
    }

    private void insert(final String email, final String phone_no, final String username, final String password) {

        StringRequest request = new StringRequest(Request.Method.POST, POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                   Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            protected Map<String,String>getParams() throws AuthFailureError{
                Map<String,String> map = new HashMap<>();
                map.put("email",email);
                map.put("phone_no",phone_no);
                map.put("username",username);
                map.put("password",password);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private boolean validate() {
        String email = _emailText.getText().toString();
        String phone_no = _phone_no.getText().toString();
        String username = _username.getText().toString();
        String password = _password.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("email not valid");
            _emailText.requestFocus();
            return true;
        } else {
            _emailText.setError(null);
        }

        if(phone_no.isEmpty()){
            _phone_no.setError("phone no. can't blank");
            return true;
        }else{
            _phone_no.setError(null);
        }

        if(username.isEmpty() && username.length()<4 || username.length()>10){
            _username.setError("username must be alphanumeric between 4 to 10");
            return true;
        }else{
            _username.setError(null);
        }

        if(password.isEmpty() && password.length()<4 || password.length()>10 ){
            _password.setError("password must be alphanumeric between 4 to 10");
            return true;
        }else{
            _password.setError(null);
        }


        return false;
    }
}
