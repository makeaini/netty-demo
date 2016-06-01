package com.pppcar.netty.message;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 8272631511915108214L;
	private String id;
	private String context;
	//1，心跳消息，2，普通消息，
	private Integer messageType;
	private String to;
	

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Message(String id, String context, Integer messageType) {
		super();
		this.id = id;
		this.context = context;
		this.messageType = messageType;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public String toString() {
		return "Message [id=" + id + ", context=" + context + ", messageType="
				+ messageType + ", to=" + to + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (messageType == null) {
			if (other.messageType != null)
				return false;
		} else if (!messageType.equals(other.messageType))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
	
	
}
