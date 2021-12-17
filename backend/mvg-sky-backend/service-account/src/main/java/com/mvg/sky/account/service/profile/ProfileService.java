package com.mvg.sky.account.service.profile;

import com.mvg.sky.account.dto.request.ProfileCreationRequest;
import com.mvg.sky.account.dto.request.ProfileModifyRequest;
import com.mvg.sky.repository.entity.ProfileEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    Collection<ProfileEntity> getAllProfilesByAccountIds(List<String> accountIds,
                                                         List<String> sorts,
                                                         Integer offset,
                                                         Integer limit);

    ProfileEntity createProfile(ProfileCreationRequest profileCreationRequest);

    ProfileEntity updateFullProfile(String profileId, ProfileModifyRequest profileModifyRequest);

    ProfileEntity updatePartialProfile(String profileId, ProfileModifyRequest profileModifyRequest);

    ProfileEntity getProfileById(String profileId);

    ProfileEntity uploadProfileAvatar(String profileId, MultipartFile avatar) throws IOException;

    Integer deleteProfileById(String profileId);
}
