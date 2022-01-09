package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VehiclePosition extends Position {

    private String vehicleId;

    @Builder
    public VehiclePosition(String vehicleId, Long tenantId, String serviceJourneyId, String sourceSystemId, String messageId,
                           Integer diagnosticStatus, Double latitude, Double longitude, Long sysTimestamp) {
        super(tenantId, serviceJourneyId, sourceSystemId, messageId,
                diagnosticStatus, latitude, longitude,
                sysTimestamp);
        this.vehicleId = vehicleId;

    }


    public VehiclePosition() {
    }


}
