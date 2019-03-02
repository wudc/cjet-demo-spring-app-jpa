package io.cjet.demoappjpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.cjet.demoappjpa.model.Topic;
import io.cjet.demoappjpa.model.TopicRepository;

/**
 * Spring service is singleton
 * 
 * @author wudc
 *
 */

@SuppressWarnings("serial")
@Service
@Qualifier("TopicServiceImpl")
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	public List<Topic> getAllTopics() {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}

	public Optional<Topic> getTopic(String id) {
		return topicRepository.findById(id);
	}

	public Topic getTopicByName(String name) {
		return topicRepository.findByName(name);
	}

	public void addTopic(Topic topic) {
		topicRepository.save(topic);
	}
	
	public void updateTopic(Topic topic) {
		if (topic.getId() == null ) {
			return;
		}
		else {
			topicRepository.save(topic);
		}
	}
	
	public void deleteTopic(String id) {
		topicRepository.deleteById(id);
	}
}
