package com.example.proyectofinal;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaActivity extends ListActivity implements  Response.Listener<JSONObject>,Response.ErrorListener {
    private Button btnNuevo;
    private final Context context = this;
    private ProcesosPHP php = new ProcesosPHP();;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Frutas> listaFrutas;
    private String serverip = "https://proyectofinalrcc.000webhostapp.com/";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista_activity);
        request = Volley.newRequestQueue(context);
        listaFrutas = new ArrayList<Frutas>();
        consultarfrutas();

    }
    public void consultarfrutas()
    {
        String url = serverip + "ConsultarFrutas.php";
        Log.i("urlc", url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Frutas fruta = null;
        JSONArray json = response.optJSONArray("frutas");
        Log.i("Longitud", String.valueOf(json.length()));
        try {
            for(int i=0;i<json.length();i++){
                fruta = new Frutas();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                fruta.setId(jsonObject.optInt("Id"));
                fruta.setNombre(jsonObject.optString("Nombre"));
                fruta.setDescripcion(jsonObject.optString("Descripcion"));
                fruta.setUrl(jsonObject.optString("ImgSrc"));
                Log.i("Fruta", jsonObject.optString("Nombre"));
                Log.i("Des", jsonObject.optString("Descripcion"));
                Log.i("Link", jsonObject.optString("ImgSrc"));
                listaFrutas.add(fruta);
            }
            MyArrayAdapter adapter = new MyArrayAdapter(context,R.layout.activity_lista,listaFrutas);
            setListAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class MyArrayAdapter extends ArrayAdapter<Frutas>{
        Context context;
        int textViewRecursoId;
        ArrayList<Frutas> objects;
        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Frutas> objects){
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }
        public View getView(final int position, View convertView, ViewGroup viewGroup){
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);
            ImageView foto = (ImageView) view.findViewById(R.id.foto);
            TextView lblNombre = (TextView) view.findViewById(R.id.txtNombre);
            TextView lblDescr = (TextView) view.findViewById(R.id.txtDescripcion);
            Button btnModificar = (Button)view.findViewById(R.id.btnModificar);
            Button btnBorrar = (Button)view.findViewById(R.id.btnLimpiar);
            Picasso.get()
                    .load(objects.get(position).getUrl())
                    .into(foto);
            //foto.setImageURI(Uri.parse(objects.get(position).getImageId()));
            lblNombre.setText(objects.get(position).getNombre());
            lblDescr.setText(objects.get(position).getDescripcion());
            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    php.setContext(context);
                    Log.i("id", String.valueOf(objects.get(position).getId()));
                    php.borrarContactoWebService(objects.get(position).getId());
                    
                    objects.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Contacto eliminado con exito", Toast.LENGTH_SHORT).show();
                }
            });
            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle oBundle = new Bundle();
                    oBundle.putSerializable("frutas", objects.get(position));
                    Intent i = new Intent(ListaActivity.this,PanelActivity.class);
                    i.putExtras(oBundle);
                    setResult(Activity.RESULT_OK, i);
                    startActivityForResult(i, 0);
                    //startActivity(i);
                }
            });
            return view;
        }
    }
}

