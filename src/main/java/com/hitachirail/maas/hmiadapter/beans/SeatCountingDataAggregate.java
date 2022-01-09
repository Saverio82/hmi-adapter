package com.hitachirail.maas.hmiadapter.beans;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SeatCountingDataAggregate {

    private String parentVehicleId;
    private String vehicleId;
    private String tripId;
    private String sourceSystemId;
    private Integer type;
    private String operator;
    private Integer seatingOccupancy;
    private Long sysTimestamp;
    private String messageId;
    private Integer diagnosticStatus;
    private List<SeatCountingDataDetails> seatCountingDataDetailsList;

}
