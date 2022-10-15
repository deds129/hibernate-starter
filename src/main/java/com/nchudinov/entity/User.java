package com.nchudinov.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.nchudinov.util.StringUtils.SPACE;

@NamedQuery(name = "findUserByName", query = "select u from User u " +
        "left join u.company c " +
        "where u.personalInfo.firstname = :firstname and c.name = :companyName " +
        "order by u.personalInfo.lastname desc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "profile", "userChats", "payments"})
@Builder
@Entity
@Table(name = "users", schema = "public")
@TypeDef(name = "nchudinov", typeClass = JsonBinaryType.class)
public class User implements Comparable<User>, BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
	@AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
	@AttributeOverrides({
			@AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
	})
	private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    @Type(type = "nchudinov")
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // company_id
    private Company company;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "receiver")
    private List<Payment> payments = new ArrayList<>();
	
	public User(Long id, PersonalInfo personalInfo, String username, Role role, Company company, Profile profile, List<UserChat> userChats) {
		this.id = id;
		this.personalInfo = personalInfo;
		this.username = username;
		this.role = role;
		this.company = company;
		this.profile = profile;
		this.userChats = userChats;
	}

	@Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    public String fullName() {
        return getPersonalInfo().getFirstname() + SPACE + getPersonalInfo().getLastname();
    }
}
