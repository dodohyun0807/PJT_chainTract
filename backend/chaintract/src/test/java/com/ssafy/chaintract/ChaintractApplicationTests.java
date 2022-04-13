package com.ssafy.chaintract;

import com.ssafy.chaintract.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
class ChaintractApplicationTests {

	@Autowired EntityManager em;

	@Test
	@Transactional
	void contextLoads() {

		// given
		User user = new User();
		user.setName("KGS");
		user.setSocialId("1");
		em.persist(user);

		// when
		User findUser = em.find(User.class, 1L);

		// then
		Assertions.assertSame(findUser.getName(), "KGS");

	}

}
