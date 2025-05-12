package com.habittracke.entity.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name = "send_sms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSms implements Serializable {
    @Id
    private String id;
    private String content;
    private  int status;
    private String phone;
    private String error;
}
