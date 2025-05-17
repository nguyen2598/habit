package com.habittracke.job;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.habittracke.entity.sql.SendSms;
import com.habittracke.repository.SendSmsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SendSmsScheduler {

    private final SendSmsRepository sendSmsRepository;

    private final KafkaTemplate<String, SendSms> kafkaTemplate;

    public SendSmsScheduler(SendSmsRepository sendSmsRepository, KafkaTemplate<String, SendSms> kafkaTemplate) {
        this.sendSmsRepository = sendSmsRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Mỗi 10 giây thực hiện 1 lần
    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void scanAndSendToKafka() {
        System.out.println("dang quet");
        List<SendSms> messages = sendSmsRepository.findByStatus(0L); // Trạng thái 0 là chưa gửi
        System.out.println("huhu");
        for (SendSms msg : messages) {
            try {
                System.out.println("try");
                msg.setStatus(1); // Chuyển sang pending
                sendSmsRepository.save(msg); // Cập nhật để tránh bị lặp
                System.out.println("send");
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(msg);

                kafkaTemplate.send("send-sms-topic", msg); // Đẩy vào Kafka
                System.out.println("send thanh cong");
            } catch (Exception e) {
                System.out.println("send that bai1: "+ e.getMessage());
                msg.setStatus(0);
                msg.setError("Kafka error: " + e.getMessage());
                sendSmsRepository.save(msg);
                System.out.println("send that bai: "+ e.getMessage());
            }
        }
    }
}

