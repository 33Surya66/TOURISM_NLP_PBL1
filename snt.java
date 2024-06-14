package com.surya.nlp;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class snt {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        try {
            // Load Excel file
            FileInputStream file = new FileInputStream(new File("D:\\scr\\src\\main\\resources\\Gateway.xlsx"));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            // Iterate through each row and get the text for sentiment analysis
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == CellType.STRING) {
                        String text = cell.getStringCellValue();
                        analyzeSentiment(text, stanfordCoreNLP);
                    }
                }
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeSentiment(String text, StanfordCoreNLP stanfordCoreNLP) {
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> coreSentenceList = coreDocument.sentences();
        for (CoreSentence coreSentence : coreSentenceList) {
            String sentiment = coreSentence.sentiment();
            System.out.println(sentiment + "\t\t\t" + coreSentence);
        }
    }
}
