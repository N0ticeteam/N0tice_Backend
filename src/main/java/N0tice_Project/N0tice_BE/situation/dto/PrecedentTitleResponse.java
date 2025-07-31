package N0tice_Project.N0tice_BE.situation.dto;

import N0tice_Project.N0tice_BE.company.domain.Company;
import N0tice_Project.N0tice_BE.company.dto.CompanyResponse;
import N0tice_Project.N0tice_BE.situation.domain.Situation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccidentCaseTitleResponse {
    private String caseNumber; // 사건번호 (상세 조회를 위한 key)
    private String caseTitle;  // 사건명
}
