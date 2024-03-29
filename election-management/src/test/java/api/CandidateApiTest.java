package api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import api.dto.in.CreateCandidate;
import domain.Candidate;
import domain.CandidateService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@QuarkusTest
class CandidateApiTest {
    @Inject
    CandidateApi candidateApi;

    @InjectMock
    CandidateService candidateService;

    @Test
    void create() {
        var dto = Instancio.create(CreateCandidate.class);
        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);

        candidateApi.create(dto);

        verify(candidateService, times(1)).save(captor.capture());
        var savedCandidate = captor.getValue();

        assertEquals(dto.givenName(), savedCandidate.givenName());
        assertEquals(dto.familyName(), savedCandidate.familyName());
        assertEquals(dto.email(), savedCandidate.email());
        assertEquals(dto.phone(), savedCandidate.phone());
        assertEquals(dto.jobTitle(), savedCandidate.jobTitle());
        assertEquals(dto.photo(), savedCandidate.photo());
    }

    @Test
    void findCandidatePersonalInfo() {
        var candidates = Instancio.stream(Candidate.class).limit(2).toList();

        when(candidateService.findAll()).thenReturn(candidates);

        var result = candidateApi.findCandidatesPersonalInfo();

        assertEquals(candidates.size(), result.size());
    }
}
