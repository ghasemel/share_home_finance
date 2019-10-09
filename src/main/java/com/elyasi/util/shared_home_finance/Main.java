package com.elyasi.util.shared_home_finance;

import com.elyasi.util.shared_home_finance.form.MainFormController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage MAIN_STAGE;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MAIN_STAGE = primaryStage;

        MainFormController mainForm = new MainFormController();
        mainForm.show();
    }

    public static Stage getMainStage() {
        return MAIN_STAGE;
    }
}
