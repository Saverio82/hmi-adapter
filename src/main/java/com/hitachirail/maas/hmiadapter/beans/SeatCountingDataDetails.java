package com.hitachirail.maas.hmiadapter.beans;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SeatCountingDataDetails {

    private String sensorId;
    private Integer sensorType;
    private Integer status;
    private Long seatTimestamp;

}
