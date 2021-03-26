package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
	private final ResolutionRepository resolutions;

	private final UserRepository users;

	public ResolutionInitializer(ResolutionRepository resolutions, UserRepository users) {
		this.resolutions = resolutions;
		this.users = users;
	}

	@Override
	public void afterSingletonsInstantiated() {
		User user = new User("user",
				"{bcrypt}$2a$10$M/UszZrlEu7OjsLwVM15iOjvFWYOIiwrdx7KHiQIBSkrbeXdMotq2");
		user.grantAuthority("resolution:read");
		user.grantAuthority("resolution:write");
		user.grantAuthority("user:read");
		user.setFullName("User Userson");
		this.users.save(user);

		User hasRead = new User();
		hasRead.setUsername("hasread");
		hasRead.setPassword("{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		hasRead.grantAuthority("resolution:read");
		hasRead.grantAuthority("user:read");
		hasRead.setFullName("Has Read");
		hasRead = this.users.save(hasRead);

		User hasWrite = new User();
		hasWrite.setUsername("haswrite");
		hasWrite.setPassword("{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		hasWrite.grantAuthority("resolution:write");
		hasWrite.grantAuthority("user:read");
		hasWrite.setFullName("Has Write");
		hasWrite.addFriend(hasRead);
		hasWrite.setSubscription("premium");
		this.users.save(hasWrite);

		User admin = new User("admin","{bcrypt}$2a$10$bTu5ilpT4YILX8dOWM/05efJnoSlX4ElNnjhNopL9aPoRyUgvXAYa");
		admin.grantAuthority("ROLE_ADMIN");
		admin.grantAuthority("user:read");
		admin.setFullName("Admin Adminson");
		this.users.save(admin);

		this.resolutions.save(new Resolution("Read War and Peace", "user"));
		this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));
	}
}
