package dev.resent.event.impl;

public class EventKey extends Event{
        public int key;

        public int getKey() {
            return key;
        }

        public EventKey(int key) {
            this.key = key;
        }
}
