package org.example;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Screr {
    public static void main(String[] args) {
        String url="https://www.tripadvisor.com/Attraction_Review-g304554-d321437-Reviews-or20-Marine_Drive-Mumbai_Maharashtra.html";
        try {
            Document document= Jsoup.connect(url).get();
            Elements books=document.select("._c");
            Writer writer = Files.newBufferedWriter(Paths.get("876s.csv"));
            for(Element bk:books) {
                String title = bk.select(".biGQs._P.pZUbB.KxBGd").text();
//                 String price = bk.select(".price_color").text();

                System.out.println(title);

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
                writer.write("\n");
                writer.write(title);
            }
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}