package N0tice_Project.N0tice_BE.company.repository;

import N0tice_Project.N0tice_BE.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // 메소드 이름 규칙을 사용한 쿼리 생성
    // Containing: SQL의 LIKE %keyword%
    List<Company> findByCompanyNameContaining(String keyword);

    // 새로운 쿼리 메소드 (시, 구, 동으로 검색)
    List<Company> findByAddressCityAndAddressDistrictAndAddressNeighborhood(String addressCity, String addressDistrict, String addressNeighborhood);
}
