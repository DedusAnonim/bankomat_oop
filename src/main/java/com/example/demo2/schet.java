package com.example.demo2;
class schet {
    private String nomerScheta;
    private double dengi;

    public schet(String nomerScheta, double dengi) {
        this.nomerScheta = nomerScheta;
        this.dengi = dengi;
    }

    public String getNomer() {
        return nomerScheta;
    }

    public double getDengi() {
        return dengi;
    }

    private void setDengi(double dengi) {
        this.dengi = dengi;
    }

    public boolean snyatSoScheta(double summa) {
        if (summa <= 0) {
            return false;
        }
        if (summa > dengi) {
            return false;
        }
        setDengi(dengi - summa);
        return true;
    }
}
