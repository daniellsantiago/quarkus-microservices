package domain;

import java.util.Optional;

public record Candidate(
  String id,
  String givenName,
  String familyName,
  String email,
  Optional<String> phone,
  Optional<String> jobTitle
) {
}
