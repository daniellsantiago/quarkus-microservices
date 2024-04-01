package infrastructure.repository;

import static java.util.stream.Collectors.toMap;

import domain.Election;
import domain.ElectionRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class RedisElectionRepository implements ElectionRepository {
    private final PubSubCommands<String> pubsub;
    private final SortedSetCommands<String, String> commands;

    public RedisElectionRepository(RedisDataSource dataSource) {
        commands = dataSource.sortedSet(String.class, String.class);
        pubsub = dataSource.pubsub(String.class);
    }

    @Override
    public void submit(Election election) {
        Map<String, Double> rank = election.votes()
          .entrySet()
          .stream()
          .collect(toMap(entry -> entry.getKey().id(),
            entry -> entry.getValue().doubleValue()));

        commands.zadd("election:" + election.id(), rank);
        pubsub.publish("elections", election.id());
    }
}
