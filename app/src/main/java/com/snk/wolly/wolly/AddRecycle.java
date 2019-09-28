package com.snk.wolly.wolly;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRecycle extends AppCompatActivity {

    EditText etPeso, etCantidad;
    Button bCantidadMenos, bCantidadMas;
    FloatingActionButton fab;
    int cant = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recycle);
        Bundle bundle = getIntent().getExtras();

        bCantidadMas = findViewById(R.id.bCantiodadMas);
        bCantidadMenos = findViewById(R.id.bCantidadMenos );


        etPeso = findViewById(R.id.etPeso );
        etCantidad = findViewById(R.id.etCantidad );
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bCantidadMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cant--;
                etCantidad.setText(""+cant);
                etCantidad.invalidate();
            }
        });
        bCantidadMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cant++;
                etCantidad.setText(""+cant);
                etCantidad.invalidate();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
