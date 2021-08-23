package com.example.proyectofinal;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProcesosPHP implements Response.Listener<JSONObject>,Response.ErrorListener{
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
        private ArrayList<Frutas> frutas = new ArrayList<Frutas>();
        private String serverip = "https://proyectofinalrcc.000webhostapp.com/";
        public void setContext(Context context){
            request = Volley.newRequestQueue(context);
        }


        public void actualizarContactoWebService(Frutas c,int id){
            String url = serverip + "Update.php?Id="+id
                    +"&Nom="+c.getNombre()+"&Des="+c.getDescripcion() +"&Url="+c.getUrl();
            url = url.replace(" ","%20");
            Log.i("urlp", url);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
            Log.i("Link",url);
        }
        public void borrarContactoWebService(int id){
            String url = serverip + "Eliminar.php?Id="+id;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
            Log.i("Link",url);
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("ERROR",error.toString());
        }
        @Override
        public void onResponse(JSONObject response) {
        }
}
