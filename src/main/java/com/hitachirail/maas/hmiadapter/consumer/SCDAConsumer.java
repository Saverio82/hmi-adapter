package com.hitachirail.maas.hmiadapter.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumerFactory;
import com.hitachirail.maas.hmiadapter.businessentity.TrainElementSeatCountingDataAggregate;
import com.hitachirail.maas.hmiadapter.businessentity.VehicleSeatCountingDataAggregate;
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
public class SCDAConsumer {


    private ObjectMapper objectMapper;
    private BusinessObjectUtils businessObjectUtils;

    @Autowired
    public SCDAConsumer(ObjectMapper objectMapper, BusinessObjectUtils businessObjectUtils) {
        this.objectMapper = objectMapper;
        this.businessObjectUtils = businessObjectUtils;
    }

    @Autowired
    PushMessages pushMessages;

    @MaasConsumer(
            kafkaTopic = "${kafka.seat.counting.data.aggregate.topic}",
            consumerGroupId = "${consumer.data.ingestion.group.id}"
    )
    public void consumeSCDATopicMessage(List<String> messages) throws Exception {
        log.info("consumeSCDATopic - messages: {}", messages);

        List<VehicleSeatCountingDataAggregate> vehicleSeatCountingDataAggregateList = new ArrayList<>();
        List<TrainElementSeatCountingDataAggregate> trainElementSeatCountingDataAggregateList = new ArrayList<>();

        for (String message : messages) {
            BusinessObjectWrapper businessObjectWrapper = objectMapper.readValue(message, new TypeReference<BusinessObjectWrapper>() {});
            fillPCDLists(vehicleSeatCountingDataAggregateList, trainElementSeatCountingDataAggregateList, businessObjectWrapper);
        }

        pushMessages.sendMessage(vehicleSeatCountingDataAggregateList,trainElementSeatCountingDataAggregateList);
    }


    private void fillPCDLists(List<VehicleSeatCountingDataAggregate> vehiclePCDList, List<TrainElementSeatCountingDataAggregate> trainElementPCDList, BusinessObjectWrapper wrapper) {
        ElementTypeEnum type = ElementTypeEnum.valueOf(wrapper.getType());
        switch (type) {
            case VEHICLE:
                Object vehicle = businessObjectUtils.convertFromLinkedHashMap(VehicleSeatCountingDataAggregate.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                vehiclePCDList.add((VehicleSeatCountingDataAggregate) vehicle);
                break;
            case TRAIN_ELEMENT:
                Object trainElement = businessObjectUtils.convertFromLinkedHashMap(TrainElementSeatCountingDataAggregate.class, (LinkedHashMap) wrapper.getBusinessObject(), null);
                trainElementPCDList.add((TrainElementSeatCountingDataAggregate) trainElement);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
