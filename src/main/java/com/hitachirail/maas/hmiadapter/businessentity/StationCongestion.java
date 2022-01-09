package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class StationCongestion {

    private String scheduledStopPointId;
    private String parentStopPlaceId;
    private String sourceSystemId;
    private Long tenantId;
    private String messageId;
    private Integer diagnosticStatus;
    private Integer peopleIn;
    private Integer peopleOut;
    private Integer peopleCount;
    private Long sysTimestamp;

@Builder
    public StationCongestion (
        String scheduledStopPointId,
        String parentStopPlaceId,
        Long tenantId,
     String messageId,
     Integer diagnosticStatus,
     String sourceSystemId,
     Long sysTimestamp,
     Integer peopleIn,
     Integer peopleOut,
     Integer peopleCount)
    {
        this.scheduledStopPointId = scheduledStopPointId;
        this.parentStopPlaceId = parentStopPlaceId;
        this.diagnosticStatus = diagnosticStatus;
        this.tenantId = tenantId;
        this.messageId = messageId;;
        this.sourceSystemId=sourceSystemId;
        this.sysTimestamp=sysTimestamp;
        this.peopleIn=peopleIn;
        this.peopleOut= peopleOut;
        this.peopleCount= peopleCount;
    }

}
