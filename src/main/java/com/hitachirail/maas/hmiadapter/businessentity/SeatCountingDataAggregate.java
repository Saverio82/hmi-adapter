package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SeatCountingDataAggregate {

    private String serviceJourneyId;
    private String sourceSystemId;
    private Long tenantId;
    private Integer seatOccupancy;
    private Long sysTimestamp;
    private String messageId;
    private Integer diagnosticStatus;
    private List<SeatCountingDataDetails> seatCountingDataDetailsList;

    public SeatCountingDataAggregate(String serviceJourneyId, String sourceSystemId, Long tenantId, Integer seatOccupancy, Long sysTimestamp, String messageId,
                                     Integer diagnosticStatus, List<SeatCountingDataDetails> seatCountingDataDetailsList) {
        this.serviceJourneyId = serviceJourneyId;
        this.sourceSystemId = sourceSystemId;
        this.tenantId = tenantId;
        this.seatOccupancy = seatOccupancy;
        this.sysTimestamp = sysTimestamp;
        this.messageId = messageId;
        this.diagnosticStatus = diagnosticStatus;
        this.seatCountingDataDetailsList = seatCountingDataDetailsList;
    }

    public SeatCountingDataAggregate() {
    }

}
