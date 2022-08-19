package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"createdAt", "createdBy" })
@EqualsAndHashCode(of = {"createdAt", "createdBy" })
@Builder
@Entity
@Table(name = "users_chat")
public class UsersChat extends AuditableEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	private Chat chat;
	
	public  void setUser(User user) {
		this.user = user;
		this.user.getUsersChats().add(this);
	}

	public  void setChat(Chat chat) {
		this.chat = chat;
		this.chat.getChatUsers().add(this);
	}
}