package htl.encrypter;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField txtAlphabet, txtKey;

    public void onStandartButtonClicked(ActionEvent event) {
        txtAlphabet.setText("abcdefghijklmnopqrstuvwxyzäöüß");
    }

    public void onRecalcButtonClicked(ActionEvent event) {
        var alphabet = txtAlphabet.getText();

        var random = new Random();
        var key = Stream.generate(() -> alphabet.charAt(random.nextInt(alphabet.length())))
                .distinct()
                .limit(alphabet.length())
                .map(String::valueOf)
                .collect(Collectors.joining());

        txtKey.setText(key);
    }
}