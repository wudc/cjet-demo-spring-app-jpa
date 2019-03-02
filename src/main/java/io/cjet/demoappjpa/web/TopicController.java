package io.cjet.demoappjpa.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.cjet.demoappjpa.model.Topic;
import io.cjet.demoappjpa.service.TopicService;

/**
 * GET /topics ->gets all topics http://localhost:8080/topics
 * GET /topics/name->gets topic by name http://localhost:8080/topics/name/First
 * GET /topics/id->gets topic by id http://localhost:8080/topics/1
 * POST /topic->Creates new a topic http://localhost:8080/topics
 * PUT /topics/id->Updates the topic http://localhost:8080/topics
 * DELETE /topics/id->deletes the topic http://localhost:8080/topics/3
 * 
 * @author wudc
 *
 */
@RestController
public class TopicController {

	@Autowired
	@Qualifier("TopicServiceImpl") 
	private TopicService topicService;
	
	@RequestMapping(method=RequestMethod.GET, value="/topics")
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/topics/{id}")
	public Optional<Topic> getTopic(@PathVariable String id) {
		return topicService.getTopic(id);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/topics/byname/{name}")
	public Topic getTopicByName(@PathVariable String name) {
		return topicService.getTopicByName(name);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/topics")
	public void addTopic(@RequestBody Topic topic) {
		topicService.addTopic(topic);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/topics/update")
	public void updateTopic(@RequestBody Topic topic) {
		topicService.updateTopic(topic);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}")
	public void deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
	}
}
