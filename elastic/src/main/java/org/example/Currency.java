package org.example;

public class Currency {
    public final String name;
    public final String code;
    public final double rate;

    public Currency(String name, String code, double rate) {
        this.name = name;
        this.code = code;
        this.rate = rate;
    }
}
