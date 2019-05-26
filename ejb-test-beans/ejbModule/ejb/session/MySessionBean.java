package ejb.session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ejb.entities.DataService;
import ejb.entities.Message;
import ejb.entities.MessageLight;

/**
 * Session Bean implementation class SessionBean
 * @remote - the bean is going to be accessed from a remote client: 
 */
@Stateless
@Remote(MySessionBeanInterface.class)
public class MySessionBean {

	private Date last = new Date(); 

	@Resource(lookup = "java:/jmx/myQueue")
	Queue queue;
	
	@Resource(lookup = "java:/jmx/myTopic")
	Topic topic;

	@Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
	ConnectionFactory connectionFactory;

	@EJB
	DataService service;
	
	// java:jboss/mail/Default
	// java:jboss/mail/MyOtherMail
	// @Resource(lookup = "java:jboss/mail/MyOtherMail")
	// javax.mail.Session mailSession;
	
    /**
     * Default constructor. 
     */
    public MySessionBean() {
        // TODO Auto-generated constructor stub
    }

    public String getData() {
    	return "Last item at: " + new SimpleDateFormat("HH:mm:ss").format(last);
    }

    public List<MessageLight> getDataList() {
    	List<Message> list = service.getMessages();
    	List<MessageLight> result = new ArrayList<MessageLight>();
    	
    	for (Message m : list) {
    		result.add(new MessageLight(m.getId(), m.getContent(), m.getTime(), m.getSender()));
    	}
    	
    	return result;
    }
    
    @Schedule(minute = "*/5", hour = "*", persistent = false)
    public void setTime() {
    	last = new Date();
	System.out.println("-----------set time--------" + last);
    }
    
    @Schedule(minute = "*/4", hour = "*", persistent = false)
    public void sendMessages() {
		try {
			System.out.println("-----------send messages--------" + Thread.currentThread().getName());
			
			Connection connection = connectionFactory.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(topic);
			
			connection.start();
			
			TextMessage message = session.createTextMessage("Scheduled message at: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			producer.send(message);
			
			// not inside app server
			//connection.stop();
			session.close();

		} catch (JMSException e) {
			System.out.println("Send email error: " + e.getMessage());
		}
    }
    
    @Schedule(minute = "*/6", hour = "*", persistent = false)
    public void sendMails() {
		try {
			//String mailndi = "java:jboss/mail/Default";
			
			//System.out.println("-----------send mails--------" + Thread.currentThread().getName());
			
			// MimeMessage message = new MimeMessage(mailSession);
			// message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("pavel.petr@gist.cz"));
			// message.setFrom();  
			// message.setSubject("Mail subject goes here"); 
			// message.setSentDate(new Date()); 
			// message.setText("Sending mail from bean");
			// Transport.send(message);
			
		} catch (Exception e) {
			System.out.println("Send email error: " + e.getMessage());
		}
    }
    
}
