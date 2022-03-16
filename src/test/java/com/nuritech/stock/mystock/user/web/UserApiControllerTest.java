package com.nuritech.stock.mystock.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.user.dto.SignUpRequestDto;
import com.nuritech.stock.mystock.user.dto.SignUpResponseDto;
import com.nuritech.stock.mystock.web.StockApiController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    @Autowired
    UserApiController userApiController;

    @Autowired
    private ObjectMapper mapper; // 객체를 json 형식으로 변경 시 사용

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String _BASE_URL = "http://localhost:";

    @Test
    public void signUp_test() throws Exception {

        SignUpRequestDto dto = new SignUpRequestDto("mintaeho75@gmail.com", "test123", "USER");
        SignUpResponseDto responseDto = userApiController.signUp(dto);
        System.out.println(responseDto.getResponse());
    }
}