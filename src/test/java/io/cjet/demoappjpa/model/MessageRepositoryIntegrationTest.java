package io.cjet.demoappjpa.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryIntegrationTest {

	private Topic topic1, topic2;
	private String topic1Id = "1";
	private String topic2Id ="2";
	private Message topic1Message1, topic1Message2, topic2Message3, topic2Message4;
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;
    
    @Before
    public void setUp() {
    	topic1 = new Topic(topic1Id, "First", "First topic");
    	entityManager.persist(topic1);
    	
    	topic2 = new Topic(topic2Id, "Second", "Second topic");
    	entityManager.persist(topic2);
        
        topic1Message1 = new Message("1", "topic1Message", "Hello topic 1");
        topic1Message1.setTopic(topic1);
        topic1Message2 = new Message("2", "topic1Message", "Hello topic 1");
        topic1Message2.setTopic(topic1);
        topic2Message3 = new Message("3", "topic2Message", "Hello topic 2");
        topic2Message3.setTopic(topic2);
        
        entityManager.persist(topic1Message1);        
        entityManager.persist(topic1Message2);
        entityManager.persist(topic2Message3);
        
        topic2Message4 = new Message("4", "topic2Message", "Hello topic 2");
        topic2Message4.setTopic(topic2);
    }
    
    @Test
    public void saveMessageTest() {
     
        messageRepository.save(topic2Message4);
        Optional<Message> foundMessage = messageRepository.findById(topic2Message4.getId());
        
        assertNotNull(foundMessage);
        assertThat(foundMessage.get().getName()).isEqualTo(topic2Message4.getName());        
    }
    
    @Test
    public void getMessageTest() {
        
        Optional<Message> foundMessage = messageRepository.findById(topic1Message1.getId());
        
        assertNotNull(foundMessage);
        assertThat(foundMessage.get().getName()).isEqualTo(topic1Message1.getName());        
    }
    
    @Test
    public void getMessageByNameTest() {
        
        ArrayList<Message> messages = new ArrayList<Message>();
		messageRepository.findByTopicId("1").forEach(messages::add);
		Message foundMessage = messages.stream().filter(t -> t.getName().equals(topic1Message1.getName())).findFirst().get();
        
        assertNotNull(foundMessage);
        assertThat(foundMessage.getName()).isEqualTo(topic1Message1.getName());        
    }

    /**
     * Find all messages of a topic
     */
    @Test
    public void getAllMessagesTest() {
		ArrayList<Message> messages = new ArrayList<Message>();
		messageRepository.findByTopicId(topic1Message1.getTopic().getId()).forEach(messages::add);
		        
        assertNotNull(messages);
        assertEquals(2, messages.size());
        Stream<Message> foundMessages = messages.stream().filter(t -> t.getTopic().getId().equals(topic1Id));
        assertEquals(2l, foundMessages.count());       
    }
    
}
