package com.nuritech.stock.mystock.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PortfolioRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Test
    @Transactional
    public void Portfolio저장_불러오기() {
        Portfolio addPortfolio1 = Portfolio.builder()
                .email("mintaeho75@gmail.com")
                .name("테스트 포트폴리오1")
                .build();
        Portfolio addPortfolio2 = Portfolio.builder()
                .email("mintaeho75@gmail.com")
                .name("테스트 포트폴리오2")
                .build();
        Portfolio addPortfolio3 = Portfolio.builder()
                .email("mintaeho75@gmail.com")
                .name("테스트 포트폴리오3")
                .build();

        // BaseTimeEntity 테스트
        LocalDateTime now = LocalDateTime.of(2020, 2, 1, 0, 0, 0);

        portfolioRepository.save(addPortfolio1);
        portfolioRepository.save(addPortfolio2);
        portfolioRepository.save(addPortfolio3);

        List<Portfolio> list = portfolioRepository.findAll();
        for(Portfolio obj : list) {
            StringBuffer sb = new StringBuffer()
                    .append(">>> Portfolio ")
                    .append("id=").append(obj.getId())
                    .append(", name=").append(obj.getName())
                    .append(", getCreatedDate=").append(obj.getCreatedDate())
                    .append(", getModifiedDate=").append(obj.getModifiedDate())
                    ;
            System.out.println(sb.toString());
        }
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0).getCreatedDate()).isAfter(now);
        assertThat(list.get(0).getModifiedDate()).isAfter(now);
    }

    @Test
    @Transactional
    public void Portfolio수정_불러오기() {
        this.Portfolio저장_불러오기();
        String modifyName = "수정 포트폴리오1";
        Long key = new Long(1);
        Optional<Portfolio> optionalObj = portfolioRepository.findById(key);

        if ( optionalObj.isPresent() ) {
            Portfolio modifyObj = optionalObj.get();
            modifyObj.update(null, modifyObj.getEmail(), modifyName);
        }
        else {
            System.out.println("optionalPortfolio 객체 없음");
        }

        List<Portfolio> list = portfolioRepository.findAll();
        for(Portfolio obj : list) {
            System.out.println(">>> Portfolio수정_불러오기 Portfolio id="+obj.getId()+" name="+obj.getName());
        }

        assertThat(list.get(0).getName()).isEqualTo(modifyName);

    }

    @Test
    @Transactional
    public void Portfolio삭제_불러오기() {
        this.Portfolio저장_불러오기();

        Long key = new Long(1);
        Portfolio target = portfolioRepository.findById(key)
                .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));

        portfolioRepository.delete(target);

        List<Portfolio> list = portfolioRepository.findAll();
        for(Portfolio obj : list) {
            System.out.println(">>> Portfolio삭제_불러오기 Portfolio id="+obj.getId()+" name="+obj.getName());
        }

        assertThat(list.size()).isGreaterThan(1);

    }

}