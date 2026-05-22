package com.example.demo2;
class debetkarta extends karta {
    public debetkarta(String nomer, String pin, double dengi) {
        super(nomer, pin, dengi);
    }

    public boolean mozhnoIstoriyu() {
        return false;
    }

    public String tipPerevoda() {
        return "partial";
    }

    public boolean snyat(double summa) {
        if (summa <= 0) {
            return false;
        }
        if (summa > getBalans()) {
            return false;
        }
        setBalans(getBalans() - summa);
        dobavitVistoriyu("снятие", summa);
        return true;
    }
}
