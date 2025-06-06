package N0tice_Project.N0tice_BE.global.exception;

import N0tice_Project.N0tice_BE.global.BaseCode;
import N0tice_Project.N0tice_BE.global.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseCode code;

    public ReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
