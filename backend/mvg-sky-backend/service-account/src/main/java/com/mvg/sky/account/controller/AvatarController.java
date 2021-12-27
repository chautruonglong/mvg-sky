package com.mvg.sky.account.controller;

import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Avatar API")
@RestController
@RequiredArgsConstructor
public class AvatarController {
    @Value("${com.mvg.sky.service-account.external-resource}")
    private String externalResource;

    @NotNull
    private final AccountRepository accountRepository;

    @GetMapping("/accounts-resources/avatar/{accountId}")
    @ResponseBody
    public HttpEntity<byte[]> getAvatarApi(@PathVariable String accountId) {
        try {
            String ext = null, file = accountId;
            if(accountId.contains(".")) {
                int at = file.indexOf(".");
                accountId = file.substring(0, at);
                ext = file.substring(at + 1);
            }

            HttpHeaders headers = new HttpHeaders();
            String resource = externalResource.substring("file:".length()) + "accounts-resources/avatar/";
            InputStream inputStream;

            try {
                inputStream = new FileInputStream(resource + accountId + ".jpg");
                if(ext != null && !ext.equals("jpg")) {
                    throw new FileNotFoundException("File not found");
                }
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.set("Content-Disposition", "attachment; filename=" + accountId + ".jpg");
            }
            catch(FileNotFoundException e) {
                try {
                    inputStream = new FileInputStream(resource + accountId + ".jpeg");
                    if(ext != null && !ext.equals("jpeg")) {
                        throw new FileNotFoundException("File not found");
                    }
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    headers.set("Content-Disposition", "attachment; filename=" + accountId + ".jpeg");
                }
                catch(FileNotFoundException ex) {
                    inputStream = new FileInputStream(resource + accountId + ".png");
                    if(ext != null && !ext.equals("png")) {
                        throw new FileNotFoundException("File not found");
                    }
                    headers.setContentType(MediaType.IMAGE_PNG);
                    headers.set("Content-Disposition", "attachment; filename=" + accountId + ".png");
                }
            }

            return new HttpEntity<>(IOUtils.toByteArray(inputStream), headers);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts-resources/avatar-by-email/{email}")
    @ResponseBody
    public HttpEntity<byte[]> getAvatarByEmailApi(@Valid @Email @PathVariable String email) {
        try {
            AccountEntity accountEntity = accountRepository.findByUsernameAndIsDeletedFalseAndIsActiveTrue(email);

            if(accountEntity == null) {
                throw new RuntimeException("Email not found in database");
            }

            return getAvatarApi(accountEntity.getId().toString());
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
