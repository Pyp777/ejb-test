package ejb.messages;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import ejb.entities.DataService;

/**
 * Message-Driven Bean implementation class for: MyTopicMessageBean
 */
@MessageDriven(activationConfig = { 
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "myTopic"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class MyTopicMessageBean1 implements MessageListener {

	@EJB
	DataService service;
	
	/**
	 * Default constructor.
	 */
	public MyTopicMessageBean1() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		if (message == null) {
			System.out.println("MessageBean1 - Message is null");
			return;
		}
		if (message instanceof TextMessage) {
			TextMessage text = (TextMessage) message;
			try {
				System.out.println("MessageBean1 - Text message: " + text.getText());
				// save message
				service.saveMessage(text.getText(), getClass().getSimpleName());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
