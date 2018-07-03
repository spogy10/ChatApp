package poliv.jr.com.chat;

import poliv.jr.com.chat.ChatInteractor;
import poliv.jr.com.chat.ChatRepository;
import poliv.jr.com.chat.ChatRepositoryImpl;

public class ChatInteractorImpl implements ChatInteractor {

    ChatRepository chatRepository;

    public ChatInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        chatRepository.sendMessage(msg);
    }

    @Override
    public void setRecipient(String recipient) {
        chatRepository.sendMessage(recipient);
    }

    @Override
    public void destroyChatListener() {
        chatRepository.destroyChatListener();
    }

    @Override
    public void subscribeForChatUpdates() {
        chatRepository.subscribeForChatUpdates();
    }

    @Override
    public void unSubscribeForChatUpdates() {
        chatRepository.unSubscribeForChatUpdates();
    }
}
