package N0tice_Project.N0tice_BE.global.status;

import N0tice_Project.N0tice_BE.global.BaseCode;
import N0tice_Project.N0tice_BE.global.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    
    // User 관련 성공 응답
    _USER_INFO_SUCCESS(HttpStatus.OK, "USER200", "사용자 정보 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReasonHttpStatus(){
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}