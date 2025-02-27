package htl.encrypter;

import htl.encrypter.model.Encrypter;
import htl.encrypter.model.FileWriterUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EncryptController {
    Encrypter encrypter = new Encrypter();

    @FXML
    private TextField txtAlphabet, txtKey;

    @FXML
    private TextArea txtPlainText, txtEncryptedText;

    public void initialize() {
        txtPlainText.selectedTextProperty().addListener(
                (observable, oldValue, newValue) -> {
                    var start = txtPlainText.getSelection().getStart();
                    var end = txtPlainText.getSelection().getEnd();
                    txtEncryptedText.selectRange(start, end);
                }
        );

        txtEncryptedText.selectedTextProperty().addListener(
                (observable, oldValue, newValue) -> {
                    var start = txtEncryptedText.getSelection().getStart();
                    var end = txtEncryptedText.getSelection().getEnd();
                    txtPlainText.selectRange(start, end);
                }
        );

        txtAlphabet.textProperty().bindBidirectional(encrypter.alphabetProperty());
        txtKey.textProperty().bindBidirectional(encrypter.keyProperty());
        txtPlainText.textProperty().bindBidirectional(encrypter.plainTextProperty());
        txtEncryptedText.textProperty().bindBidirectional(encrypter.encryptedTextProperty());

        encrypter.alphabetProperty().addListener((observable, oldValue, newValue) -> {
            txtAlphabet.setText(newValue);
        });
    }

    public void onStandartButtonClicked(ActionEvent event) {
        encrypter.setDefaultAlphabet();
    }

    public void onRecalcButtonClicked(ActionEvent event) {
        encrypter.generateRandomKey();
    }

    public void onSaveButtonClicked(ActionEvent event) {
        FileWriterUtil.writeToFile("alphabetAndKey.txt", txtAlphabet.getText() + "\n" + txtKey.getText());
        FileWriterUtil.writeToFile("plainText.txt", txtPlainText.getText());
        FileWriterUtil.writeToFile("encryptedText.txt", txtEncryptedText.getText());
    }
}