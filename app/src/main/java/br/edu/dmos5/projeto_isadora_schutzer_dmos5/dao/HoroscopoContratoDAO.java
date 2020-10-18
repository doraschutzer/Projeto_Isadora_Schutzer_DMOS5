package br.edu.dmos5.projeto_isadora_schutzer_dmos5.dao;

import android.provider.BaseColumns;

public final class HoroscopoContratoDAO {

    private HoroscopoContratoDAO() {
    }

    public static class HoroscopoEntrada implements BaseColumns {
        public static final String TABELA_NOME = "horoscopo";
        public static final String COLUNA_NOME_CURRENT_DATE = "current_date_search";
        public static final String COLUNA_NOME_SIGN = "sign";
        public static final String COLUNA_NOME_DESCRIPTION = "description";
        public static final String COLUNA_NOME_COMPATIBILITY = "compatibility";
        public static final String COLUNA_NOME_MOOD = "mood";
        public static final String COLUNA_NOME_COLOR = "color";
        public static final String COLUNA_NOME_LUCKY_NUMBER = "lucky_number";
        public static final String COLUNA_NOME_LUCKY_TIME = "lucky_time";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HoroscopoEntrada.TABELA_NOME + "("
                    + HoroscopoEntrada._ID + " INTEGER PRIMARY KEY, "
                    + HoroscopoEntrada.COLUNA_NOME_CURRENT_DATE + " TEXT NOT NULL, "
                    + HoroscopoEntrada.COLUNA_NOME_SIGN + " TEXT NOT NULL, "
                    + HoroscopoEntrada.COLUNA_NOME_DESCRIPTION + " TEXT, "
                    + HoroscopoEntrada.COLUNA_NOME_COMPATIBILITY + " TEXT, "
                    + HoroscopoEntrada.COLUNA_NOME_MOOD + " TEXT, "
                    + HoroscopoEntrada.COLUNA_NOME_COLOR + " TEXT, "
                    + HoroscopoEntrada.COLUNA_NOME_LUCKY_NUMBER + " INTEGER, "
                    + HoroscopoEntrada.COLUNA_NOME_LUCKY_TIME + " TEXT, "
                    + "UNIQUE ("
                    + HoroscopoEntrada.COLUNA_NOME_CURRENT_DATE + ", "
                    + HoroscopoEntrada.COLUNA_NOME_SIGN
                    + "));";
}
