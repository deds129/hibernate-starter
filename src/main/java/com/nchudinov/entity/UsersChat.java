package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"createdAt", "createdBy" })
@EqualsAndHashCode(of = {"createdAt", "createdBy" })
@Builder
@Entity
@Table(name = "users_chat")
public class UsersChat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "created_by", nullable = false, length = 64)
	private String createdBy;
	
	public  void setUser(User user) {
		this.user = user;
		this.user.getUsersChats().add(this);
	}

	public  void setChat(Chat chat) {
		this.chat = chat;
		this.chat.getChatUsers().add(this);
	}
}