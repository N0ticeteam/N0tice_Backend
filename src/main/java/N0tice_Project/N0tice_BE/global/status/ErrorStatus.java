package N0tice_Project.N0tice_BE.global.status;

import N0tice_Project.N0tice_BE.global.BaseCode;
import N0tice_Project.N0tice_BE.global.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // Company 관련 에러 코드 추가
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMPANY404", "해당 회사를 찾을 수 없습니다."),
    NO_SEARCH_RESULT(HttpStatus.NOT_FOUND, "COMPANY404", "검색 결과가 없습니다."),

    // Worklog 관련 에러 코드 추가
    _NOT_FOUND(HttpStatus.NOT_FOUND,"WORKLOG404", "해당 작업일지를 찾을 수 없습니다."),
    _DUPLICATE_RESOURCE(HttpStatus.CONFLICT,"WORKLOG409", "이미 작성된 작업일지가 있습니다."),

    // User 관련 에러
    _USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "해당 사용자를 찾을 수 없습니다."),

    // Situation 관련 에러
    _SITUATION_NOT_FOUND(HttpStatus.NOT_FOUND, "SITUATION404", "해당 상황 정보를 찾을 수 없습니다."),

    // Precedent(판례) 관련 에러
    _PRECEDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRECEDENT404", "해당 제목의 판례 상세 내용을 찾을 수 없습니다."),
    _EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PRECEDENT500", "외부 판례 API 호출 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ReasonDTO getReasonHttpStatus(){
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
