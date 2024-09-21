package com.moquawel.tenderInvitation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TenderInvitationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenderInvitationApplication.class, args);
	}

}
