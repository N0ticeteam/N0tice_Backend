package N0tice_Project.N0tice_BE.company.controller;

import N0tice_Project.N0tice_BE.company.domain.Company;
import N0tice_Project.N0tice_BE.company.dto.CompanyResponse;
import N0tice_Project.N0tice_BE.company.service.CompanyService;
import N0tice_Project.N0tice_BE.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/search")
    public ApiResponse<List<CompanyResponse>> searchCompanies(
            @RequestParam("keyword") String keyword) {

        List<Company> companies = companyService.searchCompanies(keyword);

        List<CompanyResponse> dtoList = companies.stream()
                .map(CompanyResponse::fromEntity)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(dtoList);
    }

    // 시, 구, 동으로 검색
    @GetMapping("/location")
    public ApiResponse<List<CompanyResponse>> searchCompaniesByLocation(
            @RequestParam("city") String city,
            @RequestParam("district") String district,
            @RequestParam("neighborhood") String neighborhood) {

        List<Company> companies = companyService.searchCompaniesByLocation(city, district, neighborhood);

        List<CompanyResponse> dtoList = companies.stream()
                .map(CompanyResponse::fromEntity)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(dtoList);
    }
}