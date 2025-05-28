package at.htl;

import javafx.scene.control.Button;

public class NavigationController {

    private Button switchButton;

    public NavigationController(Button switchButton) {
        this.switchButton = switchButton;
        this.switchButton.setOnAction(event -> switchToPrimary());
    }

    private void switchToPrimary() {
        System.out.println("Switching to primary view");
    }
}