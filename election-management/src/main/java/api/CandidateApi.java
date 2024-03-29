package api;

import api.dto.in.CreateCandidate;
import api.dto.out.CandidatePersonalInfo;
import domain.CandidateService;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CandidateApi {
    private final CandidateService service;

    public CandidateApi(CandidateService service) {
        this.service = service;
    }

    public void create(CreateCandidate createCandidate) {
        service.save(createCandidate.toDomain());
    }

    public List<CandidatePersonalInfo> findCandidatesPersonalInfo() {
        return service.findAll()
          .stream()
          .map(CandidatePersonalInfo::fromDomain)
          .toList();
    }
}
