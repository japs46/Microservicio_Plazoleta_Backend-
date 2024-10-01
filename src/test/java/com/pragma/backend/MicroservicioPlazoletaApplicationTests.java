package com.pragma.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
class MicroservicioPlazoletaApplicationTests {

	@Test
	void contextLoads() {
	}

}
