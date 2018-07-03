package poliv.jr.com.chat.ui;

import poliv.jr.com.chat.entities.ChatMessage;

public interface ChatView {
    void sendMessage();
    void onMessageReceived(ChatMessage msg);
}
