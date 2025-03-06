package com.example.demo20;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {

    public static int calculateFine(int publicationYear, LocalDate dueDate, LocalDate returnDate) {
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);

        if (daysLate <= 0) {
            return 0; // No fine if returned on time or early
        }

        int fineRate = 10; // Fine rate per day (example: 10 rubles per day)

        if (publicationYear < 1900) {
            fineRate = 20; // Increased fine for older books
        }

        return (int) (daysLate * fineRate); // Total fine
    }
}

