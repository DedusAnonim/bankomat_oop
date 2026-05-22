package com.example.demo2;
class kreditkarta extends karta {
    private double procentPenya;

    public kreditkarta(String nomer, String pin, double dengi, double procentPenya) {
        super(nomer, pin, dengi);
        this.procentPenya = procentPenya;
    }

    public kreditkarta(String nomer, String pin, double dengi) {
        this(nomer, pin, dengi, 5.0);
    }

    public boolean mozhnoIstoriyu() {
        return true;
    }

    public String tipPerevoda() {
        return "partial";
    }

    private void naschitatPeniu() {
        if (getBalans() < 0) {
            double penya = getBalans() * (procentPenya / 100.0);
            setBalans(getBalans() + penya);
            dobavitVistoriyu("пени " + procentPenya + "%", penya);
        }
    }

    public boolean snyat(double summa) {
        if (summa <= 0) {
            return false;
        }
        setBalans(getBalans() - summa);
        dobavitVistoriyu("снятие", summa);
        if (getBalans() < 0) {
            naschitatPeniu();
        }
        return true;
    }
}
