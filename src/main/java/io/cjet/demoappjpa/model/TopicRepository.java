package io.cjet.demoappjpa.model;

import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data APIs auto generate all the database interaction implementations
 * 1) getAllTopics
 * 2) getTopic(String id)
 * 3) UpdateTopic(Topic topic)
 * 4) deleteTopic(String id)
 * 
 * @author wudc
 *
 */
public interface TopicRepository extends CrudRepository <Topic, String>{

	public Topic findByName(String name);
}
