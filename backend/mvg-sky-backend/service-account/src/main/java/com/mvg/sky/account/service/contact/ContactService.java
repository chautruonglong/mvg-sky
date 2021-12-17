package com.mvg.sky.account.service.contact;

import com.mvg.sky.account.dto.request.ContactCreationRequest;
import com.mvg.sky.repository.entity.ContactEntity;
import java.util.Collection;
import java.util.List;

public interface ContactService {
    Collection<?> getAllContacts(List<String> profileIds,
                                 List<String> domainIds,
                                 List<String> sorts,
                                 Integer offset,
                                 Integer limit);

    ContactEntity addNewContact(ContactCreationRequest contactCreationRequest);

    Integer deleteAllContacts(List<String> profileIds);

    Integer deleteContactById(String contactId);
}
