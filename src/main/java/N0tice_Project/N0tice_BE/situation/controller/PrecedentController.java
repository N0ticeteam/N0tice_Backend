package N0tice_Project.N0tice_BE.situation.controller;

import N0tice_Project.N0tice_BE.global.ApiResponse;
import N0tice_Project.N0tice_BE.situation.dto.PrecedentDetailResponse;
import N0tice_Project.N0tice_BE.situation.dto.PrecedentTitleResponse;
import N0tice_Project.N0tice_BE.situation.service.PrecedentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accident-cases")
public class PrecedentController {

    private final PrecedentService precedentService;

    @GetMapping("/match")
    public ApiResponse<List<PrecedentTitleResponse>> getPrecedentsForSituation(
            @RequestParam("input-id") Long situationId) {

        List<PrecedentTitleResponse> titles = precedentService.findPrecedentsForSituation(situationId);
        return ApiResponse.onSuccess(titles);
    }

    @GetMapping("/detail")
    public ApiResponse<PrecedentDetailResponse> getPrecedentDetail(
            @RequestParam("input-id") Long situationId,
            @RequestParam("case-number") String caseNumber) {

        PrecedentDetailResponse detail = precedentService.findPrecedentDetail(situationId, caseNumber);
        return ApiResponse.onSuccess(detail);
    }

}
