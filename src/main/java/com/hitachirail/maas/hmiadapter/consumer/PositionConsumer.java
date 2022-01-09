package com.hitachirail.maas.hmiadapter.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumerFactory;
import com.hitachirail.maas.hmiadapter.businessentity.TrainElementPosition;
import com.hitachirail.maas.hmiadapter.businessentity.VehiclePosition;
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
public class PositionConsumer {



    private ObjectMapper objectMapper;

    private BusinessObjectUtils businessObjectUtils;

    @Autowired
    PositionConsumer(ObjectMapper objectMapper, BusinessObjectUtils businessObjectUtils){

        this.objectMapper = objectMapper;
        this.businessObjectUtils = businessObjectUtils;
    }

    @Autowired
    PushMessages pushMessages;

    @MaasConsumer(
            kafkaTopic = "${kafka.position.topic}",
            consumerGroupId = "${consumer.data.ingestion.group.id}"
    )
    public void consumePositionTopicMessage(List<String> messages) throws Exception{
        log.info("consumePositionTopic - messages: {}", messages);
        List <VehiclePosition> vehiclePositionList = new ArrayList<>();
        List<TrainElementPosition> trainElementPositionList = new ArrayList<>();

        for(String message : messages){
            BusinessObjectWrapper businessObjectWrapper =objectMapper.readValue(message, new TypeReference<BusinessObjectWrapper>(){});
            fillPositionLists(vehiclePositionList, trainElementPositionList, businessObjectWrapper);
        }


        pushMessages.sendMessage(vehiclePositionList,trainElementPositionList);

    }

    private void fillPositionLists(List <VehiclePosition> vehiclePositionList, List<TrainElementPosition> trainElementPositionList, BusinessObjectWrapper wrapper){
        ElementTypeEnum type = ElementTypeEnum.valueOf(wrapper.getType());
        switch (type){
            case VEHICLE:
                Object vehicle = businessObjectUtils.convertFromLinkedHashMap(VehiclePosition.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                vehiclePositionList.add((VehiclePosition) vehicle);
                break;
            case TRAIN_ELEMENT:
                Object trainElement = businessObjectUtils.convertFromLinkedHashMap(TrainElementPosition.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                trainElementPositionList.add((TrainElementPosition) trainElement);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
