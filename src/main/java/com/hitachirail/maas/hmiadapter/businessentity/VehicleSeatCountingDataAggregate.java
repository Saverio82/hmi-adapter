package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VehicleSeatCountingDataAggregate extends SeatCountingDataAggregate{

    private String vehicleId;

    @Builder
    public VehicleSeatCountingDataAggregate(String serviceJourneyId, String sourceSystemId, Long tenantId, Integer seatOccupancy,
                                            Long sysTimestamp, String messageId, Integer diagnosticStatus, List<SeatCountingDataDetails> seatCountingDataDetailsList, String vehicleId) {
        super(serviceJourneyId, sourceSystemId, tenantId, seatOccupancy, sysTimestamp, messageId, diagnosticStatus, seatCountingDataDetailsList);
        this.vehicleId = vehicleId;
    }

    public VehicleSeatCountingDataAggregate(){}
}
