package com.mvg.sky.account.service.domain;

import com.mvg.sky.james.operation.DomainOperation;
import com.mvg.sky.repository.DomainRepository;
import com.mvg.sky.repository.entity.DomainEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DomainServiceImpl implements DomainService {
    private final DomainRepository domainRepository;
    private final DomainOperation domainOperation;

    @Override
    public DomainEntity createDomain(String name) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        // Create domain on Apache James
        if(!domainOperation.containsDomain(name)) {
            domainOperation.addDomain(name);
        }

        // Create domain on MVG Sky
        DomainEntity domainEntity = domainRepository.findByNameAndIsDeletedTrue(name);

        if(domainEntity != null) {
            domainEntity.setIsDeleted(false);
        }
        else {
            domainEntity = DomainEntity.builder()
                .name(name)
                .build();
        }

        domainEntity = domainRepository.save(domainEntity);

        log.info("save new domain {}", domainEntity);
        return domainEntity;
    }

    @Override
    public DomainEntity updateDomain(String domainId, String name) {
        DomainEntity domainEntity = domainRepository.findByIdAndIsDeletedFalse(UUID.fromString(domainId));

        domainEntity.setName(name);
        domainEntity = domainRepository.save(domainEntity);

        log.info("save new domain {}", domainEntity);
        return domainEntity;
    }

    @Override
    public Integer deleteDomainById(String domainId) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        DomainEntity domainEntity = domainRepository.findById(UUID.fromString(domainId))
            .orElseThrow(() -> new RuntimeException("Domain not found in database"));

        domainRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(domainId));

        // Delete domain on Apache James
        if(domainOperation.containsDomain(domainEntity.getName())) {
            domainOperation.removeDomain(domainEntity.getName());
        }

        log.info("delete domain {}, {} updated", domainId, 1);
        return 1;
    }

    @Override
    public DomainEntity getDomainById(String domainId) {
        DomainEntity domainEntity = domainRepository.findByIdAndIsDeletedFalse(UUID.fromString(domainId));

        if(domainEntity == null) {
            log.error("Domain is not in database");
            throw new RuntimeException("Domain is not in database");
        }

        log.info("save new domain {}", domainEntity);
        return domainEntity;
    }

    @Override
    public Collection<DomainEntity> getAllDomains(List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Collection<DomainEntity> domainEntities = domainRepository.findAllByIsDeletedFalse(pageable);

        log.info("find {} account entities", domainEntities == null ? 0 : domainEntities.size());
        return domainEntities;
    }
}
