package com.example.sayamiasis.loginnew;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public final String EXTRA_USER = "bizcom.bizcom.USER";
    private EditText Username;
    private EditText Password;
    private Button Login;
    private TextView Signup; //this is for unregistered users
    String jsonUrl = "http://192.168.1.89:3000/test/api/search";



   /* protected void ToastForEmptyField(boolean val){
        if(val){
            Toast.makeText(this,"empty username", Toast.LENGTH_SHORT).show();
            Username.setError("required field");
        }
        else{
            Toast.makeText(this,"empty password", Toast.LENGTH_SHORT).show();
            Username.setError("required field");
        }
    }
    */
    //RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);


    public boolean isNotNull(String user, String pass) {
        if ((user.length() != 0) && (pass.length() != 0)) {
            //if not null and credentials match
            /*if((user.equals("aashish")) && (pass.equals("sayami"))){
                Intent intent = new Intent(this,SecondActivity.class);
                startActivity(intent);
            */
            return true;
        } else {
            return false;
        }
    }
           /*//if credentials  dont match
            else{
                //we need an alert dialog box here
                AlertDialog.Builder inCorrectAlert = new AlertDialog.Builder(this);
                inCorrectAlert.setMessage("Incorrect password or username");
                inCorrectAlert.setTitle("Error");
                inCorrectAlert.setCancelable(true);
                inCorrectAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                //now create the dialog box
                AlertDialog alert = inCorrectAlert.create();
                //display the AlerDialog
                alert.show();
            }
        }
        //if null
        else{
            //we need an empty field error msg here
            if(TextUtils.isEmpty(user)){  //using null or .equals (null) wasnt compatible with use of space. however this still
                //doesnt help
                //we make a toast here


                 Toast.makeText(getApplicationContext(),"empty username", Toast.LENGTH_SHORT).show();
                 Username.setError("required field");


            }
            if (TextUtils.isEmpty(pass)){
                //next toast here too



                 Toast.makeText(getApplicationContext(),"empty password", Toast.LENGTH_SHORT).show();
                 Password.setError("required field");


            }
        }

    }*/


    public void callSignupAcitvity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText) findViewById(R.id.etUsername);
        Password = (EditText) findViewById((R.id.etPass));
        Login = (Button) findViewById((R.id.btnlogin));
        Signup = (TextView) findViewById((R.id.etSignup));

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callSignupAcitvity(); // this call is compulsory call idk why
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean NotNull = isNotNull(Username.getText().toString(), Password.getText().toString());

                final String name, pass;
                name = Username.getText().toString();
                pass = Password.getText().toString();



                if (NotNull) {

                   /* JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST)

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("userName", name);
                            params.put("password", pass);
                            return super.getParams();
                        }

                    };*/

                   //creating a json object to pass credentials to server
                   JSONObject passCredentials = new JSONObject();
                    try {
                        passCredentials.put("userName", name);
                        passCredentials.put("password", pass);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //String Credentials = passCredentials.toString();


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, jsonUrl, passCredentials, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                           try {
                                //String uName= response.getString("userName");


                                //if credentials dont match
                                response.getBoolean("result");

                                    AlertDialog.Builder inCorrectAlert = new AlertDialog.Builder(MainActivity.this);
                                    inCorrectAlert.setMessage("Incorrect username or password");
                                    inCorrectAlert.setTitle("Error");
                                    inCorrectAlert.setCancelable(true);
                                    inCorrectAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    //now create the dialog box
                                    AlertDialog alert = inCorrectAlert.create();
                                    //display the AlerDialog
                                    alert.show();



                                //if credentials match







                            } catch (JSONException e) {


                                   String user = response.toString();
                                   Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                                   intent.putExtra(EXTRA_USER,user);
                                   startActivity(intent);



                        }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(),"please connect to the internet", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();


                        }
                    });


                    MySingleton.getMinstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

                 //if null
                } else {

                    if (TextUtils.isEmpty(name)) {  //using null or .equals (null) wasnt compatible with use of space. however this still
                        //doesnt help
                        //we make a toast here


                        Toast.makeText(getApplicationContext(), "empty username", Toast.LENGTH_SHORT).show();
                        Username.setError("required field");


                    }
                    if (TextUtils.isEmpty(pass)) {
                        //next toast here too


                        Toast.makeText(getApplicationContext(), "empty password", Toast.LENGTH_SHORT).show();
                        Password.setError("required field");

                    }
                }
            }
        });
    }
}

