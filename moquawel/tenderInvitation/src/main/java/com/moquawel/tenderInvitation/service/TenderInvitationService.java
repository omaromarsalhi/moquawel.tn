package com.moquawel.tenderInvitation.service;

import com.moquawel.tenderInvitation.dto.CriteriaSearch;
import com.moquawel.tenderInvitation.feignclient.RetrieveClient;
import com.moquawel.tenderInvitation.offer.OfferRepository;
import com.moquawel.tenderInvitation.response.OfferResponse;
import com.moquawel.tenderInvitation.response.PayloadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
public class TenderInvitationService {

    private final RetrieveClient retrieveClient;
    private final OfferRepository offerRepository;

    public OfferResponse getOffers() {
        Map<String, Object> formData = new HashMap<>();
        var currentDateTime = this.formatNowDate();
        formData.put("publicYn", 'Y');
        formData.put("publicDt", currentDateTime);

        List<Map<String, String>> searchPayload = generateSearchCriteria(formData);

        Map<String, Object> payload = new HashMap<>();
        payload.put("dataSearch", searchPayload);

        ResponseEntity<OfferResponse> response = retrieveClient.retrieveClient(payload);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            if (response.getBody().payload().total() != 0) {

                var newOfferList = response.getBody()
                        .payload()
                        .data()
                        .stream()
                        .filter(data -> compareDateTime(currentDateTime, data.getPublicDt()))
                        .toList();

                offerRepository.saveAll(newOfferList);

                return OfferResponse
                        .builder()
                        .code(200)
                        .payload(
                                PayloadResponse
                                        .builder()
                                        .total(newOfferList.size())
                                        .data(newOfferList)
                                        .build()
                        )
                        .build();
            }
            return null;
        } else
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private List<Map<String, String>> generateSearchCriteria(Map<String, Object> formData) {
        List<Map<String, String>> options = new ArrayList<>();

        if (formData.containsKey("publicDt")) {
            String formattedPublicDt = (String) formData.get("publicDt");
            options.add(new CriteriaSearch("publicDt", formattedPublicDt, ">=").toMap());
        }

        if (formData.containsKey("publicDt_end")) {
            String formattedPublicDtEnd = (String) formData.get("publicDt_end");
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


    private boolean compareDateTime(String datetime1, String datetime2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(datetime1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(datetime2.substring(0, datetime2.indexOf('.')), formatter);
        return dateTime1.isBefore(dateTime2);
    }

    private String formatNowDate() {
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = date.atTime(LocalDateTime.now().getHour(), 0, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
