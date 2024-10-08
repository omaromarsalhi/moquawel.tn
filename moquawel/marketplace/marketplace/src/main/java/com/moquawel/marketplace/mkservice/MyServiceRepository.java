package com.moquawel.marketplace.mkservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyServiceRepository extends MongoRepository<MyService, String> {
}
