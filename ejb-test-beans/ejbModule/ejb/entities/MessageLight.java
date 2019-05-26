package ejb.entities;

import java.io.Serializable;
import java.util.Date;

public class MessageLight implements Serializable {
	private Long id;
	private String content;
	private Date time;
	private String sender;

	public MessageLight(Long id, String content, Date time, String sender) {
		this.id = id;
		this.content = content;
		this.time = time;
		this.sender = sender;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Date getTime() {
		return time;
	}

	public String getSender() {
		return sender;
	}
}
