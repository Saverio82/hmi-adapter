package com.hitachirail.maas.hmiadapter.beans;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StationCongestion {

    private String stopId;
    private String parentStopId;
    private String sourceSystemId;
    private String operator;
    private String messageId;
    private Integer diagnosticStatus;
    private Integer peopleIn;
    private Integer peopleOut;
    private Integer peopleCount;
    private Long sysTimestamp;

}
