package com.moquawel.authentication.token;


import org.springframework.data.mongodb.repository.MongoRepository;



public interface TokenBlackListRepository extends MongoRepository<TokenBlackList, String> {

}
