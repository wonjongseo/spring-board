package com.board.web;

import com.board.web.dto.HelloResponseDto;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// 테스트 진행 시 Junit 에 내장된 실행자 외에 다른 실행자 실행 ( 여기서는 SpringRunner 라는 스프링 실행자)
// 즉 스프링과 제이유닛의  연결자

// 그래들 테스트 https://hsa8275.tistory.com/177
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest{

    @Autowired
    private MockMvc mvc;

    @Test
    public void test_lombok(){
        String name = "test";
        int amount = 100;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        Assertions.assertThat(dto.getAmount()).isEqualTo(amount);
        Assertions.assertThat(dto.getName()).isEqualTo(name);
    }

    @Test
    public void return_hello() throws Exception {
        String hello = "hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello"))
        ;

    }

    @Test
    public void return_hello_dto() throws Exception {
        String name = "test";
        int amount = 100;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name)))

                // .jsonPath $ 를 기준으로 필드명을 명시함 ,
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(amount)))

        ;
    }

}