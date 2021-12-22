package com.mvg.sky.account.service.domain;

import com.mvg.sky.repository.entity.DomainEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;

public interface DomainService {
    DomainEntity createDomain(String name) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException;

    DomainEntity updateDomain(String domainId, String name);

    Integer deleteDomainById(String domainId) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException;

    DomainEntity getDomainById(String domainId);

    Collection<DomainEntity> getAllDomains(List<String> sorts,
                                           Integer offset,
                                           Integer limit);
}
