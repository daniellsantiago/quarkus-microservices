package infrastrucutre.repository;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;
import infrastrucutre.repository.entity.CandidateEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {
    private final EntityManager entityManager;

    public SQLCandidateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidates) {
        candidates.stream()
          .map(CandidateEntity::fromDomain)
          .forEach(entityManager::merge);
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        var cb = entityManager.getCriteriaBuilder();
        var queryBuilder = cb.createQuery(CandidateEntity.class);
        var root = queryBuilder.from(CandidateEntity.class);

        var conditions = Stream.of(
          query.ids().map(ids -> cb.in(root.get("id")).value(ids)),
          query.name().map(name -> cb.or(
            cb.like(cb.lower(root.get("familyName")), name.toLowerCase() + "%"),
            cb.like(cb.lower(root.get("givenName")), name.toLowerCase() + "%")
          ))
        ).flatMap(Optional::stream)
          .toArray(Predicate[]::new);

        queryBuilder.select(root).where(conditions);

        return entityManager.createQuery(queryBuilder)
          .getResultStream()
          .map(CandidateEntity::toDomain)
          .toList();
    }
}
