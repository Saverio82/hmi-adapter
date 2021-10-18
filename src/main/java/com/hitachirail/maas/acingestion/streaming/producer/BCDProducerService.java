package com.hitachirail.maas.acingestion.streaming.producer;

import com.google.gson.Gson;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasProducer;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasProducerUser;
import com.hitachi.maas.ilspringlibrary.streaming.producer.MaasProducerComponent;
import com.hitachirail.maas.acingestion.beans.BluetoothCountingData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@MaasProducerUser
@Slf4j
public class BCDProducerService implements ProducerService <BluetoothCountingData>{

    @MaasProducer(
            kafkaTopic = "${kafka.bluetooth.counting.data.bulk.topic}"
    )
    private MaasProducerComponent internalKafkaProducer;

    @MaasProducer(
            kafkaTopic = "${kafka.bluetooth.counting.data.topic}"
    )
    private MaasProducerComponent officialKafkaProducer;

    private Gson gson;

    @Autowired
    public BCDProducerService(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void publishListOnKafkaBulkTopic(List<BluetoothCountingData> payload) {
        log.debug("publish list of {} elements into 'BluetoothCountingData' bulk topic", payload.size());

        this.internalKafkaProducer.publish(gson.toJson(payload));
    }

    @Override
    public void publishListOnKafkaOfficialTopic(List<BluetoothCountingData> payload){
        log.debug("publish list of {} elements into 'BluetoothCountingData' bulk topic", payload.size());

        this.officialKafkaProducer.publish(gson.toJson(payload));
    }


}