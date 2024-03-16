package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

@QuarkusTest
class CandidateServiceTest {

    @Inject
    CandidateService candidateService;

    @InjectMock
    CandidateRepository candidateRepository;

    @Test
    void save() {
        // given
        var candidate = Instancio.create(Candidate.class);

        // when
        candidateService.save(candidate);

        // then
        verify(candidateRepository, times(1)).save(candidate);
    }

    @Test
    void findAll() {
        // given
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(10).toList();

        // when
        when(candidateRepository.findAll()).thenReturn(candidates);
        var result = candidateService.findAll();
        
        // then
        assertEquals(candidates, result);
    }

    @Test
    void findById() {
        // given
        var candidate = Instancio.create(Candidate.class);

        // when
        when(candidateRepository.findById(candidate.id())).thenReturn(Optional.of(candidate));
        var result = candidateService.findById(candidate.id());

        // then
        assertEquals(candidate, result);
    }

    @Test
    void findById_ThrowsException_WhenCandidateIsNotFound() {
        // given
        var candidateId = "nonExistentId";
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        // when / then
        assertThrows(NoSuchElementException.class, () -> candidateService.findById(candidateId));
    }
}
