package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrainElementPeopleCountingData extends PeopleCountingData {

    private String trainElementId;
    private String trainId;

    @Builder
    public TrainElementPeopleCountingData(Long tenantId, String serviceJourneyId, String sourceSystemId, String messageId, Integer diagnosticStatus, Double latitude, Double longitude, Integer peopleIn, Integer peopleOut, Integer peopleCount, Long sysTimestamp,
                                          Long openTime, Long closeTime, String trainElementId, String trainId, String scheduledStopPointId) {
        super(tenantId, serviceJourneyId, sourceSystemId, messageId, diagnosticStatus, latitude, longitude,
                peopleIn, peopleOut, peopleCount, sysTimestamp, openTime, closeTime, scheduledStopPointId);
        this.trainElementId = trainElementId;
        this.trainId = trainId;
    }

    public TrainElementPeopleCountingData(){}
}
