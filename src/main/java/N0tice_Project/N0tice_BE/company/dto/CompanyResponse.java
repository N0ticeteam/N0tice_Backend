package N0tice_Project.N0tice_BE.company.dto;

import N0tice_Project.N0tice_BE.company.domain.AccidentType;
import N0tice_Project.N0tice_BE.company.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private String companyName;
    private Integer dataYear;
    private String address;
    private String accidentType; // string으로 반환

    public static CompanyResponse fromEntity(Company company) {
        if (company == null) return null;
        return CompanyResponse.builder()
                .companyName(company.getCompanyName())
                .dataYear(company.getDataYear())
                .address(company.getAddress())
                .accidentType(company.getAccidentType() != null ? company.getAccidentType().getDisplayName() : null)
                .build();
    }
}

