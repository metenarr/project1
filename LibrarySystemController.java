package com.example.demo20;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LibrarySystemController {

    @FXML private TableView<LibraryBook> libraryBookTable;
    @FXML private TableColumn<LibraryBook, Integer> colInventoryNumber;
    @FXML private TableColumn<LibraryBook, String> colAuthor;
    @FXML private TableColumn<LibraryBook, String> colTitle;
    @FXML private TableColumn<LibraryBook, Integer> colYear;
    @FXML private DatePicker returnDatePicker;
    @FXML private DatePicker dueDatePicker;

    private ObservableList<LibraryBook> libraryBooks = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Настроим колонки таблицы
        colInventoryNumber.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getInventoryNumber()).asObject());
        colAuthor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAuthor()));
        colTitle.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        colYear.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getPublicationYear()).asObject());

        // Привязка данных к таблице
        libraryBookTable.setItems(libraryBooks);

        // Загружаем книги сразу при инициализации
        loadLibraryBooksFromDatabase();
    }

    private void loadLibraryBooksFromDatabase() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Загружаем книги в коллекцию
            while (resultSet.next()) {
                LibraryBook book = new LibraryBook(
                        resultSet.getInt("inventory_number"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getInt("publication_year")
                );
                libraryBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void calculateFine() {
        // Получаем выбранные даты
        LocalDate returnDate = returnDatePicker.getValue();
        LocalDate dueDate = dueDatePicker.getValue();

        if (returnDate == null || dueDate == null) {
            showError("Пожалуйста, выберите даты для возврата и фактического возврата.");
            return;
        }

        // Рассчитываем штраф, если возврат произошел позже dueDate
        if (returnDate.isAfter(dueDate)) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            double fine = daysLate * 1.5; // например, штраф 1.5 рубля за каждый день просрочки

            // Показать результат в сообщении
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Рассчитан штраф");
            alert.setHeaderText(null);
            alert.setContentText("Штраф за просрочку: " + fine + " рублей");
            alert.showAndWait();
        } else {
            showError("Возврат книги был выполнен вовремя.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
