package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PeopleCountingData {

    private Long tenantId;
    private String serviceJourneyId;
    private String sourceSystemId;
    private String messageId;
    private Integer diagnosticStatus;
    private Double latitude;
    private Double longitude;
    private Integer peopleIn;
    private Integer peopleOut;
    private Integer peopleCount;
    private Long sysTimestamp;
    private String scheduledStopPointId;
    private Long openTime;
    private Long closeTime;

    public PeopleCountingData(Long tenantId, String serviceJourneyId, String sourceSystemId, String messageId, Integer diagnosticStatus,
                              Double latitude, Double longitude, Integer peopleIn, Integer peopleOut, Integer peopleCount,
                              Long sysTimestamp, Long openTime, Long closeTime, String scheduledStopPointId){

        this.tenantId = tenantId;
        this.serviceJourneyId = serviceJourneyId;
        this.sourceSystemId = sourceSystemId;
        this.messageId = messageId;
        this.diagnosticStatus = diagnosticStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.peopleIn = peopleIn;
        this.peopleOut = peopleOut;
        this.peopleCount = peopleCount;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.sysTimestamp = sysTimestamp;
        this.scheduledStopPointId = scheduledStopPointId;
    }

    public PeopleCountingData(){}
}
