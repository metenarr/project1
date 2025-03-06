package com.example.demo20;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryController {

    @FXML private TextField publicationYearField;  // Поле для ввода года издания
    @FXML private DatePicker dueDatePicker;        // Дата возврата по правилам
    @FXML private DatePicker returnDatePicker;     // Фактическая дата возврата
    @FXML private Button calculateButton;          // Кнопка для расчета штрафа
    @FXML private Label resultLabel;               // Метка для отображения результата

    // Метод для расчета штрафа
    @FXML
    private void calculateFine() {
        // Получаем данные о дате возврата и дате, когда книга должна была быть возвращена
        LocalDate returnDate = returnDatePicker.getValue();
        LocalDate dueDate = dueDatePicker.getValue();

        // Проверяем, были ли выбраны обе даты
        if (returnDate != null && dueDate != null) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);

            if (daysLate > 0) {
                // Если книга была возвращена поздно, рассчитываем штраф
                double fine = daysLate * 0.5;  // Например, штраф 0.5 за каждый день просрочки
                resultLabel.setText("Штраф: " + fine + " рублей");
            } else {
                // Если книга была возвращена вовремя
                resultLabel.setText("Книга была возвращена вовремя");
            }
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите обе даты", "");
        }
    }

    // Метод для отображения сообщения об ошибке
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
