package com.hitachirail.maas.hmiadapter.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumerFactory;
import com.hitachirail.maas.hmiadapter.businessentity.TrainElementPeopleCountingData;
import com.hitachirail.maas.hmiadapter.businessentity.VehiclePeopleCountingData;
import com.hitachirail.maas.hmiadapter.consumer.util.BusinessObjectUtils;

import com.hitachirail.maas.hmiadapter.consumer.util.BusinessObjectWrapper;
import com.hitachirail.maas.hmiadapter.controller.PushMessages;
import com.hitachirail.maas.hmiadapter.enums.ElementTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@MaasConsumerFactory
@Slf4j
public class PCDConsumer {



    private ObjectMapper objectMapper;

    private BusinessObjectUtils businessObjectUtils;

    @Autowired
    PCDConsumer(ObjectMapper objectMapper, BusinessObjectUtils businessObjectUtils){
        this.objectMapper = objectMapper;
        this.businessObjectUtils = businessObjectUtils;
    }

    @Autowired
    PushMessages pushMessages;

    @MaasConsumer(
            kafkaTopic = "${kafka.people.counting.data.topic}",
            consumerGroupId = "${consumer.data.ingestion.group.id}"
    )
    public void consumePeopleCountingDataTopicMessage(List<String> messages) throws Exception{
        log.info("consumePCDTopic - messages: {}", messages);
        List <VehiclePeopleCountingData> vehiclePCDList = new ArrayList<>();
        List<TrainElementPeopleCountingData> trainElementPCDList = new ArrayList<>();

        for(String message : messages){
            BusinessObjectWrapper businessObjectWrapper =objectMapper.readValue(message, new TypeReference<BusinessObjectWrapper>(){});
            fillPCDLists(vehiclePCDList, trainElementPCDList, businessObjectWrapper);
        }


        pushMessages.sendMessage(vehiclePCDList,trainElementPCDList);

    }

    private void fillPCDLists(List <VehiclePeopleCountingData> vehiclePCDList, List<TrainElementPeopleCountingData> trainElementPCDList, BusinessObjectWrapper wrapper) {
        ElementTypeEnum type = ElementTypeEnum.valueOf(wrapper.getType());
        switch (type) {
            case VEHICLE:
                Object vehicle = businessObjectUtils.convertFromLinkedHashMap(VehiclePeopleCountingData.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                vehiclePCDList.add((VehiclePeopleCountingData) vehicle);
                break;
            case TRAIN_ELEMENT:
                Object trainElement = businessObjectUtils.convertFromLinkedHashMap(TrainElementPeopleCountingData.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                trainElementPCDList.add((TrainElementPeopleCountingData) trainElement);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

}

