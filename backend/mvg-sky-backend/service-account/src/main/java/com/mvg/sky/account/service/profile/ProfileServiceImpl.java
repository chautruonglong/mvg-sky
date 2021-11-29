package com.mvg.sky.account.service.profile;

import com.mvg.sky.account.dto.request.ProfileCreationRequest;
import com.mvg.sky.account.dto.request.ProfileModifyRequest;
import com.mvg.sky.account.util.mapper.MapperUtil;
import com.mvg.sky.repository.ProfileRepository;
import com.mvg.sky.repository.entity.ProfileEntity;
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
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final MapperUtil mapperUtil;

    @Override
    public Collection<ProfileEntity> getAllProfilesByAccountIds(List<String> accountIds, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Collection<ProfileEntity> profileEntities;

        if(accountIds == null) {
            profileEntities = profileRepository.findAllByIsDeletedFalse(pageable);
        }
        else {
            profileEntities = profileRepository.findAllByAccountIdInAndIsDeletedFalse(
                accountIds.stream().map(UUID::fromString).collect(Collectors.toList()),
                pageable
            );
        }

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
    public Integer deleteProfileById(String profileId) {
        int num = profileRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(profileId));

        log.info("delete profile successfully, {} updated", num);
        return num;
    }
}
