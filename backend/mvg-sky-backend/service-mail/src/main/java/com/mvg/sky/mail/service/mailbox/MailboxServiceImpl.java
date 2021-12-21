package com.mvg.sky.mail.service.mailbox;

import com.mvg.sky.repository.FolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailboxServiceImpl implements MailboxService {
    private final FolderRepository folderRepository;
}
