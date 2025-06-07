package N0tice_Project.N0tice_BE.situation.service;

import N0tice_Project.N0tice_BE.situation.domain.Situation;
import N0tice_Project.N0tice_BE.situation.dto.SituationRequest;
import N0tice_Project.N0tice_BE.situation.dto.SituationResponse;
import N0tice_Project.N0tice_BE.situation.repository.SituationRepository;
import N0tice_Project.N0tice_BE.user.domain.User;
import N0tice_Project.N0tice_BE.user.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class SituationService {

    private final SituationRepository situationRepository;
    private final UserRepository userRepository;

    public SituationService(SituationRepository situationRepository, UserRepository userRepository) {
        this.situationRepository = situationRepository;
        this.userRepository = userRepository;
    }

    public SituationResponse createSituation(SituationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다. id=" + request.getUserId()));

        Situation situation = Situation.builder()
                .user(user)
                .situationType(request.getSituationType())
                .additionalInfoText(request.getAdditionalInfoText())
                .inputKeywords(request.getInputKeywords())
                .inputTimestamp(LocalDateTime.now())
                .build();

        Situation saved = situationRepository.save(situation);
        return SituationResponse.of(saved);
    }
}
