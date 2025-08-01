package ru.netology.authorize;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private static final GenericContainer<?> myAppFirst = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static final GenericContainer<?> myAppSecond = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        myAppFirst.start();
        myAppSecond.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + myAppFirst.getMappedPort(8080), String.class);
        System.out.println(forEntity.getBody());
    }

    @Test
    void testFirstContainer() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + myAppFirst.getMappedPort(8080), String.class);

        assertEquals("8080", forEntity.getBody());
    }

    @Test
    void testSecondContainer() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + myAppSecond.getMappedPort(8081), String.class);

        assertEquals("8081", forEntity.getBody());
    }

}
