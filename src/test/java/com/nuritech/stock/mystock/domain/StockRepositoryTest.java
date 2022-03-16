package com.nuritech.stock.mystock.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StockRepositoryTest {

    @Autowired
    StockRepository repository;

    @Test
    @Transactional
    public void Stock저장_불러오기() {
        Stock addStock1 = Stock.builder().ticker("TEST1").build();
        Stock addStock2 = Stock.builder().ticker("TEST2").build();
        Stock addStock3 = Stock.builder().ticker("TEST3").build();
        repository.save(addStock1);
        repository.save(addStock2);
        repository.save(addStock3);

        List<Stock> list = repository.findAll();
        for(Stock obj : list) {
            System.out.println(">>> 저장::id="+obj.getId()+" ticker="+obj.getTicker()+" name="+obj.getStockName());
        }
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void Stock수정_불러오기() {
        this.Stock저장_불러오기();
        String modifyName = "수정TEST1주식명";
        Long key = new Long(1);
        Optional<Stock> optionalObj = repository.findById(key);

        if ( optionalObj.isPresent() ) {
            Stock modifyObj = optionalObj.get();
            modifyObj.update(modifyObj.getTicker(),
                    modifyName,
                    null, null, null, null, null,
                    null,
                    null, null, null, null,
                    null, null, null, null,
                    null, null);
        }
        else {
            System.out.println("optionalPortfolio 객체 없음");
        }

        List<Stock> list = repository.findAll();
        for(Stock obj : list) {
            System.out.println(">>> 수정후::id="+obj.getId()+" ticker="+obj.getTicker()+" name="+obj.getStockName());
        }

        assertThat(list.get(0).getStockName()).isEqualTo(modifyName);

    }

    @Test
    @Transactional
    public void Stock삭제_불러오기() {
        this.Stock저장_불러오기();

        Long key = new Long(1);
        Stock target = repository.findById(key)
                .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));

        repository.delete(target);

        List<Stock> list = repository.findAll();
        for(Stock obj : list) {
            System.out.println(">>> 삭제후::id="+obj.getId()+" ticker="+obj.getTicker()+" name="+obj.getStockName());
        }

        assertThat(list.size()).isEqualTo(2);

    }
}