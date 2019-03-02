package io.cjet.demoappjpa.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cjet.demoappjpa.model.Topic;
import io.cjet.demoappjpa.service.TopicService;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicController.class)
public class TopicControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	@Qualifier("TopicServiceImpl")
	private TopicService topicService;

	private Topic topic1, topic2, topic3, topic4;
	
	@Before
	public void setUp() {
		topic1 = new Topic("1", "First", "First Topic");
		topic2 = new Topic("2", "Second", "Second Topic");
		topic3 = new Topic("3", "Third", "Third Topic");
		topic4 = new Topic("4", "Fourth", "Fourth Topic");
	}

	@Test
	public void getAllTopicsTest() throws Exception {

		List<Topic> topics = Arrays.asList(topic1, topic2, topic3);

		given(topicService.getAllTopics()).willReturn(topics);

		mvc.perform(get("/topics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].id", is(topic1.getId())))
				.andExpect(jsonPath("$[0].name", is(topic1.getName())))
				.andExpect(jsonPath("$[0].description", is(topic1.getDescription())))
				.andExpect(jsonPath("$[1].id", is(topic2.getId())))
				.andExpect(jsonPath("$[1].name", is(topic2.getName())))
				.andExpect(jsonPath("$[1].description", is(topic2.getDescription())))
				.andExpect(jsonPath("$[2].id", is(topic3.getId())))
				.andExpect(jsonPath("$[2].name", is(topic3.getName())))
				.andExpect(jsonPath("$[2].description", is(topic3.getDescription()))).andDo(print());
	}

	@Test
	public void getTopicTest() throws Exception {

		given(topicService.getTopic("3")).willReturn(Optional.of(topic3));

		mvc.perform(get("/topics/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(topic3.getId()))).andExpect(jsonPath("$.name", is(topic3.getName())))
				.andExpect(jsonPath("$.description", is(topic3.getDescription()))).andDo(print());
	}

	@Test
	public void getTopicByNameTest() throws Exception {

		given(topicService.getTopicByName("Second")).willReturn(topic2);

		mvc.perform(get("/topics/byname/Second").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(topic2.getId()))).andExpect(jsonPath("$.name", is(topic2.getName())))
				.andExpect(jsonPath("$.description", is(topic2.getDescription())));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void addTopicTest() throws Exception {
		String inputJson = mapToJson(topic4);
		
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(topicService).addTopic(topic4);
		
		mvc.perform(MockMvcRequestBuilders.post("/topics").content(inputJson).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void updateTopicTest() throws Exception {

		String inputJson = mapToJson(topic3);
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(topicService).updateTopic(topic4);
		
		mvc.perform(MockMvcRequestBuilders.put("/topics/update").content(inputJson).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void deleteTopicTest() throws Exception {

		String inputJson = mapToJson(topic1);
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(topicService).deleteTopic(topic4.getId());
		
		mvc.perform(MockMvcRequestBuilders.delete("/topics/3").content(inputJson).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

}
