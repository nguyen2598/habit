package com.habittracke.repository;

import com.habittracke.entity.sql.SendSms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SendSmsRepository extends JpaRepository<SendSms, Long> {
    List<SendSms> findByStatus(Long status);
}
