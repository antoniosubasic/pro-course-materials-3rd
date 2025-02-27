package htl.encrypter.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Encrypter {
    private final String DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyzüöäß";

    private StringProperty alphabet = new SimpleStringProperty("");
    private StringProperty key = new SimpleStringProperty("");
    private StringProperty plainText = new SimpleStringProperty("");
    private StringProperty encryptedText = new SimpleStringProperty("");

    public Encrypter() {
        alphabet.addListener((observable, oldValue, newValue) -> {
            var distinctAlphabet = newValue.chars()
                    .distinct()
                    .mapToObj(c -> String.valueOf((char) c))
                    .collect(Collectors.joining());

            if (!distinctAlphabet.equals(newValue)) {
                setAlphabet(distinctAlphabet);
            }

            generateRandomKey();
        });
        key.addListener((observable, oldValue, newValue) -> encrypt());
        plainText.addListener((observable, oldValue, newValue) -> encrypt());

        setDefaultAlphabet();
        setPlainText("Please enter your text here");
    }

    public String getAlphabet() {
        return alphabet.get();
    }

    public StringProperty alphabetProperty() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet.set(alphabet);
    }

    public String getKey() {
        return key.get();
    }

    public StringProperty keyProperty() {
        return key;
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getPlainText() {
        return plainText.get();
    }

    public StringProperty plainTextProperty() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText.set(plainText);
    }

    public StringProperty encryptedTextProperty() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText.set(encryptedText);
    }

    public void setDefaultAlphabet() {
        setAlphabet(DEFAULT_ALPHABET);
    }

    public void generateRandomKey() {
        var alphabet = getAlphabet();

        var random = new Random();
        var key = Stream.generate(() -> alphabet.charAt(random.nextInt(alphabet.length())))
                .distinct()
                .limit(alphabet.length())
                .map(String::valueOf)
                .collect(Collectors.joining());

        setKey(key);
    }

    private void encrypt() {
        var alphabet = getAlphabet();
        var key = getKey();

        var encrypted = getPlainText().toLowerCase().chars()
                .mapToObj(c -> {
                    var index = alphabet.indexOf((char) c);
                    return index != -1 ? String.valueOf(key.charAt(index)) : String.valueOf((char) c);
                })
                .collect(Collectors.joining());

        setEncryptedText(encrypted);
    }
}
