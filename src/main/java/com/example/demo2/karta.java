package com.example.demo2;

abstract class karta {
    private String nomer;
    private String pin;
    private double dengi;
    private String[] istoriya;
    private int istoriyaCount;

    public karta(String nomer, String pin, double dengi) {
        this.nomer = nomer;
        this.pin = pin;
        this.dengi = dengi;
        this.istoriya = new String[100];
        this.istoriyaCount = 0;
        dobavitVistoriyu("создание карты", 0);
    }

    public String getNomer() {
        return nomer;
    }

    public boolean proveritPin(String vvod) {
        return this.pin.equals(vvod);
    }

    public double getBalans() {
        return dengi;
    }

    protected void setBalans(double dengi) {
        this.dengi = dengi;
    }

    public void dobavitVistoriyu(String oper, double summa) {
        if (istoriyaCount < 100) {
            String zapis = oper + " - сумма: " + summa + " - баланс: " + dengi;
            istoriya[istoriyaCount] = zapis;
            istoriyaCount++;
        }
    }

    public String[] getIstoriya() {
        return istoriya;
    }

    public int getIstoriyaCount() {
        return istoriyaCount;
    }

    public abstract boolean snyat(double summa);
    public abstract boolean mozhnoIstoriyu();
    public abstract String tipPerevoda();

    public void popolnit(double summa) {
        if (summa <= 0) {
            System.out.println("сумма должна быть больше нуля.");
            return;
        }
        dengi += summa;
        dobavitVistoriyu("пополнение", summa);
    }
}
