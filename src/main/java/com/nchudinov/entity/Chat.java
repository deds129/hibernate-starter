package com.nchudinov.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name") // исключаем зацикливание
@ToString(exclude = "chatUsers") // исключаем зацикливание
@Builder
@Entity
@Table(name = "chat", schema = "public")
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Builder.Default // для создания объекта с помощью Builder
	@OneToMany(mappedBy = "chat") //таблица сущности read-only
	private Set<UsersChat> chatUsers = new HashSet<>();
}
