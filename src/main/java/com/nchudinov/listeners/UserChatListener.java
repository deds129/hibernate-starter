package com.nchudinov.listeners;

import com.nchudinov.entity.UserChat;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

public class UserChatListener {
	
	//works only for transactions via hibernate
	@PostPersist
	public void postPersist(UserChat userChat) {
		var chat = userChat.getChat();
		chat.setCount(chat.getCount() + 1);
	}

	@PostRemove
	public void postRemove(UserChat userChat) {
		var chat = userChat.getChat();
		chat.setCount(chat.getCount() - 1);
	}
}
