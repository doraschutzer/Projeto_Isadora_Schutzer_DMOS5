package br.edu.dmos5.projeto_isadora_schutzer_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import br.edu.dmos5.projeto_isadora_schutzer_dmos5.model.Horoscopo;


public class HoroscopoDAO {

    private final SQLiteHelper dbHelper;

    public HoroscopoDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void adicionar(Horoscopo horoscopo) {
        if (horoscopo == null) throw new NullPointerException();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_CURRENT_DATE,
                horoscopo.getCurrent_date().toLowerCase());
        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_SIGN,
                horoscopo.getSign().toLowerCase());
        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_DESCRIPTION,
                horoscopo.getDescription().toLowerCase());
        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_COMPATIBILITY,
                horoscopo.getCompatibility().toLowerCase());
        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_MOOD,
                horoscopo.getMood().toLowerCase());
        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_COLOR,
                horoscopo.getColor().toLowerCase());
        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_LUCKY_NUMBER,
                horoscopo.getLucky_number().toLowerCase());
        values.put(HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_LUCKY_TIME,
                horoscopo.getLucky_time().toLowerCase());

        db.insert(HoroscopoContratoDAO.HoroscopoEntrada.TABELA_NOME, null, values);

        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Horoscopo buscar(String sign, String day) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = new String[]{
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_CURRENT_DATE,
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_SIGN,
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_DESCRIPTION,
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_COMPATIBILITY,
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_MOOD,
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_COLOR,
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_LUCKY_NUMBER,
                HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_LUCKY_TIME
        };

        String selection = HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_CURRENT_DATE + " = ?"
                + " AND " + HoroscopoContratoDAO.HoroscopoEntrada.COLUNA_NOME_SIGN + " = ?";


        String[] selectionArgs = new String[]{
                dayToDate(day),
                sign
        };

        Cursor cursor = db.query(
                HoroscopoContratoDAO.HoroscopoEntrada.TABELA_NOME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Horoscopo horoscopo = null;
        if (cursor.moveToFirst()) {
            horoscopo = new Horoscopo(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
            );
        }

        cursor.close();

        db.close();

        return horoscopo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dayToDate(String day) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
                .withLocale(Locale.ENGLISH);
        switch (day) {
            case "today":
                return today.format(dateTimeFormatter).toLowerCase();
            case "tomorrow":
                return tomorrow.format(dateTimeFormatter).toLowerCase();
            case "yesterday":
                return yesterday.format(dateTimeFormatter).toLowerCase();
            default:
                return null;
        }
    }
}
