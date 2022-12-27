package dev.resent.event.impl;

import net.kyori.event.Cancellable;

public class ClientChatEvent extends Event implements Cancellable{
    public String message;
    public boolean canceled;

    public ClientChatEvent(String message) { this.message = message; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    @Override
    public boolean isCancelled() { return canceled; }
    @Override
    public void setCancelled(boolean canceled) { this.canceled = canceled; }
    
}
