package com.company.invoice.dto;

public class Product {
    private int id;
    private String name;
    private double priceBrutto;
    private double priceNetto;
    private int vat;
    private String unitOfMeasure;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceNetto() {
        return priceNetto;
    }

    public int getDBPriceNetto() {
        return (int)this.priceNetto * 100;
    }

    public void setDBPriceNetto(int priceNetto) {
        this.priceNetto = (double)priceNetto / 100;
    }

    public double getPriceBrutto() {
        return priceBrutto;
    }

    public int getDBPriceBrutto() {
        return (int)this.priceBrutto * 100;
    }

    public void setPriceBrutto(double priceBrutto) {
        this.priceBrutto = priceBrutto;
        this.priceNetto = this.priceBrutto * (1.0 - ((double)this.vat / 100));
    }

    public void setDBPriceBrutto(int priceBrutto) {
        this.priceBrutto = (double)priceBrutto / 100;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Name: " + this.name +
                "\nPrice Brutto: " + this.priceBrutto + " Price Netto: " + this.priceNetto + " VAT: " + this.vat + " UoM: " + this.unitOfMeasure;
    }
}
