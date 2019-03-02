package io.cjet.demoappjpa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import io.cjet.demoappjpa.model.Message;

public interface MessageService extends Serializable {
	public List<Message> getAllMessages(String topicId);
	public Optional<Message> getMessage(String id);
	public Message getMessageByName(String name);
	public void addMessage(Message message);
	public void updateMessage(Message message);
	public void deleteMessage(String id);
}
