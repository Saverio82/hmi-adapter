package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BluetoothCountingData {

    private Long tenantId;
    private String serviceJourneyId;
    private String sourceSystemId;
    private String messageId;
    private Integer diagnosticStatus;
    private Long sysTimestamp;
    private String deviceId;
    private String scheduledStopPointId;
    private Double latitude;
    private Double longitude;
    private Integer status;
    private Long bleTs;


    public BluetoothCountingData(Long tenantId, String serviceJourneyId, String sourceSystemId, String messageId,
                                 Integer diagnosticStatus, Long sysTimestamp, String deviceId, String scheduledStopPointId,
                                 Double latitude, Double longitude, Integer status, Long bleTs) {
        this.tenantId = tenantId;
        this.serviceJourneyId = serviceJourneyId;
        this.sourceSystemId = sourceSystemId;
        this.messageId = messageId;
        this.diagnosticStatus = diagnosticStatus;
        this.sysTimestamp = sysTimestamp;
        this.deviceId = deviceId;
        this.scheduledStopPointId = scheduledStopPointId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.bleTs = bleTs;
    }

    public BluetoothCountingData(){}
}
