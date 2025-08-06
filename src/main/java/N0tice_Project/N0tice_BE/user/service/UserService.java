package N0tice_Project.N0tice_BE.user.service;

import N0tice_Project.N0tice_BE.user.domain.User;
import N0tice_Project.N0tice_BE.user.dto.UserResponse;
import N0tice_Project.N0tice_BE.user.repository.UserRepository;
import N0tice_Project.N0tice_BE.global.exception.GeneralException;
import N0tice_Project.N0tice_BE.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;

    public UserResponse getUserById(String userId) {
        try {
            Long userIdLong = Long.parseLong(userId);
            User user = userRepository.findById(userIdLong)
                    .orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));

            return UserResponse.from(user);
            
        } catch (NumberFormatException e) {
            throw new GeneralException(ErrorStatus._INVALID_USER_ID);
        }
    }

}
