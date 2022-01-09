package com.hitachirail.maas.hmiadapter.businessentity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Device {

    private String deviceId;
    private String scheduledStopPointId;
    private Double latitude;
    private Double longitude;
    private Integer status;
    private Long bleTs;

    public Device(String deviceId, String scheduledStopPointId, Double latitude, Double longitude, Integer status, Long bleTs) {
        this.deviceId = deviceId;
        this.scheduledStopPointId = scheduledStopPointId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.bleTs = bleTs;
    }

    public Device(){}
}
