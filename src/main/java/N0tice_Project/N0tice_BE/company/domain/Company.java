package N0tice_Project.N0tice_BE.company.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    private Integer dataYear;
    private String address;
    private String addressCity;
    private String addressDistrict;
    private String addressNeighborhood;

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 DB에 저장
    @Column(nullable = false)
    private AccidentType accidentType; // string -> enum 타입 변경
}
