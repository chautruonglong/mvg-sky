package com.mvg.sky.account.service.domain;

import com.mvg.sky.repository.entity.DomainEntity;
import java.util.Collection;
import java.util.List;

public interface DomainService {
    DomainEntity createDomain(String name);

    DomainEntity updateDomain(String domainId, String name);

    Integer deleteDomainById(String domainId);

    DomainEntity getDomainById(String domainId);

    Collection<DomainEntity> getAllDomains(List<String> sorts,
                                           Integer offset,
                                           Integer limit);
}
