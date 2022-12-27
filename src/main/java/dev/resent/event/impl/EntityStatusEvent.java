package dev.resent.event.impl;

import net.minecraft.network.play.server.S19PacketEntityStatus;

public class EntityStatusEvent extends Event{

    public byte status;

    public EntityStatusEvent(S19PacketEntityStatus event){
        this.status = event.logicOpcode;
    }
    
}