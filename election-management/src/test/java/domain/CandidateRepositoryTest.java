package domain;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

public abstract class CandidateRepositoryTest {
    public abstract CandidateRepository repository();

    @Test
    void save() {
        Candidate candidate = Instancio.create(Candidate.class);
        repository().save(candidate);

        var result = repository().findById(candidate.id());

        assertTrue(result.isPresent());
        assertEquals(candidate, result.get());
    }

    @Test
    void findAll() {
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(2).toList();

        repository().save(candidates);

        var result = repository().findAll();

        assertEquals(candidates.size(), result.size());
    }

    @Test
    void findByNamePassingFamilyName() {
        Candidate candidate1 = Instancio.create(Candidate.class);
        Candidate candidate2 = Instancio.of(Candidate.class)
          .set(field("familyName"), "Smith").create();

        var query = new CandidateQuery.Builder().name("SMI").build();

        repository().save(List.of(candidate1, candidate2));

        var result = repository().find(query);

        assertEquals(1, result.size());
        assertEquals(candidate2, result.get(0));
    }

    @Test
    void findByNamePassingGivenName() {
        Candidate candidate1 = Instancio.create(Candidate.class);
        Candidate candidate2 = Instancio.of(Candidate.class)
          .set(field("givenName"), "Daniel").create();

        var query = new CandidateQuery.Builder().name("Dan").build();

        repository().save(List.of(candidate1, candidate2));

        var result = repository().find(query);

        assertEquals(1, result.size());
        assertEquals(candidate2, result.get(0));
    }
}
