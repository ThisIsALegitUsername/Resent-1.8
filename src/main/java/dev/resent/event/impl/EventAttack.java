package dev.resent.event.impl;

import net.kyori.event.Cancellable;
import net.minecraft.src.Entity;

public class EventAttack extends Event implements Cancellable{
    public Entity target;
    private boolean cancelled;

    public Entity getTarget() { return target; }
    public void setTarget(Entity target) { this.target = target; }
    public EventAttack(Entity target) { this.target = target; }
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
}
