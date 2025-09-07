package com.example.restaurantgrupo6;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String[] comida = {"Ceviche s/20", "Lomo Saltado s/30", "Aji de Gallina s/20", "Rocoto Relleno s/10"};
    String[] bebida = {"Gaseosa1L s/5", "Gaseosa 2L s/10", "Agua s/2"};
    EditText CantidadPlato, CantidadPlato2, CantidadBebiba;
    TextView total;
    Button btnCalcular;
    Map<String, Integer> precios = new HashMap<>();
    Spinner spComida, spComida2;

    private static final String PLACEHOLDER = "Seleccione";
    Spinner spBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spComida = findViewById(R.id.spPlato);
        spComida2 = findViewById(R.id.spPlato2);
        spBebida = findViewById(R.id.spBebida);
        CantidadPlato = findViewById(R.id.etCantidadPlato);
        CantidadPlato2 = findViewById(R.id.etCantidadPlato2);
        CantidadBebiba = findViewById(R.id.etCantidadBebida);
        total = findViewById(R.id.total);
        btnCalcular = findViewById(R.id.btnCalcular);

        precios.put("Ceviche s/20", 20);
        precios.put("Lomo Saltado s/30", 30);
        precios.put("Aji de Gallina s/20", 20);
        precios.put("Rocoto Relleno s/10", 10);
        precios.put("Gaseosa1L s/5", 5);
        precios.put("Gaseosa 2L s/10", 10);
        precios.put("Agua s/2", 2);

        List<String> lista1, lista2, listaBebida;

        lista1 = new ArrayList<>();
        lista1.add(PLACEHOLDER);
        lista1.addAll(Arrays.asList(comida));

        lista2 = new ArrayList<>();
        lista2.add(PLACEHOLDER);
        lista2.addAll(Arrays.asList(comida));

        listaBebida = new ArrayList<>();
        listaBebida.add(PLACEHOLDER);
        listaBebida.addAll(Arrays.asList(bebida));

        ArrayAdapter<String> adapter1, adapter2, adapterBebida;

        adapter1 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                lista1
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spComida.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                lista2
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spComida2.setAdapter(adapter2);

        adapterBebida = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaBebida
        );
        adapterBebida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBebida.setAdapter(adapterBebida);

        spComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position == 0) return;
               String seleccionado = lista1.get(position);
               actualizarLista(spComida2, adapter2, lista2, seleccionado);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {}
        });

        spComida2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                String seleccionado = lista2.get(position);
                actualizarLista(spComida, adapter1, lista1, seleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spBebida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                String seleccionado = listaBebida.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnCalcular.setOnClickListener(v -> calcularTotal());
    }
    private void actualizarLista(Spinner otroSpinner, ArrayAdapter<String> otroAdapter, List<String> otraLista, String seleccionado) {
        otraLista.clear();
        otraLista.add(PLACEHOLDER);
        for (String p : comida) {
            if (!p.equals(seleccionado)) {
                otraLista.add(p);
            }
        }
        otroAdapter.notifyDataSetChanged();
    }

    private void calcularTotal() {
        int t = 0;

        // Primer plato
        String plato1 = (String) spComida.getSelectedItem();
        int cant1 = parseCantidad(CantidadPlato.getText().toString());
        if (!plato1.equals(PLACEHOLDER) && precios.containsKey(plato1)) {
            t += precios.get(plato1) * cant1;
        }

        // Segundo plato
        String plato2 = (String) spComida2.getSelectedItem();
        int cant2 = parseCantidad(CantidadPlato2.getText().toString());
        if (!plato2.equals(PLACEHOLDER) && precios.containsKey(plato2)) {
            t += precios.get(plato2) * cant2;
        }

        // Bebida
        String bebida = (String) spBebida.getSelectedItem();
        int cant3 = parseCantidad(CantidadBebiba.getText().toString());
        if (!bebida.equals(PLACEHOLDER) && precios.containsKey(bebida)) {
            t += precios.get(bebida) * cant3;
        }

        // Mostrar el total
        total.setText("S/. " + t + ".00");
    }

    private int parseCantidad(String texto) {
        if (texto.isEmpty()) return 0;
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}