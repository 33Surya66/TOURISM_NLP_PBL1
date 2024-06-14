package com.surya.nlp;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SSS {
    public static void main(String[] args) {
        String url = "https://www.google.com/maps/place/Venna+Lake+%26+Dam/@17.9259413,73.628182,14z/data=!4m12!1m2!2m1!1sThings+to+do!3m8!1s0x3bc26546fd125fa1:0xcf9f28c8103878f0!8m2!3d17.9344642!4d73.6687491!9m1!1b1!15sCgxUaGluZ3MgdG8gZG9aDiIMdGhpbmdzIHRvIGRvkgESdG91cmlzdF9hdHRyYWN0aW9umgEkQ2hkRFNVaE5NRzluUzBWSlEwRm5TVVJIY0dWaWNIZDNSUkFC4AEA!16s%2Fg%2F1hm54nmf8?entry=ttu";

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("div[class='section-review-content']");

            Writer writer = Files.newBufferedWriter(Paths.get("google_maps_data.csv"));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            for (Element element : elements) {
                String reviewText = element.select("span[class='section-review-text']").text();
                String rating = element.select("span[class='section-review-stars']").attr("aria-label");

                System.out.println("Review: " + reviewText);
                System.out.println("Rating: " + rating);

                // Write to CSV
                csvPrinter.printRecord(reviewText, rating);
            }

            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
