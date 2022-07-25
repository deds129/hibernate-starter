package com.nchudinov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
    @JoinColumn(name = "user_id")
	private User user;

	private String street;

	private String language;

	public void setUser(User user) {
		user.setProfile(this);
		this.user = user;
	}
}