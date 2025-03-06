package com.example.demo20;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Integer> colInventoryNumber;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, Integer> colYear;

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Инициализация колонок таблицы
        colInventoryNumber.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getInventoryNumber()).asObject());
        colAuthor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));
        colTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        colYear.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPublicationYear()).asObject());

        // Загружаем данные из базы данных
        loadBooksFromDatabase();

        // Устанавливаем данные в таблицу
        bookTable.setItems(books);
    }

    private void loadBooksFromDatabase() {
        // Подключение к базе данных и загрузка данных о книгах
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM books";  // Запрос к базе данных
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Чтение данных из ResultSet и добавление в коллекцию
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("inventory_number"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getInt("publication_year")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Логирование ошибок
            showAlert("Ошибка базы данных", "Не удалось загрузить данные из базы данных.", Alert.AlertType.ERROR);
        }
    }

    // Метод для отображения сообщений об ошибках
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
