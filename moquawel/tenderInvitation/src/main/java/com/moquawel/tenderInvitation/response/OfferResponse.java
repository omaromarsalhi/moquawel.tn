package com.moquawel.tenderInvitation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record OfferResponse(
    int code,
    PayloadResponse payload
) {
}
