package com.hitachirail.maas.acingestion.streaming.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasConsumerFactory;
import com.hitachirail.maas.acingestion.beans.Position;
import com.hitachirail.maas.acingestion.businessentity.PositionBusiness;
import com.hitachirail.maas.acingestion.businessentity.factory.PositionBusinessFactory;
import com.hitachirail.maas.acingestion.streaming.producer.ProducerService;
import com.hitachirail.maas.acingestion.streaming.consumer.utils.BusinessObjectUtils;
import com.hitachirail.maas.acingestion.streaming.consumer.utils.BusinessObjectWrapper;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@MaasConsumerFactory
@Slf4j
public class PositionConsumer {

    private ProducerService<BusinessObjectWrapper<PositionBusiness>> positionProducer;

    private ObjectMapper objectMapper;


    BusinessObjectUtils businessObjectUtils;

    @Autowired
   public PositionConsumer(ObjectMapper objectMapper, ProducerService<BusinessObjectWrapper<PositionBusiness>> positionProducer, BusinessObjectUtils businessObjectUtils){
        this.objectMapper = objectMapper;
        this.positionProducer = positionProducer;
        this.businessObjectUtils = businessObjectUtils;
    }

    @Timed(value="maas.kafka.consumer", extraTags = {"type","PositionBulk"})
    @MaasConsumer(
            kafkaTopic = "${kafka.position.bulk.topic}",
            consumerGroupId = "${consumer.data.ingestion.group.id}"
    )
    public void consumerPCDTopic(List<String> messages) throws JsonProcessingException {
        log.info("consumer messages on 'Position' topic");

        List<Position> positionList = new ArrayList<>();

        for(String message : messages)
            positionList.addAll(objectMapper.readValue(message, new TypeReference<List<Position>>(){}));

        log.debug("Position list size extracted: {}", positionList.size());


        for(Position position : positionList){
            BusinessObjectWrapper<PositionBusiness> wrapper = PositionBusinessFactory.createPositionBusiness(position, businessObjectUtils.getTenantId(position.getOperator()));
            this.positionProducer.publishOnKafkaOfficialTopic(wrapper);
        }



    }



}
