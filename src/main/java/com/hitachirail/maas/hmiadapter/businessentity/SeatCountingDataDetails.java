package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SeatCountingDataDetails {

    private String sensorId;
    private Integer sensorType;
    private Integer status;
    private Long seatTimestamp;

    @Builder
    public SeatCountingDataDetails(String sensorId, Integer sensorType, Integer status, Long seatTimestamp) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.status = status;
        this.seatTimestamp = seatTimestamp;
    }

    public SeatCountingDataDetails(){};
}
