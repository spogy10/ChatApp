package poliv.jr.com.chat;

import poliv.jr.com.chat.ChatRepository;
import poliv.jr.com.chat.ChatRepositoryImpl;
import poliv.jr.com.chat.ChatSessionInteractor;

public class ChatSessionInteractorImpl implements ChatSessionInteractor {

    ChatRepository chatRepository;

    public ChatSessionInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        chatRepository.changeUserConnectionStatus(online);
    }
}
