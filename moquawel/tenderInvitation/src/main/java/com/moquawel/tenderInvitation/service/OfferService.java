package com.moquawel.tenderInvitation.service;


import com.moquawel.tenderInvitation.offer.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final MongoTemplate mongoTemplate;

    public void removeExpiredOffers() {
        Query query = new Query();
        query.addCriteria(Criteria.where("myBdRecvEndDt").lt(LocalDateTime.now()));
        mongoTemplate.remove(query, Offer.class);
    }
}
