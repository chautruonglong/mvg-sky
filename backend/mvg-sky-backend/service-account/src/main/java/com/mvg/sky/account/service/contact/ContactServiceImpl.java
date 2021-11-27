package com.mvg.sky.account.service.contact;

import com.mvg.sky.repository.ContactRepository;
import com.mvg.sky.repository.entity.ContactEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public Collection<?> getAllContacts(List<String> accountIds,
                                                    List<String> sorts,
                                                    Integer offset,
                                                    Integer limit) {

        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        Collection<?> contactEntities = contactRepository.findAllByProfileIdAndIsDeletedFalse(
            accountIds.stream().map(UUID::fromString).collect(Collectors.toList()),
            pageable
        );

        log.info("find {} session entities", contactEntities == null ? 0 : contactEntities.size());
        return contactEntities;
    }
}
