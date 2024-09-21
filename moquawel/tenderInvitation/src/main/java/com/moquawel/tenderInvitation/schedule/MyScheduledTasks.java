package com.moquawel.tenderInvitation.schedule;

import com.moquawel.tenderInvitation.service.TenderInvitationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyScheduledTasks {

    private final TenderInvitationService tenderInvitationService;

    @Scheduled(cron = "0 0 8-20 * * ?")
    public void tenderInvitationTask() {
        try {
            tenderInvitationService.getOffersFromTuneps();
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}