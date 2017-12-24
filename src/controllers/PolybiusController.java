package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import static utils.ObjectsGenerator.polybiusSquare;

public class PolybiusController {
    @FXML
    private TextArea ORIGINAL_TEXT;

    @FXML
    private TextArea CIPHERTEXT;

    public void initialize() {
        ORIGINAL_TEXT.textProperty().addListener((observableValue, s, s2) -> {
            if(ORIGINAL_TEXT.isFocused()) {
                onChangeOriginalText(s2);
            }
        });

        CIPHERTEXT.textProperty().addListener((observableValue, s, s2) -> {
            if(CIPHERTEXT.isFocused()) {
                onChangeCipherText(s2);
            }
        });
    }

    private void onChangeOriginalText(String newText) {
        String newCipherText = "";
        boolean isFound;
        for (int i = 0; i < newText.length(); i++) {
            isFound = false;
            for (int y = 0; y < 8; y++) {
                if (isFound) {
                    break;
                }
                for (int x = 0; x < 9; x++) {
                    if (polybiusSquare[y][x] == newText.charAt(i)) {
                        newCipherText = newCipherText + (y + 1) + (x + 1);
                        isFound = true;
                        break;
                    }
                }
            }
            if (isFound) {
                newCipherText += " ";
            }
            CIPHERTEXT.setText(newCipherText);

        }
    }

    private void onChangeCipherText(String newText) {
        StringBuilder newOriginalText = new StringBuilder();
        String mas[] = newText.split("\\s+");
        for (String ma : mas) {
            if (ma.length() != 2)
                continue;
            int y = Integer.parseInt(Character.toString(ma.charAt(0))) - 1;
            int x = Integer.parseInt(Character.toString(ma.charAt(1))) - 1;
            if (y < 0 || y >= 10 || x < 0 || x >= 10) {
                continue;
            }
            newOriginalText.append(polybiusSquare[y][x]);
        }
        ORIGINAL_TEXT.setText(newOriginalText.toString());
    }
}
