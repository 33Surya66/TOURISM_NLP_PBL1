package com.surya.nlp;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class sentimental {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP=Pipeline.getPipeline();
        String text="It was great, fun and adventurous for the family. The staff and the activities were superb and really very fun. Much recommended. Ravi";
        CoreDocument coreDocument =new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> coreSentenceList=coreDocument.sentences();
        for(CoreSentence coreSentence:coreSentenceList)
        {
            String sentiment = coreSentence.sentiment();
            System.out.println(sentiment+"\t"+coreSentence);
        }
    }
}

