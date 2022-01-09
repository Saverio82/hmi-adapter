package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrainElementSeatCountingDataAggregate extends SeatCountingDataAggregate{

    private String trainElementId;
    private String trainId;

    @Builder
    public TrainElementSeatCountingDataAggregate(String serviceJourneyId, String sourceSystemId, Long tenantId, Integer seatOccupancy, Long sysTimestamp,
                                                 String messageId, Integer diagnosticStatus, List<SeatCountingDataDetails> seatCountingDataDetailsList, String trainElementId, String trainId) {
        super(serviceJourneyId, sourceSystemId, tenantId, seatOccupancy, sysTimestamp, messageId, diagnosticStatus, seatCountingDataDetailsList);
        this.trainElementId = trainElementId;
        this.trainId = trainId;
    }

    public TrainElementSeatCountingDataAggregate(){}
}
