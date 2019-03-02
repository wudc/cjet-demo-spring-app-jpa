package io.cjet.demoappjpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.cjet.demoappjpa.model.Message;
import io.cjet.demoappjpa.model.MessageRepository;
import io.cjet.demoappjpa.model.Topic;

@RunWith(SpringRunner.class)
public class MessageServiceTest {
	
	private Topic topic1, topic2;
	private String topic1Id = "1";
	private String topic2Id ="2";
	private Message topic1Message1, topic1Message2, topic2Message3, topic2Message4;
	
	@TestConfiguration
	static class MessageServiceImplTestContextConfiguration {

		@Bean
		public MessageService messageService() {
			return new MessageServiceImpl();
		}
	}

	@Autowired
	private MessageService messageService;

	@MockBean
	private MessageRepository messageRepository;
	
    @SuppressWarnings("rawtypes")
	@Before
    public void setUp() {
    	topic1 = new Topic(topic1Id, "First", "First topic");
    	topic2 = new Topic(topic2Id, "Second", "Second topic");
        
        topic1Message1 = new Message("1", "topic1Message", "Hello topic 1");
        topic1Message1.setTopic(topic1);
        topic1Message2 = new Message("2", "topic1Message2", "Hello topic 1");
        topic1Message2.setTopic(topic1);
        topic2Message3 = new Message("3", "topic2Message3", "Hello topic 2");
        topic2Message3.setTopic(topic2);
        
        topic2Message4 = new Message("4", "topic2Message4", "Hello topic 2");
        topic2Message4.setTopic(topic2);
        
		ArrayList<Message> messages = new ArrayList<Message>();
        Mockito.when(findAllMessages()).thenReturn(messages);       
        Mockito.when(messageRepository.findByName(topic1Message2.getName())).thenReturn(topic1Message2);
        Mockito.when(messageRepository.findById(topic2Message3.getId())).thenReturn(Optional.of(topic2Message3));

        Mockito.doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(messageRepository).save(topic2Message4);
			
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(messageRepository).deleteById(topic2Message4.getId());
    }
    
	private List<Message> findAllMessages() {
		ArrayList<Message> messages = new ArrayList<Message>();
		messageRepository.findByTopicId(topic1Message1.getTopic().getId()).forEach(messages::add);
		return messages;
	}
	
	@Test
	public void getAllMessagesTest() throws Exception {

		messageService.getAllMessages(topic1Message1.getTopic().getId());
        Mockito.verify(messageRepository, VerificationModeFactory.times(1)).findByTopicId(topic1Message1.getTopic().getId());
        Mockito.reset(messageRepository);
	}

    @Test
    public void getMessageTest() {
        
    	Optional<Message> foundMessage = messageService.getMessage(topic2Message3.getId());
        
        assertNotNull(foundMessage);
        assertThat(foundMessage.get().getId()).isEqualTo(topic2Message3.getId());
        Mockito.verify(messageRepository, VerificationModeFactory.times(1)).findById(topic2Message3.getId());
        Mockito.reset(messageRepository);
    }
    
    @Test
    public void getMessageByNameTest() {
        
    	Message foundMessage = messageService.getMessageByName(topic1Message2.getName());
        
        assertNotNull(foundMessage);
        assertThat(foundMessage.getName()).isEqualTo(topic1Message2.getName());
        Mockito.verify(messageRepository, VerificationModeFactory.times(1)).findByName(topic1Message2.getName());
        Mockito.reset(messageRepository);
    }
    
    @Test
    public void addMessageTest() {
    	messageService.addMessage(topic2Message4);
        Mockito.verify(messageRepository, VerificationModeFactory.times(1)).save(topic2Message4);
        Mockito.reset(messageRepository);
    }
    
    @Test
    public void updateMessageTest() {
    	messageService.updateMessage(topic2Message4);
        Mockito.verify(messageRepository, VerificationModeFactory.times(1)).save(topic2Message4);
        Mockito.reset(messageRepository);
    }
    
    @Test
    public void deleteMessageTest() {
    	messageService.deleteMessage(topic2Message4.getId());
        Mockito.verify(messageRepository, VerificationModeFactory.times(1)).deleteById(topic2Message4.getId());
        Mockito.reset(messageRepository);
    }
    
    
}
