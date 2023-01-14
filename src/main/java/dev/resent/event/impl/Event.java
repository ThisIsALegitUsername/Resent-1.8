package dev.resent.event.impl;

public abstract class Event {

    public abstract boolean isCancelled();

    public abstract void setCancelled(boolean cancelled);

    public void setType(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public EventType type;

    public boolean isPre() {
        if (type == null) return false;

        return type == EventType.pre;
    }

    public boolean isPost() {
        if (type == null) return false;

        return type == EventType.post;
    }

    public enum EventType {
        pre,
        post
    }
}
