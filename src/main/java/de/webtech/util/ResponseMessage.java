package de.webtech.util;

import java.util.HashSet;
import java.util.Set;

public class ResponseMessage {
    private Set<String> messages;

    public ResponseMessage(String message) {
        this.messages = new HashSet<>();
        this.messages.add(message);
    }

    public ResponseMessage() {
    }

    public ResponseMessage(Set<String> messages) {
        this.messages = messages;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }
}
