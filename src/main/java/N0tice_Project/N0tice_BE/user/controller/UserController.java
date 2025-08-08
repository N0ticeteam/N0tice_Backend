package N0tice_Project.N0tice_BE.user.controller;

import N0tice_Project.N0tice_BE.auth.config.JwtTokenProvider;
import N0tice_Project.N0tice_BE.global.ApiResponse;
import N0tice_Project.N0tice_BE.global.status.SuccessStatus;
import N0tice_Project.N0tice_BE.user.dto.UserResponse;
import N0tice_Project.N0tice_BE.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "현재 로그인한 사용자 정보 조회", description = "JWT 토큰을 통해 현재 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        // Bearer 토큰에서 실제 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");
        
        // 토큰에서 사용자 ID 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token);

        UserResponse userResponse = userService.getUserById(userId);

        return ApiResponse.onSuccess(userResponse);
    }

    @Operation(summary = "특정 사용자 정보 조회", description = "사용자 ID를 통해 특정 사용자의 정보를 조회합니다.")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String userId) {
        UserResponse userResponse = userService.getUserById(userId);

        return ApiResponse.onSuccess(userResponse);
    }



}