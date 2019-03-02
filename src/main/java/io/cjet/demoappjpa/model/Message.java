package io.cjet.demoappjpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Message {
	
	@Id
	private String id;
	private String name;
	private String description;
	
	//Associate this message to a topic. Type must be an ENTITY
	//Spring Data creates a Topic Id column in the Message table as foreign key
	@ManyToOne
	private Topic topic;
	
	public Message() {}
	
	public Message(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
}
