package com.cojeans.osiopso.service.user;

import com.cojeans.osiopso.dto.response.feed.UserSearchResponseDto;
import com.cojeans.osiopso.dto.user.FollowResponseDto;
import com.cojeans.osiopso.dto.user.SignUpRequestDto;
import com.cojeans.osiopso.dto.user.UserDto;
import com.cojeans.osiopso.dto.user.UserModifyDto;
import com.cojeans.osiopso.entity.user.AuthProvider;
import com.cojeans.osiopso.entity.user.Follow;
import com.cojeans.osiopso.entity.user.User;
import com.cojeans.osiopso.exception.BadRequestException;
import com.cojeans.osiopso.exception.ResourceNotFoundException;
import com.cojeans.osiopso.repository.user.FollowRepository;
import com.cojeans.osiopso.repository.user.UserRepository;
import com.cojeans.osiopso.security.TokenProvider;
import com.cojeans.osiopso.security.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private FollowRepository followRepository;

    public UserDto saveUser(SignUpRequestDto signUpRequest){
        User userEntity = userRepository.save(User.builder()
                        .name(signUpRequest.getName())
                        .email(signUpRequest.getEmail())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .age(signUpRequest.getAge())
                        .gender(signUpRequest.getGender())
                        .provider(AuthProvider.local)
                        .imageUrl(signUpRequest.getImageUrl())
                        .emailVerified(false)
                        .build()
        );
        log.info("result : {}", userEntity);


        return userEntity.toDto();
    }

    //비밀번호 일치 체크
    public boolean isPasswordMatch(String inputPassword,String dataBasePassword) {
        return passwordEncoder.matches(inputPassword,dataBasePassword);
    }

    public boolean isEmailExist(String email) {
        //중복된 Email로 등록을하면
        if(userRepository.existsByEmail(email)) {
            return true;
        }
        return false;
    }

    public UserDto getUser(Long userId, UserDetail userDetail){
        UserDto userDto = userRepository.findById(userId).orElse(null).toDto();

        if(followRepository.findByFollowingIdAndFollowerId(userId, userDetail.getId()) != null) userDto.setFollowed(true);
        else userDto.setFollowed(false);
        System.out.println("유저디티오^_^ : " + userDto);
        return userDto;
    }

    @Transactional
    public UserDto editUser(UserModifyDto userModifyDto) {
        UserDto userDto = userRepository
                .findById(userModifyDto.getId())
                .orElseThrow(()-> new BadRequestException("없는 유저입니다."))
                .toDto();

        if(StringUtils.isNotBlank(userModifyDto.getName())) userDto.setName(userDto.getName());
        if(userDto.getGender()!=null) userDto.setGender(userDto.getGender());
        userDto.setAge(userModifyDto.getAge());
        if(userDto.getImageUrl()!=null) userDto.setImageUrl(userDto.getImageUrl());

        return userRepository.save(User.builder()
                        .id(userDto.getId())
                        .email(userDto.getEmail())
                        .name(userDto.getName())
                        .password(userDto.getPassword())
                        .gender(userDto.getGender())
                        .age(userDto.getAge())
                        .imageUrl(userDto.getImageUrl())
                        .provider(userDto.getProvider())
                        .providerId(userDto.getProviderId())
                        .emailVerified(userDto.getEmailVerified())
                        .bio(userDto.getBio())
                        .isProfilePublic(userDto.getIsProfilePublic())
                        .role(userDto.getRole())
                .build()).toDto();
    }

    public List<UserSearchResponseDto> searchUserByNickname(String input) {

        // 유저의 아이디가 포함되어 시작되는 유저들 목록 가져오기
        List<User> users = userRepository.findAllByNameStartingWith(input);
        List<UserSearchResponseDto> userList = new ArrayList<>();

        for (User user : users) {
            userList.add(UserSearchResponseDto.builder()
                    .userName(user.getName())
                    .imageUrl(user.getImageUrl())
                    .build());
        }

        return userList;
    }

    @Transactional
    public void followUser(Long followingId, UserDetail userDetail) {
        // 아직 팔로우하지 않는 경우 해당 유저 팔로우
        if(followRepository.findByFollowingIdAndFollowerId(followingId, userDetail.getId()) == null) {
            followRepository.save(Follow.builder()
                    .following(userRepository.findById(followingId).orElseThrow())
                    .follower(userRepository.findById(userDetail.getId()).orElseThrow())
                    .build());
        } else { // 이미 팔로우한 경우 언팔로우
            followRepository.deleteByFollowingIdAndFollowerId(followingId, userDetail.getId());
        }

    }

//    @Transactional
//    public void unfollowUser(String email, UserDetail userDetail) {
//        User following  = userRepository.findByEmail(email).orElseThrow();
//        User follower = userRepository.findById(userDetail.getId()).orElseThrow();
//
//        followRepository.deleteByFollowingIdAndFollowerId(following.getId(), follower.getId());
//    }

    public List<FollowResponseDto> listFollower(Long followingId) {
        User targetUser = userRepository.findById(followingId).orElseThrow();

        List<Follow> followers = followRepository.findAllByFollowingId(targetUser.getId());
        List<FollowResponseDto> result = new ArrayList<>();

        for (Follow follower : followers) {
            User user = userRepository.findById(follower.getFollower().getId()).orElseThrow();
            result.add(FollowResponseDto.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .imageUrl(user.getImageUrl())
                    .build());
        }
        return result;
    }

    public List<FollowResponseDto> listFollowing(Long followingId) {
        User targetUser = userRepository.findById(followingId).orElseThrow();

        List<Follow> followings = followRepository.findAllByFollowerId(targetUser.getId());
        List<FollowResponseDto> result = new ArrayList<>();

        for (Follow following : followings) {
            User user = userRepository.findById(following.getFollowing().getId()).orElseThrow();
            result.add(FollowResponseDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .imageUrl(user.getImageUrl())
                    .build());
        }
        return result;
    }

    /* 이메일 인증이 되어있는지. 되어있지 않다면 false 반환*/
    public boolean isEmailVerified(String email) {
        if(userRepository.existsByEmail(email)){
            return userRepository.findByEmail(email).orElse(null).getEmailVerified();
        }
        return false;
    }

    public boolean changeIsProfilePublic(Long id) {
        userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id.toString(),"userRepository",null))
                .changeIsProfilePublic();
        return true;
    }

    public void modifyPassword(String password, Long id) {
            userRepository
                    .findById(id)
                    .orElseThrow(()-> new BadRequestException("존재하지 않는 유저입니다. 비밀번호를 바꿀 수 없습니다."))
                .setPassword(passwordEncoder.encode(password));
    }
}
