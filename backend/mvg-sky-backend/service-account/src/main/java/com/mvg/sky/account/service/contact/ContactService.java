package com.mvg.sky.account.service.contact;

import java.util.Collection;
import java.util.List;

public interface ContactService {
    Collection<?> getAllContacts(List<String> profileIds,
                                 List<String> sorts,
                                 Integer offset,
                                 Integer limit);
}
