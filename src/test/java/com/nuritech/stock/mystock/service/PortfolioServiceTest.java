package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PortfolioServiceTest {

    private final String DATA_EMAIL = "mintaeho75@gmail.com";
    private final String DATA_NAME = "첫번째포트폴리오";
    private final String DATA_NAME2 = "두번째포트폴리오";

    @Autowired
    PortfolioService service;

    @Test
    public void portfolio_Save_List_without_PortfolioStock() {

        // 등록 요청 객체 생성
        PortfolioSaveRequestDto requestDto = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME)
                .build();

        // 저장
        Long result = service.save(requestDto);
        System.out.println(">>> result="+result);

        // 저장 목록 조회 및 출력
        List<PortfolioResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDtos) {
            System.out.println(">>> dto.getEmail()="+dto.getEmail()+
                    ", dto.getName()="+dto.getName());
        }

        // 저장된 객체의 id가 1이면 통과
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void portfolio_Modify_List_without_PortfolioStock() {

        // 저장 요청 객체 생성
        PortfolioSaveRequestDto requestDto = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME)
                .build();

        // 저장
        Long result = service.save(requestDto);
        System.out.println(">>> result="+result);

        //  저장 목록 조회 및 출력
        List<PortfolioResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDtos) {
            System.out.println(">>> dto.getEmail()="+dto.getEmail()+
                    ", dto.getName()="+dto.getName());
        }

        // id값으로 저장된 객체 조회
        PortfolioResponseDto portfolioResponseDto1 = service.findById(result);

        // 수정 요청 객체 생성 및 수정대상 항목에 수정된 값 반영
        // 포트폴리오 명 변경
        String modifyName = "첫번째포트폴리오_수정";
        PortfolioModifyRequestDto modifyRequestDto = PortfolioModifyRequestDto.builder()
                .portfolioId(result)
                .email(portfolioResponseDto1.getEmail())
                .name(modifyName)
                .build();

        // 수정된 객체로 저장
        Long result2 = service.modify(modifyRequestDto);
        System.out.println(">>> result2="+result2);

        //  저장 목록 조회 및 출력
        responseDtos = service.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDtos) {
            System.out.println(">>> dto.getEmail()="+dto.getEmail()+
                    ", dto.getName()="+dto.getName());
        }

        // 첫번째 저장된 객체의 id가 1이면 통과
        assertThat(result).isEqualTo(1);

        // 첫번째 저장된 객체의 name이 수정 된 name과 동일하면 통과
        assertThat(responseDtos.get(0).getName()).isEqualTo(modifyName);

        // 첫번째 저장된 객체의 email이 수정 전 email과 동일하면 통과
        assertThat(responseDtos.get(0).getEmail()).isEqualTo(DATA_EMAIL);
    }

    @Test
    @Transactional
    public void portfolio_Remove_List_without_PortfolioStock() {

        // 저장 요청 객체 2개 생성
        // 등록 요청 객체 생성
        PortfolioSaveRequestDto requestDto = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME)
                .build();

        PortfolioSaveRequestDto requestDto2 = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME2)
                .build();

        // 객체 2개 저장
        Long result = service.save(requestDto);
        System.out.println(">>> result="+result);

        Long result2 = service.save(requestDto2);
        System.out.println(">>> result2="+result2);

        // 저장된 객체 목록 조회
        List<PortfolioResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDtos) {
            System.out.println(">>> 삭제 전 dto.getEmail()="+dto.getEmail()+
                    ", dto.getName()="+dto.getName());
        }

        // 저장된 Soock객체중 1번 객체 삭제
        // remove
        service.remove(result);

        // 저장된 Stock 객체 목록 조회
        List<PortfolioResponseDto> responseDtos2 = service.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDtos2) {
            System.out.println(">>> 삭제 후 dto.getEmail()="+dto.getEmail()+
                    ", dto.getName()="+dto.getName());
        }

        // 저장된 객체가 1개이면 통과
        assertThat(responseDtos2.size()).isEqualTo(1);

        // 저장된 객체의 name이 2번 name과 동일하면 통과
        assertThat(responseDtos2.get(0).getName()).isEqualTo(DATA_NAME2);
    }



}
