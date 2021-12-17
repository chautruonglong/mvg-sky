package com.mvg.sky.account.dto.request;

import java.util.UUID;
import lombok.Data;

@Data
public class ContactCreationRequest {
    private UUID yourProfileId;
    private UUID partnerProfileId;
}
