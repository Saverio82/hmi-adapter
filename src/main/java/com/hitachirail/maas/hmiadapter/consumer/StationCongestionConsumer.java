package com.hitachirail.maas.hmiadapter.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumerFactory;
import com.hitachirail.maas.hmiadapter.businessentity.StationCongestion;
import com.hitachirail.maas.hmiadapter.consumer.util.BusinessObjectUtils;
import com.hitachirail.maas.hmiadapter.controller.PushMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@MaasConsumerFactory
@Slf4j
public class StationCongestionConsumer {

    private ObjectMapper objectMapper;
    private BusinessObjectUtils businessObjectUtils;



    @Autowired
    StationCongestionConsumer(ObjectMapper objectMapper, BusinessObjectUtils businessObjectUtils){
        this.objectMapper = objectMapper;
        this.businessObjectUtils = businessObjectUtils;
    }

    @Autowired
    PushMessages pushMessages;

    @MaasConsumer(
            kafkaTopic = "${kafka.station.congestion.topic}",
            consumerGroupId = "${consumer.data.ingestion.group.id}"
    )


    public void consumeStationCongestionTopicMessage(List<String> messages) throws Exception{
        log.info("consumeStationCongestionTopic - messages: {}", messages);
        List <StationCongestion> stationCongestionList = new ArrayList<>();


        for(String message : messages){
            StationCongestion item = objectMapper.readValue(message, new TypeReference<StationCongestion>(){});
            stationCongestionList.add( item);
        }

        pushMessages.sendMessage(stationCongestionList, Collections.emptyList());
    }

}
