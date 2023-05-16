package com.patryk.app.rest;

import com.patryk.app.rest.Model.User;
import com.patryk.app.rest.Model.UserRegisterFormData;
import com.patryk.app.rest.Repository.UsersRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest

class RestApplicationTests {

	@Autowired
	UsersRepository repo;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void createNewUserTest() {

		User user = new User();
		user.setEmail("patkoc11@interia.pl");
		user.setName("Pako2425");
		user.setPassword("gitara321");

		User savedUser = repo.save(user);
		User foundUser = entityManager.find(User.class, savedUser.getId());
		Assertions.assertEquals(savedUser.getEmail(), foundUser.getEmail());
	}

	@Test
	void registerUserTest() {
		User user = new User();
		user.setEmail("patkoc11@interia.pl");
		user.setName("Pako2425");
		user.setPassword("gitara321");

		UserRegisterFormData userRegisterFormData = new UserRegisterFormData();
		userRegisterFormData.setEmail("makaron@gmail.com");
		userRegisterFormData.setName("Adam6758");
		userRegisterFormData.setPassword("aaa");
		userRegisterFormData.setRepeatPassword("aaa");

		mockMvc.perform()
	}
}
