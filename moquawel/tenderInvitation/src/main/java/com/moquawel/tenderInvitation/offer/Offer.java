package com.moquawel.tenderInvitation.offer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "offer")
public class Offer {

    @Id
    private String offerId;

    private String bdRecvEndDt;

    private String bidInstNm;

    private String bidNmEn;

    private String publicDt;

    private String publicYn;

    private String bidModSeq;

    private String bidNo;

    private String bidNmAr;

    private String bidNmFr;

    private int epBidMasterId;

}
