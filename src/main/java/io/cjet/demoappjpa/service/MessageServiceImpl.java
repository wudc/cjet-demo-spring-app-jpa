package io.cjet.demoappjpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.cjet.demoappjpa.model.Message;
import io.cjet.demoappjpa.model.MessageRepository;

/**
 * Spring service is singleton
 * 
 * @author wudc
 *
 */

@SuppressWarnings("serial")
@Service
@Qualifier("MessageServiceImpl")
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public List<Message> getAllMessages(String topicId) {
		ArrayList<Message> messages = new ArrayList<Message>();
		messageRepository.findByTopicId(topicId).forEach(messages::add);
		return messages;
	}

	public Optional<Message> getMessage(String id) {
		return messageRepository.findById(id);
	}

	public Message getMessageByName(String name) {
		return messageRepository.findByName(name);
	}

	public void addMessage(Message message) {
		messageRepository.save(message);
	}
	
	public void updateMessage(Message message) {
		if (message.getId() == null ) {
			return;
		}
		else {
			messageRepository.save(message);
		}
	}
	
	public void deleteMessage(String id) {
		messageRepository.deleteById(id);
	}

}
