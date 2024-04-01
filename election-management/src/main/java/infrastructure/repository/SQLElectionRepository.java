package infrastructure.repository;

import domain.Election;
import domain.ElectionRepository;
import infrastructure.repository.entity.ElectionCandidateEntity;
import infrastructure.repository.entity.ElectionEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class SQLElectionRepository implements ElectionRepository {
    private final EntityManager entityManager;

    public SQLElectionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void submit(Election election) {
        entityManager.merge(ElectionEntity.fromDomain(election));

        election.votes()
                .entrySet()
                .stream()
                .map(entry -> ElectionCandidateEntity.fromDomain(election, entry.getKey(), entry.getValue()))
                .forEach(entityManager::merge);
    }
}
