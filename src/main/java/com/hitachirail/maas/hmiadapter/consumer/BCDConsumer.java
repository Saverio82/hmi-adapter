package com.hitachirail.maas.hmiadapter.consumer;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumerFactory;

import com.hitachirail.maas.hmiadapter.businessentity.TrainElementBluetoothCountingData;
import com.hitachirail.maas.hmiadapter.businessentity.VehicleBluetoothCountingData;
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
public class BCDConsumer {



    private ObjectMapper objectMapper;

    private BusinessObjectUtils businessObjectUtils;

    @Autowired
    PushMessages pushMessages;

    @Autowired
    BCDConsumer( ObjectMapper objectMapper, BusinessObjectUtils businessObjectUtils){
        this.objectMapper = objectMapper;
        this.businessObjectUtils = businessObjectUtils;
    }

    @MaasConsumer(
            kafkaTopic = "${kafka.bluetooth.counting.data.topic}",
            consumerGroupId = "${consumer.data.ingestion.group.id}"
    )
    public void consumeBluetoothCountingDataTopicMessage(List<String> messages) throws Exception{
        log.info("consumeBCDTopic - messages: {}", messages);
        List <VehicleBluetoothCountingData> vehicleBCDList = new ArrayList<>();
        List<TrainElementBluetoothCountingData> trainElementBCDList = new ArrayList<>();

        for(String message : messages){
            BusinessObjectWrapper businessObjectWrapper =objectMapper.readValue(message, new TypeReference<BusinessObjectWrapper>(){});
            fillBCDLists(vehicleBCDList, trainElementBCDList, businessObjectWrapper);
        }


        pushMessages.sendMessage(vehicleBCDList,trainElementBCDList);

    }

    private void fillBCDLists(List<VehicleBluetoothCountingData> vehicleBCDList, List<TrainElementBluetoothCountingData> trainElementBCDList, BusinessObjectWrapper wrapper) {
        ElementTypeEnum type = ElementTypeEnum.valueOf(wrapper.getType());
        switch (type) {
            case VEHICLE:
                Object vehicle = businessObjectUtils.convertFromLinkedHashMap(VehicleBluetoothCountingData.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                vehicleBCDList.add((VehicleBluetoothCountingData) vehicle);
                break;
            case TRAIN_ELEMENT:
                Object trainElement = businessObjectUtils.convertFromLinkedHashMap(TrainElementBluetoothCountingData.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                trainElementBCDList.add((TrainElementBluetoothCountingData) trainElement);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

}
