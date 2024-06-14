package com.surya.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Santi {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        List<String> predictedSentiments = new ArrayList<>();
        List<String> groundTruthSentiments = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(new File("D:\\scr\\src\\main\\resources\\Amruteshwar.xlsx"));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell textCell = row.getCell(0);
                Cell sentimentCell = row.getCell(1);

                if (textCell != null && sentimentCell != null) {
                    String text = textCell.getStringCellValue();
                    String groundTruthSentiment = sentimentCell.getStringCellValue();

                    analyzeSentiment(text, stanfordCoreNLP, predictedSentiments);
                    groundTruthSentiments.add(groundTruthSentiment);
                }
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double accuracy = calculateAccuracy(predictedSentiments, groundTruthSentiments);
        double precision = calculatePrecision(predictedSentiments, groundTruthSentiments);
        double recall = calculateRecall(predictedSentiments, groundTruthSentiments);
        double f1Score = calculateF1Score(precision, recall);

        System.out.println("Accuracy: " + accuracy);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1 Score: " + f1Score);
    }

    private static void analyzeSentiment(String text, StanfordCoreNLP stanfordCoreNLP, List<String> predictedSentiments) {
        Annotation annotation = stanfordCoreNLP.process(text);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            System.out.println(sentiment + "\t" + sentence);
            predictedSentiments.add(sentiment);
        }
    }

    private static double calculateAccuracy(List<String> predictedSentiments, List<String> groundTruthSentiments) {
        int correctPredictions = 0;
        for (int i = 0; i < predictedSentiments.size(); i++) {
            if (predictedSentiments.get(i).equals(groundTruthSentiments.get(i))) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / predictedSentiments.size();
    }

    private static double calculatePrecision(List<String> predictedSentiments, List<String> groundTruthSentiments) {
        int truePositives = 0;
        int falsePositives = 0;

        for (int i = 0; i < predictedSentiments.size(); i++) {
            if (predictedSentiments.get(i).equals("positive") && groundTruthSentiments.get(i).equals("positive")) {
                truePositives++;
            } else if (predictedSentiments.get(i).equals("positive") && groundTruthSentiments.get(i).equals("negative")) {
                falsePositives++;
            }
        }

        double precision = (double) truePositives / (truePositives + falsePositives);
        return precision;
    }

    private static double calculateRecall(List<String> predictedSentiments, List<String> groundTruthSentiments) {
        int truePositives = 0;
        int falseNegatives = 0;

        for (int i = 0; i < predictedSentiments.size(); i++) {
            if (predictedSentiments.get(i).equals("positive") && groundTruthSentiments.get(i).equals("positive")) {
                truePositives++;
            } else if (predictedSentiments.get(i).equals("negative") && groundTruthSentiments.get(i).equals("positive")) {
                falseNegatives++;
            }
        }

        double recall = (double) truePositives / (truePositives + falseNegatives);
        return recall;
    }

    private static double calculateF1Score(double precision, double recall) {
        double f1Score = 2 * ((precision * recall) / (precision + recall));
        return f1Score;
    }
}
