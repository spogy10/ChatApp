package poliv.jr.com.chat.events;

import poliv.jr.com.chat.entities.ChatMessage;

public class ChatEvent {
    ChatMessage msg;

    public ChatEvent(ChatMessage msg) {
        this.msg = msg;
    }

    public ChatMessage getMessage() {
        return msg;
    }
}
