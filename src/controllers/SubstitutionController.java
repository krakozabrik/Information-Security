package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static java.lang.Math.abs;
import static java.lang.Math.floorMod;
import static utils.ObjectsGenerator.alphabet72;

public class SubstitutionController {
    @FXML
    TextArea SUBSTITUTION_ORIGINAL;

    @FXML
    TextArea SUBSTITUTION_CIPHER;

    @FXML
    TextField SUBSTITUTION_KEY;

    public void initialize() {
        SUBSTITUTION_ORIGINAL.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (SUBSTITUTION_ORIGINAL.isFocused()) {
                onChangeOriginalText(newValue);
            }
        });

        SUBSTITUTION_CIPHER.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (SUBSTITUTION_CIPHER.isFocused()) {
                onChangeCipherText(newValue);
            }
        });

        SUBSTITUTION_KEY.textProperty().addListener((observable, oldValue, newValue) -> {
            onChangeOriginalText(SUBSTITUTION_ORIGINAL.getText());
        });
    }

    private void onChangeOriginalText(String newText) {
        StringBuilder newCipherText = new StringBuilder();
        String keyText = SUBSTITUTION_KEY.getText();
        int keyLength = SUBSTITUTION_KEY.getLength();
        if (keyLength <= 0) {
            SUBSTITUTION_CIPHER.setText("");
            return;
        }
        for (int i = 0; i < newText.length(); i++) {
            // Cj = (Mj+Kj) % n
            int newChar = (
                    alphabet72.indexOf(newText.charAt(i)) +
                            alphabet72.indexOf(keyText.charAt(i % keyLength))
            ) % alphabet72.length();
            newCipherText.append(alphabet72.charAt(newChar));
        }
        SUBSTITUTION_CIPHER.setText(newCipherText.toString());
    }

    private void onChangeCipherText(String newText) {
        StringBuilder newOriginalText = new StringBuilder();
        String keyText = SUBSTITUTION_KEY.getText();
        int keyLength = SUBSTITUTION_KEY.getLength();
        if (keyLength <= 0) {
            SUBSTITUTION_ORIGINAL.setText("");
            return;
        }
        for (int i = 0; i < newText.length(); i++) {
            //Mj = (Cj - Kj) % n
            int newChar = (
                    alphabet72.indexOf(newText.charAt(i)) -
                            alphabet72.indexOf(keyText.charAt(i % keyLength))
            );
            //есть 2 вида взятия остатка для отрицательного числа, % - обычный, отрицательный остаток.
            //Math.floorMod - правильный остаток{floorMod(-4, +3) == +2}
            newOriginalText.append(alphabet72.charAt(floorMod(newChar, alphabet72.length())));
        }
        SUBSTITUTION_ORIGINAL.setText(newOriginalText.toString());
    }

    private void onChangeKey(String newText) {

    }
}
