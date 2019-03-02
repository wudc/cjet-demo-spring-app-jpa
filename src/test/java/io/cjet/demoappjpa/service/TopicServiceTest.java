package io.cjet.demoappjpa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.Arrays;
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

import io.cjet.demoappjpa.model.Topic;
import io.cjet.demoappjpa.model.TopicRepository;

@RunWith(SpringRunner.class)
public class TopicServiceTest {

	private Topic topic2, topic3, topic4;

	@TestConfiguration
	static class TopicServiceImplTestContextConfiguration {

		@Bean
		public TopicService topicService() {
			return new TopicServiceImpl();
		}
	}

	@Autowired
	private TopicService topicService;

	@MockBean
	private TopicRepository topicRepository;

	@SuppressWarnings("rawtypes")
	@Before
    public void setUp() {

		topic2 = new Topic("2", "Second", "Second Topic");
		topic3 = new Topic("3", "Third", "Third Topic");
		topic4 = new Topic("4", "Fourth", "Fourth Topic");
     
		ArrayList<Topic> topics = new ArrayList<Topic>();
        Mockito.when(findAllTopic()).thenReturn(topics);       
        Mockito.when(topicRepository.findByName(topic3.getName())).thenReturn(topic3);
        Mockito.when(topicRepository.findById(topic2.getId())).thenReturn(Optional.of(topic2));

        Mockito.doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(topicRepository).save(topic4);
			
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(topicRepository).deleteById(topic4.getId());
    }

	private List<Topic> findAllTopic() {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}
	
	@Test
	public void getAllTopicsTest() throws Exception {

		topicService.getAllTopics();
        Mockito.verify(topicRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(topicRepository);
	}

	@Test
	public void getTopicTest() throws Exception {
		Optional<Topic> topic = topicService.getTopic(topic2.getId());

		assertTrue(topic.isPresent());
		assertEquals("2", topic.get().getId());
		assertEquals("Second", topic.get().getName());
		assertEquals("Second Topic", topic.get().getDescription());
	}

	@Test
	public void getTopicByNameTest() throws Exception {
		Topic topic = topicService.getTopicByName(topic3.getName());
		
		System.out.println(Arrays.asList(topic));
		
		assertEquals("3", topic.getId());
		assertEquals("Third", topic.getName());
		assertEquals("Third Topic", topic.getDescription());
	}

	@Test
	public void addTopicTest() throws Exception {

		topicService.addTopic(topic4);
        Mockito.verify(topicRepository, VerificationModeFactory.times(1)).save(topic4);
        Mockito.reset(topicRepository);
	}

	@Test
	public void updateTopicTest() throws Exception {
		Topic topic4 = new Topic("4", "Five", "Fifth Topic");
		topicService.updateTopic(topic4);

        Mockito.verify(topicRepository, VerificationModeFactory.times(1)).save(topic4);
        Mockito.reset(topicRepository);
	}

	@Test
	public void deleteTopicTest() throws Exception {

		topicService.deleteTopic(topic4.getId());
        Mockito.verify(topicRepository, VerificationModeFactory.times(1)).deleteById(topic4.getId());
        Mockito.reset(topicRepository);

	}
}
