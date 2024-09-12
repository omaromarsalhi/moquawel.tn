package com.moquawel.subscription.subscription;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "subscription")
public class Subscription {

    @Id
    private String userId;

    private Date started_at;

    private Date ends_at;

    private String status;

    private SubscriptionType type;

}
