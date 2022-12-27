package dev.resent.event.impl;

import net.kyori.event.Cancellable;
import net.minecraft.src.Packet;

public class PacketEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final Packet packet;
    private final boolean serverBound;

    public PacketEvent(Packet packet, boolean serverBound) {
        this.packet = packet;
        this.serverBound = serverBound;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public boolean isServerBound() {
        return this.serverBound;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
