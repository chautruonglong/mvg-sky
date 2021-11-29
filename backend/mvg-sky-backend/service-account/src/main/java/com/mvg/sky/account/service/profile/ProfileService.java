package com.mvg.sky.account.service.profile;

import com.mvg.sky.account.dto.request.ProfileCreationRequest;
import com.mvg.sky.account.dto.request.ProfileModifyRequest;
import com.mvg.sky.repository.entity.ProfileEntity;
import java.util.Collection;
import java.util.List;

public interface ProfileService {
    Collection<ProfileEntity> getAllProfilesByAccountIds(List<String> accountIds,
                                                         List<String> sorts,
                                                         Integer offset,
                                                         Integer limit);

    ProfileEntity createProfile(ProfileCreationRequest profileCreationRequest);

    ProfileEntity updateFullProfile(String profileId, ProfileModifyRequest profileModifyRequest);

    ProfileEntity updatePartialProfile(String profileId, ProfileModifyRequest profileModifyRequest);

    ProfileEntity getProfileById(String profileId);

    Integer deleteProfileById(String profileId);
}
