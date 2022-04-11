package com.board.web;

import com.board.domain.posts.Posts;
import com.board.domain.posts.PostsRepository;
import com.board.web.dto.PostSaveRequestDto;
import com.board.web.dto.PostsUpdateRequestDto;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)

// JPA 릉 이용할땐  TestRestTemplate 를 사용한다,
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest extends TestCase {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;


    @After
    public void tearDown() {
        postsRepository.deleteAll();
    }


    @Test
    public void save_posts() {
        String title = "test title";
        String content = "test content";

        PostSaveRequestDto dto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("jongseo")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";


        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);
    }


    @Test
    public void update_Posts() {
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder()
                .content(expectedContent)
                .title(expectedTitle)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" +updateId;


        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(postsUpdateRequestDto);


        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);


        List<Posts> all = postsRepository.findAll();

        Assertions.assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
    }


    @Test
    public void Enrole_BaseTimeEntity() {
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author").build());

        List<Posts> all = postsRepository.findAll();

        Posts posts = all.get(0);

        System.out.println("posts.getCreatedDate() = " + posts.getCreatedDate());

        System.out.println("posts.getModifiedDate() = " + posts.getModifiedDate());


        Assertions.assertThat(posts.getCreatedDate()).isAfter(now);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(now);
    }

}