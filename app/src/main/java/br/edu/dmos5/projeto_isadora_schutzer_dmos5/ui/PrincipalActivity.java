package br.edu.dmos5.projeto_isadora_schutzer_dmos5.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Optional;

import br.edu.dmos5.projeto_isadora_schutzer_dmos5.R;
import br.edu.dmos5.projeto_isadora_schutzer_dmos5.api.AstroAPI;
import br.edu.dmos5.projeto_isadora_schutzer_dmos5.dao.HoroscopoDAO;
import br.edu.dmos5.projeto_isadora_schutzer_dmos5.model.Horoscopo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrincipalActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 64;
    public static final String KEY_ELEPHANT_OBJECT = "ELEPHANT";

    private RadioGroup radioGroup;
    private AppCompatRadioButton radioBtnOntem, radioBtnHoje, radioBtnAmanha;
    private Spinner spinner;
    private ConstraintLayout constraintLayoutInfo;
    private TextView textDescricao, textCompatibilidade, textHumor, textCor,
            textNumeroDaSorte, textHoraDaSorte;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        radioBtnOntem = findViewById(R.id.rb_ontem);
        radioBtnHoje = findViewById(R.id.rb_hoje);
        radioBtnAmanha = findViewById(R.id.rb_amanha);
        radioGroup = findViewById(R.id.radioGroup);
        constraintLayoutInfo = findViewById(R.id.layout_info);
        textDescricao = findViewById(R.id.text_descricao);
        textCompatibilidade = findViewById(R.id.text_compatibilidade);
        textHumor = findViewById(R.id.text_humor);
        textCor = findViewById(R.id.text_cor);
        textNumeroDaSorte = findViewById(R.id.text_numero_da_sorte);
        textHoraDaSorte = findViewById(R.id.text_hora_da_sorte);

        Button btnVisualizarHoroscopo = findViewById(R.id.btn_visualizar_horoscopo);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                PrincipalActivity.this, R.array.signs_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        btnVisualizarHoroscopo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    HoroscopoDAO horoscopoDAO = new HoroscopoDAO(PrincipalActivity.this);
                    String sign = spinner.getSelectedItem().toString().toLowerCase();
                    String day;

                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.rb_ontem:
                            day = "yesterday";
                            break;
                        case R.id.rb_hoje:
                            day = "today";
                            break;
                        case R.id.rb_amanha:
                            day = "tomorrow";
                            break;
                        default:
                            day = null;
                            break;
                    }

                    Horoscopo horoscopo = horoscopoDAO.buscar(sign, day);

                    if (horoscopo == null){
                        Log.e("DMO", "ENTROU NA API");
                        buscarHoroscopoDoDia(sign, day);
                    } else {
                        Log.e("DMO", "NÃO ENTROU NA API");
                        preencherInformacoes(horoscopo);
                    }

                } else {
                    solicitarPermissao();
                }
            }
        });
    }

    private void preencherInformacoes(Horoscopo horoscopo) {
        constraintLayoutInfo.setVisibility(View.VISIBLE);

        textDescricao.setText(horoscopo.getDescription());
        textCompatibilidade.setText(horoscopo.getCompatibility());
        textHumor.setText(horoscopo.getMood());
        textCor.setText(horoscopo.getColor());
        textNumeroDaSorte.setText(horoscopo.getLucky_number());
        textHoraDaSorte.setText(horoscopo.getLucky_time());
    }

    @SuppressLint("NonConstantResourceId")
    public void onDayRadioBtnClick(View view) {
        boolean isSelected = ((AppCompatRadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_ontem:
                if (isSelected) {
                    radioBtnOntem.setTextColor(Color.WHITE);
                    radioBtnHoje.setTextColor(getColor(R.color.colorAccent));
                    radioBtnAmanha.setTextColor(getColor(R.color.colorAccent));
                }
                break;
            case R.id.rb_hoje:
                if (isSelected) {
                    radioBtnHoje.setTextColor(Color.WHITE);
                    radioBtnAmanha.setTextColor(getColor(R.color.colorAccent));
                    radioBtnOntem.setTextColor(getColor(R.color.colorAccent));
                }
                break;
            case R.id.rb_amanha:
                if (isSelected) {
                    radioBtnAmanha.setTextColor(Color.WHITE);
                    radioBtnOntem.setTextColor(getColor(R.color.colorAccent));
                    radioBtnHoje.setTextColor(getColor(R.color.colorAccent));
                }
                break;
        }
    }


    private void buscarHoroscopoDoDia(final String sign, String day) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AstroAPI.endPoint)
                .build();

        AstroAPI service = retrofit.create(AstroAPI.class);

        Call<Horoscopo> call = service.horoscopo(sign, day);

        call.enqueue(new Callback<Horoscopo>() {
            @Override
            public void onResponse(Call<Horoscopo> call, Response<Horoscopo> response) {
                if (response.isSuccessful()) {
                    Horoscopo horoscopo = response.body();
                    horoscopo.setSign(sign);

                    HoroscopoDAO horoscopoDAO = new HoroscopoDAO(PrincipalActivity.this);
                    horoscopoDAO.adicionar(horoscopo);
                    preencherInformacoes(horoscopo);
                } else {
                    Toast.makeText(
                            PrincipalActivity.this,
                            getString(R.string.erro_consultar_horoscopo)
                            , Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<Horoscopo> call, Throwable t) {
                Toast.makeText(
                        PrincipalActivity.this
                        , getString(R.string.erro)
                        , Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private boolean verificarPermissao() {
        return ContextCompat.checkSelfPermission(PrincipalActivity.this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitarPermissao() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                PrincipalActivity.this
                , Manifest.permission.INTERNET
        )) {
            new AlertDialog.Builder(PrincipalActivity.this)
                    .setMessage(getString(R.string.solicitar_permissao))
                    .setPositiveButton(
                            "Sim"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(
                                            PrincipalActivity.this,
                                            new String[]{
                                                    Manifest.permission.INTERNET
                                            },
                                            REQUEST_PERMISSION
                                    );
                                }
                            })
                    .setNegativeButton(
                            "Não"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                    .show();
        }
    }

}