package com.surya.nlp;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Positivity{

    public static void main(String[] args) {
        try {
            // Load the Excel file
            FileInputStream fis = new FileInputStream("D:\\scr\\src\\main\\resources\\tryam final.xlsx");
            Workbook workbook = WorkbookFactory.create(fis);

            // Assuming the sentiment data is in the first column of the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Initialize variables for calculation
            float positiveSum = 0;
            float totalSentiments = 0;

            // Iterate through each row
            for (Row row : sheet) {
                // Assuming sentiment values are stored as strings
                Cell cell = row.getCell(0); // Assuming sentiment values are in the first column
                if (cell != null) {
                    String sentiment = cell.getStringCellValue().trim().toLowerCase();

                    // Increment positiveSum based on sentiment value
                    if (sentiment.equals("positive") || sentiment.equals("very positive")) {
                        positiveSum += (sentiment.equals("very positive") ? 1 : 0.5);
                    }
                    // Decrement positiveSum based on sentiment value
                    else if (sentiment.equals("negative") || sentiment.equals("very negative")) {
                        positiveSum -= 0.5;
                    }

                    // Increment totalSentiments for each non-empty cell
                    if (!sentiment.isEmpty()) {
                        totalSentiments++;
                    }
                }
            }

            // Calculate positivity ratio
            double positivityRatio = (double) positiveSum / totalSentiments;
            System.out.println("Positivity Ratio: " + positivityRatio);

            // Close the workbook and file input stream
            workbook.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
