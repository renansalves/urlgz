package br.urlgz.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlgzApplicationTests {

	@Test
	void contextLoads() {
	}
  @Test
  void dumyTest(){
    Boolean value = true;
    assertEquals(true, value);
  }

}
