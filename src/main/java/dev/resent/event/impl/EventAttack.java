package dev.resent.event.impl;

import net.minecraft.entity.Entity;

public class EventAttack extends Event {

    public Entity target;
    public boolean cancelled;

    public EventAttack(Entity target) {
        this.target = target;
    }

    @Override
    public boolean isCancelled() { return cancelled; }
    @Override
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}
