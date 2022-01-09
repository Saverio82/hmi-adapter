package com.hitachirail.maas.hmiadapter.service.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvaResponse {
    
    private String name;

    public ProvaResponse(String s) {
        this.name = s;
    }
}
