package com.moquawel.tenderInvitation.service;

import com.moquawel.tenderInvitation.dto.CriteriaSearch;
import com.moquawel.tenderInvitation.feignclient.SearchClient;
import com.moquawel.tenderInvitation.response.OfferResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class TenderInvitationService {

    private final SearchClient searchClient;

    public OfferResponse getOffers() {
        Map<String, Object> formData = new HashMap<>();
        formData.put("publicYn", 'Y');
        formData.put("publicDt", this.formatDate(LocalDate.now().toString()));

        List<Map<String, String>> searchPayload = generateSearchCriteria(formData);

        Map<String, Object> payload = new HashMap<>();
        payload.put("dataSearch", searchPayload);

        ResponseEntity<OfferResponse> response = searchClient.searchOffers(payload);

        if (response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private List<Map<String, String>> generateSearchCriteria(Map<String, Object> formData) {
        List<Map<String, String>> options = new ArrayList<>();

        if (formData.containsKey("publicDt")) {
            String formattedPublicDt = formatDate((String) formData.get("publicDt"));
            options.add(new CriteriaSearch("publicDt", formattedPublicDt, ">=").toMap());
        }

        if (formData.containsKey("publicDt_end")) {
            String formattedPublicDtEnd = formatDate((String) formData.get("publicDt_end"));
            options.add(new CriteriaSearch("publicDt", formattedPublicDtEnd, "<=").toMap());
        }

        if (formData.containsKey("publicYn")) {
            options.add(new CriteriaSearch("publicYn", formData.get("publicYn").toString(), "=").toMap());
        }

        if (formData.containsKey("bidNo")) {
            options.add(new CriteriaSearch("bidNo", formData.get("bidNo").toString().trim(), "upper_like").toMap());
        }


        return options;
    }

    private String formatDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
