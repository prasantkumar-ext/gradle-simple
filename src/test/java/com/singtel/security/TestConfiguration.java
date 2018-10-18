package com.singtel.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.singtel.security.SingtelSecurityApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SingtelSecurityApplication.class)
public class TestConfiguration {

	@Test
	public void contextLoads() {
		// Automatically load the configuration and run the test
	}
}
