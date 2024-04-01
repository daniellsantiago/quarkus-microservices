package domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ElectionService {
    private final ElectionRepository repository;

    public ElectionService(ElectionRepository repository) {
        this.repository = repository;
    }

    public List<Election> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void vote(String electionId, String candidateId) {
        repository.findById(electionId)
                  .candidates()
                  .stream()
                  .filter(candidate -> candidate.id().equals(candidateId))
                  .findFirst()
                  .ifPresent(candidate -> repository.vote(electionId, candidate));
    }
}
