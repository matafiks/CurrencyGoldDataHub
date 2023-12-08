package org.example;

public class Currency {
    public final String name;
    public final String code;
    public final double rate;
    public final String date;

    public Currency(String name, String code, double rate, String date) {
        this.name = name;
        this.code = code;
        this.rate = rate;
        this.date = date;
    }
}
