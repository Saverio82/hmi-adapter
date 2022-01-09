package com.hitachirail.maas.hmiadapter.controller;


import com.hitachirail.maas.hmiadapter.businessentity.*;
import com.hitachirail.maas.hmiadapter.service.model.Prova;
import com.hitachirail.maas.hmiadapter.service.model.ProvaResponse;
import com.hitachirail.maas.hmiadapter.service.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class PushMessages {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;



    public PushMessages(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }


    public void sendMessage(List<?>... data) {

        for(List<?> item: data){
            for(Object itemDett: item){
                String tenant = null;
                if (itemDett instanceof TrainElementBluetoothCountingData) {
                    tenant = String.valueOf(((TrainElementBluetoothCountingData) itemDett).getTenantId());
                } else if (itemDett instanceof VehicleBluetoothCountingData) {
                    tenant = String.valueOf(((VehicleBluetoothCountingData) itemDett).getTenantId());
                } else if (itemDett instanceof VehiclePeopleCountingData) {
                    tenant = String.valueOf(((VehiclePeopleCountingData) itemDett).getTenantId());
                } else if (itemDett instanceof VehiclePosition) {
                    tenant = String.valueOf(((VehiclePosition) itemDett).getTenantId());
                } else if (itemDett instanceof VehicleSeatCountingDataAggregate) {
                    tenant = String.valueOf(((VehicleSeatCountingDataAggregate) itemDett).getTenantId());
                }
                Set<String> userTenant = simpUserRegistry.getUsers().stream().map(u ->((User)((UsernamePasswordAuthenticationToken) u.getPrincipal()).getDetails()).getTenant()).collect(Collectors.toSet());
                if (tenant != null &&  userTenant.contains(tenant))
                    simpMessagingTemplate.convertAndSend("/topic/"+tenant+"/message",
                            itemDett);
            }

        }
    }


    @MessageMapping("/user-info")
    @SendToUser("/topic/user-info")
    public ProvaResponse getUser(Prova user) {
        return new ProvaResponse("Hi " + user.getName());
    }


}
