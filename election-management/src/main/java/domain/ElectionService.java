package domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ElectionService {
    private final Instance<ElectionRepository> repositories;
    private final CandidateService candidateService;

    public ElectionService(Instance<ElectionRepository> repositories, CandidateService candidateService) {
        this.repositories = repositories;
        this.candidateService = candidateService;
    }

    @Transactional
    public void submit() {
        Election election = Election.create(candidateService.findAll());
        repositories.forEach(repository -> repository.submit(election));
    }
}
