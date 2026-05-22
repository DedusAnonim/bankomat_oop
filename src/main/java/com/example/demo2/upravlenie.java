package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class upravlenie {
    @FXML private TextField cardNumberField;
    @FXML private PasswordField pinField;
    @FXML private TextArea infoArea;
    @FXML private TextArea reportArea;

    private baza baza;
    private boolean avtorizovan;

    @FXML
    public void initialize() {
        baza = new baza();
        avtorizovan = false;
        infoArea.setText("вставьте карту и введите PIN");
    }

    private void addInfo(String text) {
        infoArea.setText(infoArea.getText() + "\n" + text);
    }

    @FXML
    private void onInsertCard() {
        if (avtorizovan) {
            addInfo("сеанс уже активен. сначала завершите сеанс (кнопка 5)");
            return;
        }
        String nomer = cardNumberField.getText().trim();
        if (nomer.isEmpty()) {
            addInfo("ошибка: введите номер карты");
            return;
        }
        if (baza.proveritKartu(nomer)) {
            addInfo("карта " + nomer + " принята. введите PIN");
            cardNumberField.setDisable(true);
        } else {
            addInfo("ошибка: карта не распознана");
        }
    }

    @FXML
    private void onEnterPin() {
        if (avtorizovan) {
            addInfo("сеанс уже активен. сначала завершите сеанс (кнопка 5)");
            return;
        }
        String pin = pinField.getText();
        if (pin.isEmpty()) {
            addInfo("ошибка: введите PIN");
            return;
        }
        if (baza.proveritPin(pin)) {
            avtorizovan = true;
            addInfo("PIN верный. добро пожаловать!");
            addInfo("карта: " + baza.getNomerKarty());
            addInfo("баланс: " + baza.getBalansText());
            reportArea.setText("");
        } else {
            addInfo("неверный PIN. карта возвращена.");
            baza.zavershitSeans();
            cardNumberField.setDisable(false);
            cardNumberField.clear();
            pinField.clear();
        }
    }

    @FXML
    private void onWithdraw() {
        if (!avtorizovan) {
            addInfo("сначала вставьте карту и введите PIN");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("снятие наличных");
        dialog.setHeaderText("введите сумму для снятия");
        dialog.setContentText("сумма (руб):");

        dialog.showAndWait();
        String result = dialog.getResult();
        if (result != null && !result.isEmpty()) {
            try {
                double sum = Double.parseDouble(result);
                String otvet = baza.snyat(sum);
                addInfo(otvet);
                if (otvet.contains("снято")) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("выдача денег");
                    alert.setHeaderText("устройство выдало " + sum + " руб.");
                    alert.showAndWait();
                }
                reportArea.setText(baza.getOtchetText());
            } catch (NumberFormatException e) {
                addInfo("ошибка: нужно ввести число");
            }
        }
    }

    @FXML
    private void onTransfer() {
        if (!avtorizovan) {
            addInfo("сначала вставьте карту и введите PIN");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("перевод со счёта на карту");
        dialog.setHeaderText("введите сумму перевода");
        dialog.setContentText("сумма (руб):");

        dialog.showAndWait();
        String result = dialog.getResult();
        if (result != null && !result.isEmpty()) {
            try {
                double sum = Double.parseDouble(result);
                String otvet = baza.perevod(sum);
                addInfo(otvet);
                reportArea.setText(baza.getOtchetText());
            } catch (NumberFormatException e) {
                addInfo("ошибка: нужно ввести число");
            }
        }
    }

    @FXML
    private void onBalance() {
        if (!avtorizovan) {
            addInfo("сначала вставьте карту и введите PIN");
            return;
        }
        addInfo("баланс карты " + baza.getNomerKarty() + ": " + baza.getBalansText());
        reportArea.setText(baza.getOtchetText());
    }

    @FXML
    private void onHistory() {
        if (!avtorizovan) {
            addInfo("сначала вставьте карту и введите PIN");
            return;
        }
        if (!baza.mozhnoIstoriyu()) {
            addInfo("эта карта не хранит историю");
            return;
        }
        String ist = baza.getIstoriyaText();
        if (ist.isEmpty()) {
            addInfo("история пуста");
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("история операций");
            alert.setHeaderText(null);
            alert.setContentText(ist);
            alert.showAndWait();
        }
        reportArea.setText(baza.getOtchetText());
    }

    @FXML
    private void onExit() {
        if (!avtorizovan) {
            addInfo("нет активного сеанса");
            return;
        }
        addInfo("сеанс завершён. карта возвращена.");
        addInfo(baza.getOtchetText());
        baza.zavershitSeans();
        avtorizovan = false;
        cardNumberField.setDisable(false);
        cardNumberField.clear();
        pinField.clear();
        reportArea.setText("");
    }

    @FXML
    private void onCancel() {
        if (avtorizovan) {
            addInfo("операция отменена. сеанс завершён.");
            addInfo(baza.getOtchetText());
            baza.zavershitSeans();
            avtorizovan = false;
            cardNumberField.setDisable(false);
            cardNumberField.clear();
            pinField.clear();
            reportArea.setText("");
        } else {
            addInfo("отмена: нет активного сеанса");
        }
    }
}