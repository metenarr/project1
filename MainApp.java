package com.example.demo20;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем FXML файл
        FXMLLoader loader = new FXMLLoader(getClass().getResource("library.fxml"));

        // Создаем сцену из загруженного FXML
        Scene scene = new Scene(loader.load());

        // Устанавливаем сцену и показываем окно
        primaryStage.setTitle("Библиотечная система");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
