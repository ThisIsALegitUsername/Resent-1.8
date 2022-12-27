package dev.resent.event.impl;

import net.minecraft.src.Packet38EntityStatus;

public class EntityStatusEvent extends Event{

    public byte status;

    public EntityStatusEvent(Packet38EntityStatus event){
        this.status = event.entityStatus;
    }
    
}