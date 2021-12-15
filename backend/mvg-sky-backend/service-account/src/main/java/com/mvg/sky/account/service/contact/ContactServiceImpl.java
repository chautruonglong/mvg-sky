package com.mvg.sky.account.service.contact;

import com.mvg.sky.account.dto.request.ContactCreationRequest;
import com.mvg.sky.repository.ContactRepository;
import com.mvg.sky.repository.dto.query.ProfileContactDto;
import com.mvg.sky.repository.entity.ContactEntity;
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
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public Collection<ProfileContactDto> getAllContacts(List<String> accountIds,
                                                        List<String> domainIds,
                                                        List<String> sorts,
                                                        Integer offset,
                                                        Integer limit) {

        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<UUID> accountUuids = accountIds != null ? accountIds.stream().map(UUID::fromString).toList() : null;
        List<UUID> domainUuids = domainIds != null ? domainIds.stream().map(UUID::fromString).toList() : null;

        Collection<ProfileContactDto> contactEntities = contactRepository.findAllContacts(accountUuids, domainUuids, pageable);

        log.info("find {} session entities", contactEntities == null ? 0 : contactEntities.size());
        return contactEntities;
    }

    @Override
    public ContactEntity addNewContact(ContactCreationRequest contactCreationRequest) {
        ContactEntity contactEntity = ContactEntity.builder()
            .yourId(contactCreationRequest.getYourProfileId())
            .partnerId(contactCreationRequest.getPartnerProfileId())
            .build();

        contactEntity = contactRepository.save(contactEntity);

        log.info("save new contact {}", contactEntity);
        return contactEntity;
    }

    @Override
    public Integer deleteAllContacts(List<String> profileIds) {
        List<UUID> profileUuids = profileIds != null ? profileIds.stream().map(UUID::fromString).toList() : null;
        int num = contactRepository.clearAll(profileUuids);

        log.info("delete successfully, {} records changed", num);
        return num;
    }

    @Override
    public Integer deleteContactById(String contactId) {
        int num = contactRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(contactId));

        log.info("delete successfully, {} records changed", num);
        return num;
    }
}
