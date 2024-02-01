package com.example.model;

public class Currency {

    private String code;
    private String alphaCode;
    private String numericCode;
    private String name;
    private double rate;
    private String date;
    private double inverseRate;

    public Currency() {
    }

    public Currency(String code, String alphaCode, String numericCode, String name, double rate, String date,
            double inverseRate) {

        if (code == null || code.trim().isEmpty() ||
            alphaCode == null || alphaCode.trim().isEmpty() ||
            numericCode == null || numericCode.trim().isEmpty() ||
            name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Code, AlphaCode, NumericCode, and Name cannot be null or empty");
        }
        if (rate <= 0 || inverseRate <= 0) {
            throw new IllegalArgumentException("Rate and inverseRate must be positive");
        }

        this.code = code;
        this.alphaCode = alphaCode;
        this.numericCode = numericCode;
        this.name = name;
        this.rate = rate;
        this.date = date;
        this.inverseRate = inverseRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInverseRate() {
        return inverseRate;
    }

    public void setInverseRate(double inverseRate) {
        this.inverseRate = inverseRate;
    }

}
