package br.edu.dmos5.projeto_isadora_schutzer_dmos5.model;

import java.io.Serializable;

public class Horoscopo implements Serializable {
    private String current_date;
    private String sign;
    private String description;
    private String compatibility;
    private String mood;
    private String color;
    private String lucky_number;
    private String lucky_time;

    public Horoscopo(String current_date, String sign, String description, String compatibility, String mood
            , String color, String lucky_number, String lucky_time
    ) {
        this.current_date = current_date;
        this.description = description;
        this.sign = sign;
        this.compatibility = compatibility;
        this.mood = mood;
        this.color = color;
        this.lucky_number = lucky_number;
        this.lucky_time = lucky_time;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLucky_number() {
        return lucky_number;
    }

    public void setLucky_number(String lucky_number) {
        this.lucky_number = lucky_number;
    }

    public String getLucky_time() {
        return lucky_time;
    }

    public void setLucky_time(String lucky_time) {
        this.lucky_time = lucky_time;
    }
}
