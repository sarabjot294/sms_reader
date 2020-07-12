package com.example.payo.helperClasses;

import android.nfc.Tag;

import com.example.payo.helperClasses.model.SmsDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagsAnalysis {
    List<String> Tags = new ArrayList<>();
    List<SmsDetails> smsDetailsList = new ArrayList<>();
    Map<String, Integer> barGraphData  = new HashMap<>();

    public TagsAnalysis(List<String> tags, List<SmsDetails> smsDetailsList) {
        Tags = tags;
        this.smsDetailsList = smsDetailsList;
    }

    public Map<String, Integer> getBarGraphData() {
        return barGraphData;
    }

    public void calculateTags()
    {
        setUpMaps();
        for(int i=0;i<smsDetailsList.size();i++)
        {
            List<String> smsTags = smsDetailsList.get(i).getTagsList();
            if(smsTags.size()>0)
            {
                for(int j=0;j<smsTags.size();j++)
                {
                    String tag = smsTags.get(j);
                    int count = barGraphData.get(tag);
                    count++;
                    barGraphData.remove(tag);
                    barGraphData.put(tag,count);
                }
            }
        }
    }

    void setUpMaps()
    {
        for(int i=0;i<Tags.size();i++)
        {
            barGraphData.put(Tags.get(i),0);
        }
    }

}
