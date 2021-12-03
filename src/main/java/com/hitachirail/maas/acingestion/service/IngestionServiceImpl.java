package com.hitachirail.maas.acingestion.service;

import com.google.gson.JsonIOException;
import com.hitachi.maas.ilspringlibrary.streaming.annotation.MaasProducerUser;
import com.hitachirail.maas.acingestion.beans.BluetoothCountingData;
import com.hitachirail.maas.acingestion.beans.SeatCountingDataAggregate;
import com.hitachirail.maas.acingestion.businessentity.*;
import com.hitachirail.maas.acingestion.dto.*;
import com.hitachirail.maas.acingestion.streaming.producer.ProducerService;
import com.hitachirail.maas.securityframework.authvalidation.AuthClaimRule;
import com.hitachirail.maas.securityframework.authvalidation.AuthClaimVerify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class IngestionServiceImpl  implements IngestionService {

    private static final String CLAIM_PROPERTY = "scope";
    private static final String SCOPE_VALUE = "ac-ingestion";

    private ProducerService<PositionDTO> positionProducer;
    private ProducerService<BluetoothCountingData> bluetoothCountingDataProducer;
    private ProducerService<PeopleCountingDataDTO> peopleCountingDataProducer;
    private ProducerService<StationCongestionDTO> stationCongestionProducer;
    private ProducerService<SeatCountingDataAggregate> seatCountingDataAggregateProducer;

    @Autowired
    public IngestionServiceImpl(ProducerService<PositionDTO> positionProducer,
                                ProducerService<BluetoothCountingData> bluetoothCountingDataProducer,
                                ProducerService<PeopleCountingDataDTO> peopleCountingDataProducer,
                                ProducerService<StationCongestionDTO> stationCongestionProducer,
                                ProducerService<SeatCountingDataAggregate> seatCountingDataAggregateProducer) {
        this.positionProducer = positionProducer;
        this.bluetoothCountingDataProducer = bluetoothCountingDataProducer;
        this.peopleCountingDataProducer = peopleCountingDataProducer;
        this.stationCongestionProducer = stationCongestionProducer;
        this.seatCountingDataAggregateProducer = seatCountingDataAggregateProducer;
    }

    @AuthClaimVerify
    @AuthClaimRule(claimProperty = CLAIM_PROPERTY, equalToValue = SCOPE_VALUE, required = true)
    @Override
    public void publishPositionOnInternalKafkaQueues(List<PositionDTO> positionDTOS) throws Exception {
        this.positionProducer.publishListOnKafkaBulkTopic(positionDTOS);
    }

    @AuthClaimVerify
    @AuthClaimRule(claimProperty = CLAIM_PROPERTY, equalToValue = SCOPE_VALUE, required = true)
    @Override
    public void publishBluetoothCountingDataOnInternalKafkaQueues(List<BluetoothCountingDataDTO> bluetoothCountingDatumDTOS) throws Exception {
        this.bluetoothCountingDataProducer.publishListOnKafkaBulkTopic(convertListToBusinessList(bluetoothCountingDatumDTOS, BluetoothCountingData.class));
    }

    @AuthClaimVerify
    @AuthClaimRule(claimProperty = CLAIM_PROPERTY, equalToValue = SCOPE_VALUE, required = true)
    @Override
    public void publishPeopleCountingDataOnInternalKafkaQueues(List<PeopleCountingDataDTO> peopleCountingDatumDTOS) throws Exception {
        peopleCountingDatumDTOS.stream().filter(p -> StringUtils.isEmpty(p.getStopId()) && (p.getLongitude() == null || p.getLongitude() == null)).
                forEach(p -> {
                    throw new JsonIOException("Error in data with data with vehicle_id "+
                            p.getVehicleId()+": stopId, latitude and longitude cannot all be null");
                } );
        this.peopleCountingDataProducer.publishListOnKafkaBulkTopic(peopleCountingDatumDTOS);
    }

    @AuthClaimVerify
    @AuthClaimRule(claimProperty = CLAIM_PROPERTY, equalToValue = SCOPE_VALUE, required = true)
    @Override
    public void publishSeatCountingDataAggregateOnInternalKafkaQueues(List<SeatCountingDataAggregateDTO> seatCountingDataAggregateDTOData) throws Exception {
        this.seatCountingDataAggregateProducer.publishListOnKafkaBulkTopic(convertListToBusinessList(seatCountingDataAggregateDTOData,
                SeatCountingDataAggregate.class));
    }

    @AuthClaimVerify
    @AuthClaimRule(claimProperty = CLAIM_PROPERTY, equalToValue = SCOPE_VALUE, required = true)
    @Override
    public void publishStationCongestionOnInternalKafkaQueues(List<StationCongestionDTO> stationCongestionDTOData) throws Exception {
        this.stationCongestionProducer.publishListOnKafkaBulkTopic(stationCongestionDTOData);
    }

    private <S, T> List<T> convertListToBusinessList(List<S> sourceList, Class<T> targetClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<T> listToReturn = new ArrayList<>();

        for(S sourceObj : sourceList) {
            T targetObj = targetClass.getConstructor().newInstance();
            BeanUtils.copyProperties(sourceObj, targetObj);
            listToReturn.add(targetObj);
        }

        return listToReturn;
    }
}
