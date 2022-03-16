package com.nuritech.stock.mystock.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.PortfolioRepository;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.service.PortfolioService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortfolioApiControllerTest {

    @Autowired
    PortfolioApiController portfolioApiController;

    @Autowired
    private ObjectMapper mapper; // 객체를 json 형식으로 변경 시 사용

    private final String EMAIL = "mintaeho75@gmail.com";
    private final String NAME1 = "테스트포트폴리오1";
    private final String NAME2 = "테스트포트폴리오2";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String _BASE_URL = "http://localhost:";

    @BeforeEach
    void setUp() {
        // dto 준비

        PortfolioSaveRequestDto requestDto1 = PortfolioSaveRequestDto.builder()
                .email(EMAIL)
                .name(NAME1)
                .build();

        PortfolioSaveRequestDto requestDto2 = PortfolioSaveRequestDto.builder()
                .email(EMAIL)
                .name(NAME2)
                .build();

        // 등록
        Long result = portfolioApiController.save(requestDto1);
        Long result2 = portfolioApiController.save(requestDto2);

        this.printData("등록");
    }

    @Test
    public void portfolio_save_get() throws Exception {

        // 결과 조회
        List<PortfolioResponseDto> responseDtos = portfolioApiController.findByEmail(EMAIL);
        Long result = responseDtos.get(0).getPortfolioId();

        assertThat(result).isEqualTo(1L);

        PortfolioResponseDto dto = portfolioApiController.findById(result);
        System.out.println(">>>> findById::id=" + dto.getPortfolioId() +
                ", email=" + dto.getEmail() +
                ", name=" + dto.getName());

        assertThat(dto.getEmail()).isEqualTo(EMAIL);
        assertThat(dto.getName()).isEqualTo(NAME1);

        dto = portfolioApiController.findByEmailAndName(EMAIL, NAME1);
        System.out.println(">>>>> findByEmailAndName::id=" + dto.getPortfolioId() +
                ", email=" + dto.getEmail() +
                ", name=" + dto.getName());

        assertThat(dto.getPortfolioId()).isEqualTo(1L);
    }

    /**
     * TestRestTemplate 을 이용한 Controller 테스트
     * @throws Exception
     */
    @Test
    public void portfolio_save_get2() throws Exception {

        String _mappingPath  = "/api/v1/portfolio";
        String name3 = "테스트포트폴리오3";
        PortfolioSaveRequestDto requestDto = PortfolioSaveRequestDto.builder()
                .email(EMAIL)
                .name(name3)
                .build();

        String url = _BASE_URL + port + _mappingPath;

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // 결과 조회
        this.printData("TestRestTemplate이용");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

    }

    @Test
    public void portfolio_modify_get() throws Exception {

        // dto 준비
        String modifyName = "수정_테스트포트폴리오";

        PortfolioResponseDto responseDto = portfolioApiController.findByEmailAndName(EMAIL, NAME1);

        PortfolioModifyRequestDto modifyRequestDto = PortfolioModifyRequestDto.builder()
                .portfolioId(responseDto.getPortfolioId())
                .email(responseDto.getEmail())
                .name(modifyName)
                .build();

        Long result = portfolioApiController.modify(modifyRequestDto);
        // 결과 조회
        this.printData("수정 후");

        assertThat(result).isEqualTo(1L);

        PortfolioResponseDto dto = portfolioApiController.findById(result);
        System.out.println(">>>> findById:: id=" + dto.getPortfolioId() +
                ", email=" + dto.getEmail() +
                ", name=" + dto.getName());

        assertThat(dto.getEmail()).isEqualTo(EMAIL);
        assertThat(dto.getName()).isEqualTo(modifyName);

        dto = portfolioApiController.findByEmailAndName(EMAIL, modifyName);
        System.out.println(">>>>> findByEmailAndName:: id=" + dto.getPortfolioId() +
                ", email=" + dto.getEmail() +
                ", name=" + dto.getName());

        assertThat(dto.getPortfolioId()).isEqualTo(1L);
    }

    @Test
    public void portfolio_remove_get() throws Exception {

        // 1번 삭제
        portfolioApiController.remove(1L);

        // 결과 조회
        this.printData("삭제 후");

        List<PortfolioResponseDto> dtos = portfolioApiController.findByEmail(EMAIL);
        Long result = dtos.get(0).getPortfolioId();

        assertThat(result).isEqualTo(2L);

    }

    /**
     * TestRestTemplate 을 이용한 Controller 테스트
     * @throws Exception
     */
    @Test
    public void findByEmail_test() throws Exception {

        /*
        String _mappingPath  = "/api/v1/portfolio/byEmail?email=mintaeho75@gmail.com";
        String url = _BASE_URL + port + _mappingPath;

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("email", "mintaeho75@gmail.com");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

        System.out.println(">>> responseEntity="+responseEntity.getBody());
        */

        String _mappingPath  = "/api/v1/portfolio/byEmail?email=mintaeho75@gmail.com";
        String url = _BASE_URL + port + _mappingPath;

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("email", "mintaeho75@gmail.com");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        System.out.println(">>> responseEntity="+responseEntity.getBody());


    }

    private void printData(String mode) {
        List<PortfolioResponseDto> responseDtos = portfolioApiController.findByEmail(EMAIL);
        for(PortfolioResponseDto dto : responseDtos) {
            System.out.println(">>> ["+mode+"] id=" + dto.getPortfolioId() +
                    ", email=" + dto.getEmail() +
                    ", name=" + dto.getName());
        }
    }

}
