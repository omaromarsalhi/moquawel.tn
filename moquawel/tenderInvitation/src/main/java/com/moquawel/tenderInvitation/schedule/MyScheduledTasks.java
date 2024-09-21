package com.moquawel.tenderInvitation.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyScheduledTasks {

    @Scheduled(cron = "0 * 8-20 * * ?")
    public void task1() {
        log.info("Task 1 executed.");
    }

}