package ejb.session;

import java.util.List;

import ejb.entities.Message;
import ejb.entities.MessageLight;

public interface MySessionBeanInterface {
	public String getData();
	public List<MessageLight> getDataList();
	public void sendMessages();
}
