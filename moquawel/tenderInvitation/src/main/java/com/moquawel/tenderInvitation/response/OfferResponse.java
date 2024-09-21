package com.moquawel.tenderInvitation.response;

import java.util.List;
import java.util.Map;

public record OfferResponse(
    int code,
    Map<String,Object> payload
) {
}
