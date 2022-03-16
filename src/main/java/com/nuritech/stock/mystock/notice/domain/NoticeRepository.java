package com.nuritech.stock.mystock.notice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    @Query("SELECT p FROM Notice p ORDER BY p.id ASC")
    List<Notice> findAllDesc();

    @Query("SELECT m FROM Notice m "
            + " WHERE "
            + "     (?1 is null or m.id like concat('%', ?1, '%')) "
            + " and (?2 is null or upper(m.title) like concat('%', ?2, '%')) "
            + " and (?3 is null or upper(m.contents) like concat('%', ?3, '%')) "
            + " and (?4 is null or upper(m.contentsType) like concat('%', ?4, '%')) "
            + " and (?6 is null or upper(m.createdDate) between ?5 and ?6 ) ")
    Page<Notice> searchNotice(String id,
                              String title,
                              String contents,
                              String contentsType,
                              Date startDt,
                              Date endDt,
                              final Pageable pageable);

}

