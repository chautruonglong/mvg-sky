package com.mvg.sky.account.service.contact;

import com.mvg.sky.repository.entity.ContactEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ContactService {
    Collection<?> getAllContacts(List<String> accountIds,
                                             List<String> sorts,
                                             Integer offset,
                                             Integer limit);
}
