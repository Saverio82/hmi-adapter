package com.hitachirail.maas.acingestion.streaming.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasProducer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasProducerUser;
import com.hitachi.maas.ilspringlibrary.streaming.producer.MaasProducerComponent;
import com.hitachirail.maas.acingestion.businessentity.BluetoothCountingData;
import com.hitachirail.maas.acingestion.streaming.consumer.utils.BusinessObjectWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@MaasProducerUser
@Slf4j
public class BusinessBCDProducerService implements ProducerService<BusinessObjectWrapper<BluetoothCountingData>>{

    @MaasProducer(
            kafkaTopic = "${kafka.bluetooth.counting.data.topic}"
    )
    private MaasProducerComponent officialKafkaProducer;

    private ObjectMapper objectMapper;

    @Autowired
    BusinessBCDProducerService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishOnKafkaOfficialTopic(BusinessObjectWrapper<BluetoothCountingData> payload) throws
            JsonProcessingException {
        this.officialKafkaProducer.publish(objectMapper.writeValueAsString(payload));
    }

}