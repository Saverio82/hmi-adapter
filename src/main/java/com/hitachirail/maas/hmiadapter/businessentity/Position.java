package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public  class Position {

    private Long tenantId;
    private String serviceJourneyId;
    private String sourceSystemId;
    private String messageId;
    private Integer diagnosticStatus;
    private Double latitude;
    private Double longitude;
    private Long sysTimestamp;


    public Position(Long tenantId, String serviceJourneyId, String sourceSystemId, String messageId, Integer diagnosticStatus,
                    Double latitude, Double longitude, Long sysTimestamp) {

        this.tenantId = tenantId;
        this.serviceJourneyId = serviceJourneyId;
        this.sourceSystemId = sourceSystemId;
        this.messageId = messageId;
        this.diagnosticStatus = diagnosticStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sysTimestamp = sysTimestamp;
    }

    public Position() {
    }


}
