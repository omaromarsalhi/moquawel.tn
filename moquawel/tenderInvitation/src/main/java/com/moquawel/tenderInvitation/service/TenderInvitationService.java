package com.moquawel.tenderInvitation.service;

import com.moquawel.tenderInvitation.dto.CriteriaSearch;
import com.moquawel.tenderInvitation.feignclient.*;
import com.moquawel.tenderInvitation.offer.Offer;
import com.moquawel.tenderInvitation.offer.OfferRepository;
import com.moquawel.tenderInvitation.response.PayloadResponse;
import com.moquawel.tenderInvitation.response.OfferResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class TenderInvitationService {

    @Value("${search-service.pbkStrId}")
    private int pbkStrId;
    private final RetrieveClient retrieveClient;
    private final AoAndGeneralInfoClient aoAndGeneralInfoClient;
    private final LotAndArticleInfo lotAndArticleInfo;
    private final ProdInfo prodInfo;
    private final AgrementInfo agrementInfo;
    private final TermsOfReferenceInfo termsOfReferenceInfo;
    private final OfferRepository offerRepository;


    public Object getTermsOfReferenceInfo(String epBidMasterId) {
        try {
            return termsOfReferenceInfo.getTermsOfReferenceInfoClient(epBidMasterId).getBody();
        } catch (Exception e) {
            log.error("this error occurred while retrieving the terms of reference info of an offer: {}", e.getMessage());
            return null;
        }
    }

    public Object getAgrementInfo(String epBidMasterId) {
        try {
            return agrementInfo.getAgrementInfoClient(epBidMasterId).getBody();
        } catch (Exception e) {
            log.error("this error occurred while retrieving the agrement info of an offer: {}", e.getMessage());
            return null;
        }
    }

    public Object getProdInfo(String epBidMasterId) {
        try {
            return prodInfo.getProdInfoClient(epBidMasterId).getBody();
        } catch (Exception e) {
            log.error("this error occurred while retrieving the prod info of an offer: {}", e.getMessage());
            return null;
        }
    }

    public Object getLotAndArticleInfo(String bidNo) {
        try {
            return lotAndArticleInfo.getLotAndArticleInfoClient(bidNo).getBody();
        } catch (Exception e) {
            log.error("this error occurred while retrieving the lot and article info of an offer: {}", e.getMessage());
            return null;
        }
    }

    public Object getAoAndGeneralInfo(String epBidMasterId) {
        try {
            return aoAndGeneralInfoClient.getAoAndGeneralInfoClient(epBidMasterId).getBody();
        } catch (Exception e) {
            log.error("this error occurred while retrieving the ao and general info of an offer: {}", e.getMessage());
            return null;
        }
    }


    public PayloadResponse getOffers() {
        List<Offer> response;

        try {
            response = offerRepository.findAll();
        } catch (Exception e) {
            log.error("this error occurred while retrieving the offers: {}", e.getMessage());
            response = new ArrayList<>();
        }

        return PayloadResponse
                .builder()
                .total(response.size())
                .data(response)
                .build();
    }


    public void getOffersFromTuneps() {

        Map<String, Object> formData = new HashMap<>();
        var currentDateTime = this.formatNowDate();

        formData.put("publicYn", 'Y');
        formData.put("pbkStrId", pbkStrId);
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
                        .peek(data -> {
                            data.setMyBdRecvEndDt(this.fromStringToDatetime(data.getBdRecvEndDt()));
                            data.setBidModSeq(null);
                        })
                        .toList();
                if (!newOfferList.isEmpty())
                    offerRepository.saveAll(newOfferList);
            }
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

        if (formData.containsKey("pbkStrId")) {
            options.add(new CriteriaSearch("pbkStrId", formData.get("pbkStrId").toString(), "equals").toMap());
        }

        if (formData.containsKey("publicYn")) {
            options.add(new CriteriaSearch("publicYn", formData.get("publicYn").toString(), "=").toMap());
        }

        if (formData.containsKey("bidNo")) {
            options.add(new CriteriaSearch("bidNo", formData.get("bidNo").toString().trim(), "upper_like").toMap());
        }

        return options;
    }


    private LocalDateTime fromStringToDatetime(String datetime1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(datetime1.substring(0, datetime1.indexOf('.')), formatter);
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
