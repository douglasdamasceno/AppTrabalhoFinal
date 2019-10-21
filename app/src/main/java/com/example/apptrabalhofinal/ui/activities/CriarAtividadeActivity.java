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

    private TextView atividadeNome;
    private TextView atividadeDescricao;
    private TextView atividadeQuantidade;
    private TextView atividadeIdade;
    private TextView atividadeSexo;

    private String atividadeTipo;


    private TextView  atividadeData;
    private TextView  atividadeHorario;
    private Toolbar myToolbar;
    private Spinner tipoAtividade;
    private Button btnEnderencoAtividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_atividade);

        atividadeData = findViewById(R.id.criar_atividade_data);
        atividadeHorario = findViewById(R.id.criar_atividade_horario);

        myToolbar = (Toolbar) findViewById(R.id.minhaToolbar);

        tipoAtividade = (Spinner) findViewById(R.id.spinner);
        btnEnderencoAtividade = findViewById(R.id.btn_endereco_atividade);


        atividadeNome = findViewById(R.id.criar_atividade_nome);
        atividadeDescricao = findViewById(R.id.criar_atividade_descricao);
        atividadeQuantidade = findViewById(R.id.criar_atividade_quatidade);
        atividadeIdade = findViewById(R.id.criar_atividade_idade);
        atividadeSexo = findViewById(R.id.criar_atividade_sexo);



        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Criar Atividade");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource (this,R.array.tipoAtividade,android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoAtividade.setAdapter(adapterSpinner);
        tipoAtividade.setOnItemSelectedListener(this);


        atividadeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDataFragmento dialogDataFragmento = new DialogDataFragmento();
                dialogDataFragmento.show(getSupportFragmentManager(),"data picker");
            }
        });

        atividadeHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHorarioFragmento horario = new DialogHorarioFragmento();
                horario.show(getSupportFragmentManager(),"time picker");
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String horario = "Hora:"+i + " Minuto:"+ i1;
        atividadeHorario.setText(horario);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);

        String data = DateFormat.getDateInstance().format(calendar.getTime());
        atividadeData.setText(data);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String textoSelecionado = adapterView.getItemAtPosition(i).toString();
        atividadeTipo = textoSelecionado;
        Toast.makeText(this,"Texto Selecionado "+textoSelecionado,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void informarEnderenco(View view){
        Intent intent = new Intent(CriarAtividadeActivity.this,CriarEnderecoAtividadeActivity.class);


        intent.putExtra("nome",atividadeNome.getText().toString());
        intent.putExtra("descricao",atividadeDescricao.getText().toString());
        intent.putExtra("qtd",atividadeQuantidade.getText().toString());
        intent.putExtra("idade",atividadeIdade.getText().toString());
        intent.putExtra("sexo",atividadeSexo.getText().toString());
        intent.putExtra("hora",atividadeHorario.getText().toString());
        intent.putExtra("data",atividadeData.getText().toString());
        intent.putExtra("tipo",atividadeTipo);
        startActivity(intent);
    }

}
