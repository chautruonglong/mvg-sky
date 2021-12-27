package com.mvg.sky.account.service.profile;

import com.mvg.sky.account.dto.request.ProfileCreationRequest;
import com.mvg.sky.account.dto.request.ProfileModifyRequest;
import com.mvg.sky.account.util.mapper.MapperUtil;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.util.file.FileUtil;
import com.mvg.sky.repository.ProfileRepository;
import com.mvg.sky.repository.entity.ProfileEntity;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    @NonNull
    private final ProfileRepository profileRepository;

    @NonNull
    private final MapperUtil mapperUtil;

    @Value("${com.mvg.sky.service-account.external-resource}")
    private String externalResources;

    @Override
    public Collection<ProfileEntity> getAllProfilesByAccountIds(List<String> accountIds, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<UUID> accountUuids = accountIds != null ? accountIds.stream().map(UUID::fromString).toList() : null;
        Collection<ProfileEntity> profileEntities = profileRepository.findAllProfiles(accountUuids, pageable);

        log.info("find {} account entities", profileEntities == null ? 0 : profileEntities.size());
        return profileEntities;
    }

    @Override
    public ProfileEntity createProfile(ProfileCreationRequest profileCreationRequest) {
        ProfileEntity profileEntity = new ProfileEntity();
        mapperUtil.createProfileFromDto(profileCreationRequest, profileEntity);
        profileEntity = profileRepository.save(profileEntity);

        log.info("save profile {}", profileEntity);
        return profileEntity;
    }

    @Override
    public ProfileEntity updateFullProfile(String profileId, ProfileModifyRequest profileModifyRequest) {
        ProfileEntity profileEntity = profileRepository.findById(UUID.fromString(profileId))
            .orElseThrow(() -> new RuntimeException("profileId is not in database"));

        mapperUtil.updateFullProfileFromDto(profileModifyRequest, profileEntity);
        profileEntity = profileRepository.save(profileEntity);

        log.info("update profile {}", profileEntity);
        return profileEntity;
    }

    @Override
    public ProfileEntity updatePartialProfile(String profileId, ProfileModifyRequest profileModifyRequest) {
        ProfileEntity profileEntity = profileRepository.findById(UUID.fromString(profileId))
            .orElseThrow(() -> new RuntimeException("profileId is not in database"));

        mapperUtil.updatePartialProfileFromDto(profileModifyRequest, profileEntity);
        profileEntity = profileRepository.save(profileEntity);

        log.info("update profile {}", profileEntity);
        return profileEntity;
    }

    @Override
    public ProfileEntity getProfileById(String profileId) {
        ProfileEntity profileEntity = profileRepository.findById(UUID.fromString(profileId))
            .orElseThrow(() -> new RuntimeException("profileId is not in database"));

        log.info("profile found {}", profileEntity);
        return profileEntity;
    }

    @Override
    public ProfileEntity uploadProfileAvatar(String profileId, MultipartFile avatar) throws IOException {
        ProfileEntity profileEntity = profileRepository.findById(UUID.fromString(profileId))
            .orElseThrow(() -> new RuntimeException("profileId is not in database"));

        String fileName = null;

        if(avatar != null) {
            fileName = avatar.getOriginalFilename();
        }

        if(fileName != null && !fileName.equals("")) {
            fileName = FileUtil.changeFileName(fileName, profileEntity.getAccountId().toString());

            if(!FileUtil.isImageFile(fileName)) {
                throw new RequestException("Avatar file is not an image", HttpStatus.BAD_REQUEST);
            }

            String path = externalResources.substring("file:".length()) + "/accounts-resources/avatar/";
            Files.createDirectories(Paths.get(path));
            Files.deleteIfExists(Paths.get(path + profileEntity.getOriginalAvatar()));

            avatar.transferTo(new File(path + fileName));
        }

        profileEntity.setAvatar(fileName != null && fileName.equals("") ? null : fileName);
        profileEntity = profileRepository.save(profileEntity);

        return profileEntity;
    }

    @Override
    public Integer deleteProfileById(String profileId) {
        int num = profileRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(profileId));

        log.info("delete profile successfully, {} updated", num);
        return num;
    }
}
