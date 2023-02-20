package dev.resent.javascript;

import org.teavm.jso.JSBody;
import org.teavm.jso.dom.events.Event;

public class LoadScreen {

    @JSBody(params = { "event", "epkSize" }, script = "setBarProg(event, epkSize)")
    public static native void setBarProgress(Event event, int epkSize);

    @JSBody(params = { "version" }, script = "setVersion(version)")
    public static native void setClientVersion(String version);

    @JSBody( script = "die()")
    public static native void remove();
    
}
