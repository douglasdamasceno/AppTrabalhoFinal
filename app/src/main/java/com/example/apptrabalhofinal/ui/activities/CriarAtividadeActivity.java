package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.ui.fragments.DialogDataFragmento;
import com.example.apptrabalhofinal.ui.fragments.DialogHorarioFragmento;

import java.text.DateFormat;
import java.util.Calendar;

public class CriarAtividadeActivity extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener,AdapterView.OnItemSelectedListener {

    TextView  atividade_data;
    TextView  atividade_horario;
    private Toolbar myToolbar;
    private Spinner tipoAtividade;
    Button btnEnderencoAtividade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_atividade);

        atividade_data = findViewById(R.id.criar_atividade_data);
        atividade_horario = findViewById(R.id.criar_atividade_horario);

        myToolbar = (Toolbar) findViewById(R.id.minhaToolbar);

        tipoAtividade = (Spinner) findViewById(R.id.spinner);
        btnEnderencoAtividade = findViewById(R.id.btn_endereco_atividade);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Criar Atividade");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource (this,R.array.tipoAtividade,android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoAtividade.setAdapter(adapterSpinner);
        tipoAtividade.setOnItemSelectedListener(this);


        atividade_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDataFragmento dialogDataFragmento = new DialogDataFragmento();
                dialogDataFragmento.show(getSupportFragmentManager(),"data picker");
            }
        });

        atividade_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHorarioFragmento horario = new DialogHorarioFragmento();
                horario.show(getSupportFragmentManager(),"time picker");
            }
        });

        btnEnderencoAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CriarAtividadeActivity.this,CriarEnderecoAtividadeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String horario = "Hora:"+i + " Minuto:"+ i1;
        atividade_horario.setText(horario);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);

        String data = DateFormat.getDateInstance().format(calendar.getTime());
        atividade_data.setText(data);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String textoSelecionado = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this,"Texto Selecionado "+textoSelecionado,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
