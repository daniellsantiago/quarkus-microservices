package domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CandidateRepository {
    void save(List<Candidate> candidates);

    List<Candidate> find(CandidateQuery query);

    default void save(Candidate candidate) {
        save(List.of(candidate));
    }

    default List<Candidate> findAll() {
        return find(new CandidateQuery.Builder().build());
    }

    default Optional<Candidate> findById(String id) {
        var query = new CandidateQuery.Builder().ids(Set.of(id)).build();
        return find(query).stream().findFirst();
    }
}
