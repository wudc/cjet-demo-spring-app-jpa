package io.cjet.demoappjpa.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TopicRepositoryIntegrationTest {
	Topic topic1, topic2, topic3, topic4, topic5;
	String topic1Id = "1";
	String topic2Name = "Second";
	String topic3Description = "Third topic";

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TopicRepository topicRepository;

	@Before
	public void setUp() {
		topic1 = new Topic(topic1Id, "First", "First topic");
		entityManager.persist(topic1);
		topic2 = new Topic("2", topic2Name, "Second topic");
		entityManager.persist(topic2);
		topic3 = new Topic("3", "Third", topic3Description);
		entityManager.persist(topic3);

		topic4 = new Topic("4", "Fourth", "Fourth topic");
		
		topic5 = new Topic("5", "Fifth", "Fifth topic");
		entityManager.persist(topic5);
	}

	@Test
	public void getAllTopicsTest() {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		topicRepository.findAll().forEach(topics::add);
		assertNotNull(topics);
		assertEquals(4, topics.size());
		assertThat(topics.get(0).getId()).isEqualTo(topic1Id);
		assertThat(topics.get(1).getName()).isEqualTo(topic2Name);
		assertThat(topics.get(2).getDescription()).isEqualTo(topic3Description);
	}

	@Test
	public void getTopicTest() {

		Optional<Topic> topic = topicRepository.findById(topic1Id);
		assertTrue(topic.isPresent());
		assertThat(topic.get().getName()).isEqualTo(topic1.getName());
	}

	@Test
	public void getTopicByNameTest() {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		topicRepository.findAll().forEach(topics::add);
		Topic topic = topics.stream().filter(t -> t.getName().equals(topic2Name)).findFirst().get();

		assertNotNull(topic);
		assertThat(topic.getName()).isEqualTo(topic2.getName());
	}

	@Test
	public void addTopicTest() {

		topicRepository.save(topic4);

		Optional<Topic> topic = topicRepository.findById(topic4.getId());

		assertNotNull(topic);
		assertThat(topic.get().getName()).isEqualTo(topic4.getName());
	}

	@Test
	public void updateTopicTest() {
		String oldTopic3Name = topic3.getName();
		String newTopic3Name = "ThirdTopic";
		topic3.setName(newTopic3Name);

		Topic updatedTopic = topicRepository.save(topic3);

		assertNotNull(updatedTopic);
		assertThat(updatedTopic.getName()).isEqualTo(newTopic3Name);
		assertThat(updatedTopic.getName()).isNotEqualTo(oldTopic3Name);
	}

	@Test
	public void deleteTopicTest() {
		topicRepository.deleteById(topic5.getId());
		
		Optional<Topic> topic = topicRepository.findById(topic5.getId());
		assertFalse(topic.isPresent());
	}

}
