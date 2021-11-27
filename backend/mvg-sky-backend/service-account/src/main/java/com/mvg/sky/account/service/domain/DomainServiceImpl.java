package com.mvg.sky.account.service.domain;

import com.mvg.sky.repository.DomainRepository;
import com.mvg.sky.repository.entity.DomainEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
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
    private DomainRepository domainRepository;

    @Override
    public DomainEntity createDomain(String name) {
        DomainEntity domainEntity = DomainEntity.builder()
            .name(name)
            .build();

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
    public Integer deleteDomainById(String domainId) {
        int num = domainRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(domainId));

        log.info("delete domain {}, {} updated", domainId, num);
        return num;
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
