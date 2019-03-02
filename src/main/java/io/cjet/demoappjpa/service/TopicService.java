package io.cjet.demoappjpa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import io.cjet.demoappjpa.model.Topic;

public interface TopicService extends Serializable {
	public List<Topic> getAllTopics();
	public Optional<Topic> getTopic(String id);
	public Topic getTopicByName(String name);
	public void addTopic(Topic topic);
	public void updateTopic(Topic topic);
	public void deleteTopic(String id);
}
