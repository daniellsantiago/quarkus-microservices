package api.dto.out;

import domain.Candidate;
import java.util.Optional;

public record CandidatePersonalInfo(
    String fullName,
    String email,
    Optional<String> phone
) {
    public static CandidatePersonalInfo fromDomain(Candidate candidate) {
        return new CandidatePersonalInfo(
            candidate.givenName() + " " + candidate.familyName(),
            candidate.email(),
            candidate.phone()
        );
    }
}
