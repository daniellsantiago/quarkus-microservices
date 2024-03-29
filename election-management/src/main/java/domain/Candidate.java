package domain;

import java.util.Optional;
import java.util.UUID;

public record Candidate(
  String id,
  String givenName,
  String familyName,
  String email,
  Optional<String> phone,
  Optional<String> jobTitle,
  Optional<String> photo
) {
    public static Candidate create(String givenName, String familyName, String email,
      Optional<String> phone, Optional<String> jobTitle, Optional<String> photo) {
        return new Candidate(UUID.randomUUID().toString(), givenName, familyName, email, phone, jobTitle, photo);
    }
}
