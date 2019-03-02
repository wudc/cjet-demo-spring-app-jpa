package io.cjet.demoappjpa.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data APIs auto generate all the database interaction implementations
 * 1) getAllMessage
 * 2) getMessage(String id)
 * 3) UpdateMessage(Message message)
 * 4) deleteMessage(String id)
 * 
 * @author wudc
 *
 */
public interface MessageRepository extends CrudRepository <Message, String>{

	//String Data framework creates an implementation of this method automatically
	public List<Message> findByTopicId(String topicId);
	
	public Message findByName(String name);
}
