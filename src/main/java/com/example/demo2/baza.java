package com.example.demo2;

class baza {
    private karta[] vseKarty = new karta[100];
    private String[] nomeraKart = new String[100];
    private int kolKart = 0;

    private schet[] vseScheta = new schet[100];
    private String[] nomeraScheta = new String[100];
    private int kolScheta = 0;

    private karta tekKarta = null;
    private schet tekSchet = null;
    private String[] otchet = new String[100];
    private int otchetCount = 0;
    private boolean avtorizovan = false;

    public baza() {
        sozdatKarty();
        sozdatScheta();
    }

    private void sozdatKarty() {
        dobavitKartu(new debetkarta("123456", "1111", 5000), "123456");
        dobavitKartu(new kreditkarta("234567", "2222", 1000, 5.0), "234567");
        dobavitKartu(new kreditkarta("345678", "3333", -500, 10.0), "345678");
    }

    private void sozdatScheta() {
        dobavitSchet(new schet("123456", 10000), "123456");
        dobavitSchet(new schet("234567", 20000), "234567");
        dobavitSchet(new schet("345678", 5000), "345678");
    }

    private void dobavitKartu(karta k, String nomer) {
        if (kolKart < 100) {
            vseKarty[kolKart] = k;
            nomeraKart[kolKart] = nomer;
            kolKart++;
        }
    }

    private void dobavitSchet(schet s, String nomer) {
        if (kolScheta < 100) {
            vseScheta[kolScheta] = s;
            nomeraScheta[kolScheta] = nomer;
            kolScheta++;
        }
    }

    private int najtiKartu(String nomer) {
        for (int i = 0; i < kolKart; i++) {
            if (nomeraKart[i].equals(nomer)) {
                return i;
            }
        }
        return -1;
    }

    private int najtiSchet(String nomer) {
        for (int i = 0; i < kolScheta; i++) {
            if (nomeraScheta[i].equals(nomer)) {
                return i;
            }
        }
        return -1;
    }

    public boolean proveritKartu(String nomer) {
        int index = najtiKartu(nomer);
        if (index == -1) {
            return false;
        }
        int indexScheta = najtiSchet(nomer);
        if (indexScheta == -1) {
            return false;
        }
        tekKarta = vseKarty[index];
        tekSchet = vseScheta[indexScheta];
        return true;
    }

    public boolean proveritPin(String pin) {
        if (tekKarta == null) {
            return false;
        }
        avtorizovan = tekKarta.proveritPin(pin);
        return avtorizovan;
    }

    public boolean isAvtorizovan() {
        return avtorizovan;
    }

    public void zavershitSeans() {
        tekKarta = null;
        tekSchet = null;
        avtorizovan = false;
        otchetCount = 0;
    }

    public String getBalansText() {
        if (tekKarta == null) return "";
        return String.format("%.2f руб.", tekKarta.getBalans());
    }

    public String getNomerKarty() {
        if (tekKarta == null) return "";
        return tekKarta.getNomer();
    }

    public boolean mozhnoIstoriyu() {
        if (tekKarta == null) return false;
        return tekKarta.mozhnoIstoriyu();
    }

    public String getIstoriyaText() {
        if (tekKarta == null) return "";
        StringBuilder sb = new StringBuilder();
        String[] ist = tekKarta.getIstoriya();
        int kol = tekKarta.getIstoriyaCount();
        for (int i = 0; i < kol; i++) {
            sb.append(ist[i]).append("\n");
        }
        return sb.toString();
    }

    public String snyat(double summa) {
        if (tekKarta == null) return "ошибка: нет карты";
        if (summa <= 0) return "сумма должна быть больше нуля";
        if (tekKarta.snyat(summa)) {
            if (otchetCount < 100) {
                otchet[otchetCount] = "снятие " + summa + " руб. (остаток: " + tekKarta.getBalans() + ")";
                otchetCount++;
            }
            return "снято " + summa + " руб. остаток: " + tekKarta.getBalans();
        } else {
            return "недостаточно средств";
        }
    }

    public String perevod(double summa) {
        if (tekKarta == null) return "ошибка: нет карты";
        if (tekSchet == null) return "ошибка: нет счёта";
        if (summa <= 0) return "сумма должна быть больше нуля";
        if (tekSchet.snyatSoScheta(summa)) {
            tekKarta.popolnit(summa);
            if (otchetCount < 100) {
                otchet[otchetCount] = "перевод " + summa + " руб. со счёта " + tekSchet.getNomer() + " на карту";
                otchetCount++;
            }
            return "переведено " + summa + " руб. со счёта на карту. баланс карты: " + tekKarta.getBalans();
        } else {
            return "на счёте недостаточно денег";
        }
    }

    public String getOtchetText() {
        if (otchetCount == 0) return "нет выполненных операций";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < otchetCount; i++) {
            sb.append(i + 1).append(". ").append(otchet[i]).append("\n");
        }
        return sb.toString();
    }
}