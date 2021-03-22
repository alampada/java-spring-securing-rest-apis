package io.jzheaux.springsecurity.resolutions;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "authorities")
public class UserAuthority {

	UserAuthority() {
	}

	UserAuthority(User user, String authority) {
		this.authority = authority;
		this.user = user;
	}

	@Id
	@GeneratedValue
	UUID id;

	@Column
	String authority;

	@JoinColumn(name = "username", referencedColumnName = "username")
	@ManyToOne
	User user;

	UUID getId() {
		return id;
	}

	void setId(UUID id) {
		this.id = id;
	}

	String getAuthority() {
		return authority;
	}

	void setAuthority(String authority) {
		this.authority = authority;
	}

	User getUser() {
		return user;
	}

	void setUser(User user) {
		this.user = user;
	}
}
