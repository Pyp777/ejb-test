package ejb.entities;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@javax.ejb.Stateless
@LocalBean
public class DataService {

	@PersistenceContext
	EntityManager entityManager;
	
	public void saveMessage(String content, String sender) {
		Message m = new Message(content, new Date(), sender);
		
		entityManager.persist(m);
		entityManager.flush();
	}
	
	public List<Message> getMessages() {
		List<Message> result = entityManager.createQuery("SELECT m FROM Message m").getResultList();
		
		return result;
	}
}
