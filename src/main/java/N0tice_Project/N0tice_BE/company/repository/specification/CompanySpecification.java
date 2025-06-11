package N0tice_Project.N0tice_BE.company.repository.specification;

import N0tice_Project.N0tice_BE.company.domain.Company;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils; // null, 빈 문자열, 공백 문자열을 한번에 체크

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

// Company 엔티티에 대한 동적 쿼리 조건을 생성하는 클래스입니다.
public class CompanySpecification {

    public static Specification<Company> search(String city, String district, String neighborhood) {

        return (root, query, cb) -> {

            // Predicate는 개별 쿼리 조건(WHERE 절의 일부)을 나타냄.
            // 여러 조건을 담을 수 있는 리스트를 생성
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(city)) {
                // "WHERE address_city = 'city 값'"
                predicates.add(cb.equal(root.get("addressCity"), city));
            }

            if (StringUtils.hasText(district)) {
                predicates.add(cb.equal(root.get("addressDistrict"), district));
            }

            // null 값이 아닐 때만 선택적으로 조합됨.
            if (StringUtils.hasText(neighborhood)) {
                predicates.add(cb.equal(root.get("addressNeighborhood"), neighborhood));
            }

            // "WHERE city AND district AND ..."
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
