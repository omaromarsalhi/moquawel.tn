package com.moquawel.tenderInvitation.request;


import java.time.LocalDateTime;

public record FilterRequest(
        LocalDateTime publicDt,
        LocalDateTime bdRecvEndDt,
        String category
) {
}
