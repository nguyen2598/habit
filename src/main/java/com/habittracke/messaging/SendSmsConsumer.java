package com.habittracke.messaging;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.habittracke.entity.sql.SendSms;
import com.habittracke.repository.SendSmsRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendSmsConsumer {
    private static final Logger log = LoggerFactory.getLogger(SendSmsConsumer.class);
    private final SendSmsRepository sendSmsRepository;
    @KafkaListener(topics = "send-sms-topic", groupId = "send-sms-group")
    public void consumeMessage(String data) {
        System.out.println("SMS MESSAGE: xxx");
        try {
            ObjectMapper mapper = new ObjectMapper();
            SendSms message = mapper.readValue(data, SendSms.class);
            // Gọi hàm gửi tin nhắn
            try {
                System.out.println("t da gui tin nhan cho sdt: "+ message.getPhone() +", "+message.getContent());

                // Nếu gửi thành công -> cập nhật status = 2 (đã gửi)
                message.setStatus(2);
                message.setError(null);
            }catch (Exception ex){
                // Nếu lỗi -> cập nhật status = 0 (chờ gửi lại), và ghi log lỗi
                message.setStatus(0);
                message.setError("Send fail: " + ex.getMessage());
            }
            sendSmsRepository.save(message);

        } catch (Exception ex) {
            System.out.println("buggg");
        }

        // Cập nhật DB

    }

}
