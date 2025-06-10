package N0tice_Project.N0tice_BE.company.service;

import N0tice_Project.N0tice_BE.company.domain.Company;
import N0tice_Project.N0tice_BE.company.repository.CompanyRepository;
import N0tice_Project.N0tice_BE.company.repository.specification.CompanySpecification;
import N0tice_Project.N0tice_BE.global.exception.GeneralException;
import N0tice_Project.N0tice_BE.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

// 리포지토리에 단순히 위임만 하는 클래스
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    // 사업장명 검색
    public List<Company> searchCompanies(String keyword) {

        List<Company> companies = companyRepository.findByCompanyNameContaining(keyword);

        return companies != null ? companies : Collections.emptyList();
    }

    // 시, 구, 동으로 검색
    public List<Company> searchCompaniesByLocation(String city, String district, String neighborhood) {
        // Specification 객체를 생성
        Specification<Company> spec = CompanySpecification.search(city, district, neighborhood);

        List<Company> companies = companyRepository.findAll(spec);

        return companies;
    }
}