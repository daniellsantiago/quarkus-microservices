package api.dto.in;

import domain.Candidate;
import java.util.Optional;

public record CreateCandidate(
  String givenName,
  String familyName,
  String email,
  Optional<String> phone,
  Optional<String> jobTitle,
  Optional<String> photo
) {
    public Candidate toDomain() {
        return Candidate.create(givenName(), familyName(), email(), phone(), jobTitle(), photo());
    }
}
