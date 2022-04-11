package com.board.web;

import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest extends TestCase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void loading_main_page() {

        ResponseEntity<String> forEntity = restTemplate.getForEntity("/", String.class);

        String forObject = restTemplate.getForObject("/", String.class);
        Assertions.assertThat(forObject).contains("Hello I am jongseo");
        Assertions.assertThat(forEntity.getBody()).isEqualTo("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<!--    http-equiv=\"Content-Type\" content=\"text/html\"-->\n" +
                "    <meta  charset=\"UTF-8\">\n" +
                "    <title>스프링 부트 웹 서비스</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Hello I am jongseo</h1>\n" +
                "</body>\n" +
                "</html>");
    }

}