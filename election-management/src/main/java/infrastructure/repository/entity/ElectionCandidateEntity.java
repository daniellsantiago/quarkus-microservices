package infrastructure.repository.entity;

import domain.Candidate;
import domain.Election;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "election_candidate")
public class ElectionCandidateEntity {
    @EmbeddedId
    private ElectionCandidateIdEntity id;

    private Integer votes;

    public ElectionCandidateIdEntity getId() {
        return id;
    }

    public void setId(ElectionCandidateIdEntity id) {
        this.id = id;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public static Object fromDomain(Election election, Candidate candidate, Integer votes) {
        var entity = new ElectionCandidateEntity();

        ElectionCandidateIdEntity id = new ElectionCandidateIdEntity();
        id.setElectionId(election.id());
        id.setCandidateId(candidate.id());

        entity.setId(id);
        entity.setVotes(votes);

        return entity;
    }
}
