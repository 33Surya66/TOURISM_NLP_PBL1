package com.surya.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class TokenizeExample
{
    public static void main(String[] args)
    {
        StanfordCoreNLP stanfordCoreNLP=Pipeline.getPipeline();
        String text="It was great, fun and adventurous for the family. The staff and the activities were superb and really very fun. Much recommended. Ravi";
        CoreDocument coreDocument =new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel>coreLabelList=coreDocument.tokens();
        for(CoreLabel coreLabel:coreLabelList)
        {
            System.out.println(coreLabel.originalText());
        }
    }
}
