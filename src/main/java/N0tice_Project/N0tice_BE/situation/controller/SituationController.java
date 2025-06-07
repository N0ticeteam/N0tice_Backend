package N0tice_Project.N0tice_BE.situation.controller;

import N0tice_Project.N0tice_BE.global.ApiResponse;
import N0tice_Project.N0tice_BE.situation.dto.SituationRequest;
import N0tice_Project.N0tice_BE.situation.dto.SituationResponse;
import N0tice_Project.N0tice_BE.situation.service.SituationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Situation API", description = "사용자 산재 상황 입력 관리")
@RestController
@RequiredArgsConstructor
public class SituationController {

    private final SituationService situationService;

    @Operation(summary = "상황 입력 생성", description = "새로운 사용자 상황 입력을 생성합니다.")
    @PostMapping("api/situation-input")
    public ApiResponse<SituationResponse> createSituation(@RequestBody SituationRequest request) {
        SituationResponse resp = situationService.createSituation(request);
        return ApiResponse.onSuccess(resp);
    }
}
