package com.moquawel.authentication.token;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenBlackListService {

    private  MongoTemplate mongoTemplate;

    public void expireAllTokensByUser(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update().set("expires_at", new Date());
        mongoTemplate.updateMulti(query, update, TokenBlackList.class);
    }

    public void expireTokenByUser(String userId, String jti) {
        Query query = new Query(
                Criteria.where("userId").is(userId)
                        .and("jti").ne(jti)
        );
        Update update = new Update().set("expires_at", new Date());
        mongoTemplate.updateMulti(query, update, TokenBlackList.class);
    }

    public boolean existsNonExpiredTokens(String jti) {
        Query query = new Query(Criteria.where("jti").is(jti)
                .and("expires_at").is(null)); // Matches documents where expiresAt is null
        long count = mongoTemplate.count(query, TokenBlackList.class);
        return count > 0;
    }

}

