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

import io.cjet.demoappjpa.model.Message;
import io.cjet.demoappjpa.model.Topic;
import io.cjet.demoappjpa.service.MessageService;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	@Qualifier("MessageServiceImpl")
	private MessageService messageService;

	private Topic topic1, topic2;
	private String topic1Id = "1";
	private String topic2Id = "2";
	private Message topic1Message1, topic1Message2, topic2Message3, topic2Message4;

	@Before
	public void setUp() {
		topic1 = new Topic(topic1Id, "First", "First topic");
		topic2 = new Topic(topic2Id, "Second", "Second topic");

		// topic 1 messages
		topic1Message1 = new Message("1", "topic1Message", "Hello topic 1");
		topic1Message1.setTopic(topic1);
		topic1Message2 = new Message("2", "topic1Message", "Hello topic 1");
		topic1Message2.setTopic(topic1);

		// topic 2 messages
		topic2Message3 = new Message("3", "topic2Message", "Hello topic 2");
		topic2Message3.setTopic(topic2);
		topic2Message4 = new Message("4", "topic2Message", "Hello topic 2");
		topic2Message4.setTopic(topic2);
	}

	@Test
	public void getAllMessagesTest() throws Exception {
		List<Message> messages = Arrays.asList(topic1Message1, topic1Message2);
		given(messageService.getAllMessages(topic1Id)).willReturn(messages);

		mvc.perform(get("/topics/1/messages").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(topic1Message1.getId())))
				.andExpect(jsonPath("$[0].name", is(topic1Message1.getName())))
				.andExpect(jsonPath("$[0].description", is(topic1Message1.getDescription())))
				.andExpect(jsonPath("$[1].id", is(topic1Message2.getId())))
				.andExpect(jsonPath("$[1].name", is(topic1Message2.getName())))
				.andExpect(jsonPath("$[1].description", is(topic1Message2.getDescription()))).andDo(print());
	}

	@Test
	public void getMessageTest() throws Exception {

		given(messageService.getMessage(topic1Message1.getId())).willReturn(Optional.of(topic1Message1));

		mvc.perform(get("/topics/1/message/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(topic1Message1.getId())))
				.andExpect(jsonPath("$.name", is(topic1Message1.getName())))
				.andExpect(jsonPath("$.description", is(topic1Message1.getDescription()))).andDo(print());

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void addMessageTest() throws Exception {
		String newMessage = mapToJson(topic2Message4);

		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(messageService).addMessage(topic2Message4);

		mvc.perform(MockMvcRequestBuilders.post("/topics/2/message").content(newMessage)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void updateMessageTest() throws Exception {
		String newMessage = mapToJson(topic2Message4);

		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(messageService).updateMessage(topic2Message4);

		mvc.perform(MockMvcRequestBuilders.put("/topics/2/message").content(newMessage)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void deleteMessageTest() throws Exception {
		String newMessage = mapToJson(topic2Message3);

		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(messageService).deleteMessage(topic2Message3.getId());

		mvc.perform(MockMvcRequestBuilders.delete("/messages/3").content(newMessage).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

}
