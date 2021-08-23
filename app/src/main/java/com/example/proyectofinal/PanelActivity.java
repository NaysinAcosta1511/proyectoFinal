package com.example.proyectofinal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PanelActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnGuardar;
    private Button btnRegresar;
    private Button btnBorrar;
    private Button btnLimpiar;
    private EditText txtNombre;
    private EditText txtDes;
    private EditText txtUrl;
    private ImageView imgFru;
    private TextView lblId;
    private TextView lblSrc;
    private String imgUri;
    private Frutas savedFruta;
    ProcesosPHP php;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruta_activity);
        initComponents();
        setEvents();
        Frutas fruta = (Frutas) getIntent().getExtras().getSerializable("frutas");
        savedFruta= fruta;
        id = fruta.getId();
        txtNombre.setText(fruta.getNombre());
        txtDes.setText(fruta.getDescripcion());
        txtUrl.setText(fruta.getUrl());
        Picasso.get()
                .load(fruta.getUrl())
                .into(imgFru);
    }
    public void initComponents() {
        this.php = new ProcesosPHP();
        php.setContext(this);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtDes = (EditText) findViewById(R.id.txtDescripcion);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        imgFru = (ImageView) findViewById(R.id.imgFruta);
        lblId = (TextView) findViewById(R.id.lblId);
        savedFruta = null;
    }
    public void setEvents() {
        this.btnGuardar.setOnClickListener(this);
        this.btnLimpiar.setOnClickListener(this);
        this.btnRegresar.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(isNetworkAvailable()){
            switch (view.getId()) {
                case R.id.btnGuardar:
                    boolean completo = true;
                    if(txtNombre.getText().toString().equals("")){
                        txtNombre.setError("Introduce el Nombre");
                        completo=false;
                    }
                    if(txtDes.getText().toString().equals("")){
                        txtDes.setError("Introduce la descripción");
                        completo=false;
                    }
                    if(txtUrl.getText().toString().equals("")){
                        txtUrl.setError("Introduce el precio");
                        completo=false;
                    }
                    if (completo){
                        Frutas fruta = new Frutas();
                        fruta.setNombre(txtNombre.getText().toString());
                        fruta.setDescripcion(txtDes.getText().toString());
                        fruta.setUrl(txtUrl.getText().toString());

                        php.actualizarContactoWebService(fruta,id);
                        Toast.makeText(getApplicationContext(), "Registro Modificado", Toast.LENGTH_SHORT).show();
                        limpiar();
                        Intent a= new Intent(PanelActivity.this,ListaActivity.class);
                        startActivity(a);

                    }
                    break;
                case R.id.btnLimpiar:
                    limpiar();
                    break;
                case R.id.btnRegresar:
                    Intent a= new Intent(PanelActivity.this,ListaActivity.class);

                    startActivity(a);
                    break;
            }
        }else{
            Toast.makeText(getApplicationContext(), "Se necesita tener conexion a internet",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
    public void limpiar(){
        savedFruta = null;
        txtNombre.setText("");
        txtDes.setText("");
        txtUrl.setText("");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(intent != null){
            Bundle oBundle = intent.getExtras();

            if(Activity.RESULT_OK == resultCode){
                Frutas fruta = (Frutas) oBundle.getSerializable("frutas");
                savedFruta= fruta;
                id = fruta.getId();
                txtNombre.setText(fruta.getNombre());
                txtDes.setText(fruta.getDescripcion());
                txtUrl.setText(fruta.getUrl());
                Picasso.get()
                        .load(fruta.getUrl())
                        .into(imgFru);


            }else{
                //limpiar();
            }
        }
    }
}