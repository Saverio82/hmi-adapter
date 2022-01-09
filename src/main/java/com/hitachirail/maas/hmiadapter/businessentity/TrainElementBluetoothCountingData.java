package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrainElementBluetoothCountingData extends BluetoothCountingData {

    private String trainElementId;
    private String trainId;

    @Builder
    public TrainElementBluetoothCountingData(Long tenantId, String serviceJourneyId, String sourceSystemId,
                                             String messageId, Integer diagnosticStatus, Long sysTimestamp, String deviceId,
                                             String scheduledStopPointId, Double latitude, Double longitude, Integer status,
                                             Long bleTs, String trainElementId, String trainId) {
        super(tenantId, serviceJourneyId, sourceSystemId, messageId, diagnosticStatus, sysTimestamp, deviceId, scheduledStopPointId, latitude, longitude, status, bleTs);
        this.trainElementId = trainElementId;
        this.trainId = trainId;
    }

    public TrainElementBluetoothCountingData(){}
}
