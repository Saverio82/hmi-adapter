package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VehicleBluetoothCountingData extends BluetoothCountingData{

    private String vehicleId;

    @Builder
    public VehicleBluetoothCountingData(Long tenantId, String serviceJourneyId, String sourceSystemId,
                                        String messageId, Integer diagnosticStatus, Long sysTimestamp, String deviceId,
                                        String scheduledStopPointId, Double latitude, Double longitude, Integer status,
                                        Long bleTs, String vehicleId) {
        super(tenantId, serviceJourneyId, sourceSystemId, messageId, diagnosticStatus, sysTimestamp, deviceId, scheduledStopPointId, latitude, longitude, status, bleTs);
        this.vehicleId = vehicleId;
    }

    public VehicleBluetoothCountingData() {}
}
