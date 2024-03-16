package domain;

import java.util.Optional;
import java.util.Set;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface CandidateQuery {
    Optional<Set<String>> ids();
    Optional<String> name();
    class Builder extends CandidateQuery_Builder {}
}
