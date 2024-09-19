package com.moquawel.tenderInvitation.offer;

import com.moquawel.subscription.subscription.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    
}
