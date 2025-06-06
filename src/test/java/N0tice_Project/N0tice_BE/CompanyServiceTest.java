package N0tice_Project.N0tice_BE;

import N0tice_Project.N0tice_BE.company.domain.AccidentType;
import N0tice_Project.N0tice_BE.company.domain.Company;
import N0tice_Project.N0tice_BE.company.repository.CompanyRepository;
import N0tice_Project.N0tice_BE.company.service.CompanyService;
import N0tice_Project.N0tice_BE.global.exception.GeneralException;
import N0tice_Project.N0tice_BE.global.status.ErrorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class) // Mockito 확장 기능을 사용하기 위한 어노테이션
class CompanyServiceTest {
    @Mock // 협력자(가짜 객체)
    private CompanyRepository companyRepository;

    @InjectMocks // 테스트 주체 (companyRepository Mock 객체가 주입됨)
    private CompanyService companyService;

    @Test
    @DisplayName("사업장명으로 회사 검색 성공")
    void searchCompanies_성공() {
        // Given: 테스트를 진행할 행위를 위한 사전 준비
        String keyword = "테스트";
        Company company1 = Company.builder().companyName("테스트회사1").accidentType(AccidentType.DEATH).build();
        Company company2 = Company.builder().companyName("다른테스트회사").accidentType(AccidentType.INJURY).build();
        List<Company> expectedCompanies = Arrays.asList(company1, company2);

        // companyRepository.findByCompanyNameContaining(keyword)가 호출되면 expectedCompanies를 반환하도록 설정
        given(companyRepository.findByCompanyNameContaining(keyword)).willReturn(expectedCompanies);

        // When: 테스트를 진행할 행위
        List<Company> actualCompanies = companyService.searchCompanies(keyword);

        // Then: 테스트를 진행한 행위에 대한 결과 검증
        assertThat(actualCompanies).isNotNull();
        assertThat(actualCompanies.size()).isEqualTo(2);
        assertThat(actualCompanies).isEqualTo(expectedCompanies); // 객체 내용 비교
        verify(companyRepository).findByCompanyNameContaining(keyword); // companyRepository의 메소드가 호출되었는지 검증
    }

    @Test
    @DisplayName("사업장명으로 회사 검색 결과 없음 - 예외 발생")
    void searchCompanies_결과없음_예외발생() {
        // Given
        String keyword = "없는회사";
        // companyRepository.findByCompanyNameContaining(keyword)가 호출되면 빈 리스트를 반환하도록 설정
        given(companyRepository.findByCompanyNameContaining(keyword)).willReturn(Collections.emptyList());

        // When & Then
        assertThatThrownBy(() -> companyService.searchCompanies(keyword))
                .isInstanceOf(GeneralException.class)
                .extracting("code") // "code" 프로퍼티(getter)를 통해 값을 추출
                .isEqualTo(ErrorStatus.COMPANY_NOT_FOUND); // 예외 내부의 errorStatus 검증

        verify(companyRepository).findByCompanyNameContaining(keyword);
    }

    @Test
    @DisplayName("사업장명으로 회사 검색 시 Repository 반환값이 null인 경우 - 예외 발생")
    void searchCompanies_RepositoryNull반환_예외발생() {
        // Given
        String keyword = "테스트";
        // companyRepository.findByCompanyNameContaining(keyword)가 호출되면 null을 반환하도록 설정
        given(companyRepository.findByCompanyNameContaining(keyword)).willReturn(null);

        // When & Then
        assertThatThrownBy(() -> companyService.searchCompanies(keyword))
                .isInstanceOf(GeneralException.class)
                .extracting("code") // "code" 프로퍼티(getter)를 통해 값을 추출
                .isEqualTo(ErrorStatus.COMPANY_NOT_FOUND); // 예외 내부의 errorStatus 검증

        verify(companyRepository).findByCompanyNameContaining(keyword);
    }

    @Test
    @DisplayName("시, 구, 동으로 회사 검색 성공")
    void searchCompaniesByLocation_성공() {
        // Given
        String city = "서울특별시";
        String district = "강남구";
        String neighborhood = "역삼동";
        Company company1 = Company.builder().addressCity(city).addressDistrict(district).addressNeighborhood(neighborhood).companyName("강남점").accidentType(AccidentType.DEATH).build();
        List<Company> expectedCompanies = Collections.singletonList(company1);

        given(companyRepository.findByAddressCityAndAddressDistrictAndAddressNeighborhood(city, district, neighborhood))
                .willReturn(expectedCompanies);

        // When
        List<Company> actualCompanies = companyService.searchCompaniesByLocation(city, district, neighborhood);

        // Then
        assertThat(actualCompanies).isNotNull();
        assertThat(actualCompanies.size()).isEqualTo(1);
        assertThat(actualCompanies.get(0).getCompanyName()).isEqualTo("강남점");
        verify(companyRepository).findByAddressCityAndAddressDistrictAndAddressNeighborhood(city, district, neighborhood);
    }

    @Test
    @DisplayName("시, 구, 동으로 회사 검색 결과 없음 - 예외 발생")
    void searchCompaniesByLocation_결과없음_예외발생() {
        // Given
        String city = "부산광역시";
        String district = "해운대구";
        String neighborhood = "우동";
        given(companyRepository.findByAddressCityAndAddressDistrictAndAddressNeighborhood(city, district, neighborhood))
                .willReturn(Collections.emptyList());

        // When & Then
        assertThatThrownBy(() -> companyService.searchCompaniesByLocation(city, district, neighborhood))
                .isInstanceOf(GeneralException.class)
                .extracting("code") // "code" 프로퍼티(getter)를 통해 값을 추출
                .isEqualTo(ErrorStatus.NO_SEARCH_RESULT); // 예외 내부의 errorStatus 검증

        verify(companyRepository).findByAddressCityAndAddressDistrictAndAddressNeighborhood(city, district, neighborhood);
    }

    @Test
    @DisplayName("시, 구, 동으로 회사 검색 시 Repository 반환값이 null인 경우 - 예외 발생")
    void searchCompaniesByLocation_RepositoryNull반환_예외발생() {
        // Given
        String city = "서울특별시";
        String district = "강남구";
        String neighborhood = "역삼동";
        given(companyRepository.findByAddressCityAndAddressDistrictAndAddressNeighborhood(city, district, neighborhood))
                .willReturn(null);

        // When & Then
        assertThatThrownBy(() -> companyService.searchCompaniesByLocation(city, district, neighborhood))
                .isInstanceOf(GeneralException.class)
                .extracting("code") // "code" 프로퍼티(getter)를 통해 값을 추출
                .isEqualTo(ErrorStatus.NO_SEARCH_RESULT); // 예외 내부의 errorStatus 검증

        verify(companyRepository).findByAddressCityAndAddressDistrictAndAddressNeighborhood(city, district, neighborhood);
    }

}