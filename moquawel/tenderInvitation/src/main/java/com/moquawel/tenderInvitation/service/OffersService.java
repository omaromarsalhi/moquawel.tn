package com.moquawel.tenderInvitation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class OffersService {

    private MongoTemplate mongoTemplate;

        public void saveIfDoesntExist(String bidNo) {
        Query query = new Query(Criteria.where("bidNo").ne(bidNo));
        mongoTemplate.save(query);
    }

//    public void removeUserTokens(String userId) {
//        Query query = new Query(Criteria.where("userId").is(userId));
//        mongoTemplate.remove(query, TokenBlackList.class);
//    }
//
//    public void removeUserTokensBtJti(String userId) {
//        Query query = new Query(Criteria.where("userId").is(userId));
//        mongoTemplate.remove(query, TokenBlackList.class);
//    }
//
//    public void removeExpiredTokenByUser(String userId, String jti) {
//        Query query = new Query(
//                Criteria.where("userId").is(userId)
//                        .and("jti").ne(jti)
//        );
//        mongoTemplate.remove(query, TokenBlackList.class);
//    }
//
//    public boolean existsNonExpiredTokens(String jti,String userId) {
//        Query query = new Query(
//                Criteria.where("userId").is(userId)
//                        .and("jti").is(jti)
//        );
//        long count = mongoTemplate.count(query, TokenBlackList.class);
//        return count > 0;
//    }

}

