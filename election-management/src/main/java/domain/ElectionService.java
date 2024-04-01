package domain;

import domain.annotations.SQL;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ElectionService {
    private final Instance<ElectionRepository> repositories;
    private final CandidateService candidateService;

    private final ElectionRepository sqlRepository;

    public ElectionService(@SQL ElectionRepository sqlRepository, @Any Instance<ElectionRepository> repositories, CandidateService candidateService) {
        this.sqlRepository = sqlRepository;
        this.repositories = repositories;
        this.candidateService = candidateService;
    }

    @Transactional
    public void submit() {
        Election election = Election.create(candidateService.findAll());
        repositories.forEach(repository -> repository.submit(election));
    }

    public List<Election> findAll() {
        return sqlRepository.findAll();
    }
}
