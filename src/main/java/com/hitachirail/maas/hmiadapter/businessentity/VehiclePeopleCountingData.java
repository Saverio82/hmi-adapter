package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VehiclePeopleCountingData extends PeopleCountingData {

    private String vehicleId;

    @Builder
    public VehiclePeopleCountingData(Long tenantId, String serviceJourneyId, String sourceSystemId, String messageId, Integer diagnosticStatus, Double latitude, Double longitude, Integer peopleIn, Integer peopleOut, Integer peopleCount, Long sysTimestamp,
                                     Long openTime, Long closeTime, String vehicleId, String scheduledStopPointId) {
        super(tenantId, serviceJourneyId, sourceSystemId, messageId, diagnosticStatus, latitude, longitude, peopleIn, peopleOut, peopleCount, sysTimestamp, openTime, closeTime, scheduledStopPointId);
        this.vehicleId = vehicleId;
    }

    public VehiclePeopleCountingData(){};
}
