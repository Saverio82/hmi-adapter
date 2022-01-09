package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrainElementPosition extends Position {

    private String trainElementId;
    private String trainId;


    @Builder
    public TrainElementPosition(String trainElementId, Long tenantId, String trainId, String serviceJourneyId, String sourceSystemId, String messageId,
                                Integer diagnosticStatus, Double latitude, Double longitude, Long sysTimestamp) {
        super(tenantId, serviceJourneyId, sourceSystemId, messageId,
                diagnosticStatus, latitude, longitude,
                sysTimestamp);
        this.trainElementId = trainElementId;
        this.trainId = trainId;
    }

    public TrainElementPosition(){};

}
