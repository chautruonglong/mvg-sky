package com.mvg.sky.mail.service.mail;

import com.mvg.sky.repository.MailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final MailRepository mailRepository;
}
