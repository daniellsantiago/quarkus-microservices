package domain;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public void save(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate findById(String id) {
        return candidateRepository.findById(id).orElseThrow();
    }
}
