package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    private Button btnNuevo;
    private final Context context = this;
    private ProcesosPHP php = new ProcesosPHP();;
    private RequestQueue request;
    private EditText user,pass;
    private JsonObjectRequest jsonObjectRequest;
    private String serverip = "https://proyectofinalrcc.000webhostapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.txtUsuario);
        pass=findViewById(R.id.txtPassword);


    }

    public void login(View view)
    {
        request = Volley.newRequestQueue(context);
        if (!user.getText().toString().equals("") && !user.getText().toString().equals(""))
        {
            String url = serverip + "Log.php?user="+user.getText()+"&pass="+pass.getText();
            Log.i("Link",url);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("res3");
        String res="No jalo nada";
        try {
            for(int i=0;i<json.length();i++){
                JSONObject jsonres = null;
                jsonres=json.getJSONObject(i);
                res=jsonres.optString("salida");
                Log.i("res",res);
                if (res.equals("Yes")){
                    Intent ini= new Intent(MainActivity.this,ListaActivity.class);
                    startActivity(ini);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Datos de Inicio de Sesion Erroneos", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}