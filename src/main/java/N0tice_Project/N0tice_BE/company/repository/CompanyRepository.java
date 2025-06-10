package N0tice_Project.N0tice_BE.company.repository;

import N0tice_Project.N0tice_BE.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    // 메소드 이름 규칙을 사용한 쿼리 생성
    // Containing: SQL의 LIKE %keyword%
    List<Company> findByCompanyNameContaining(String keyword);
}