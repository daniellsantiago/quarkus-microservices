package infrastructure.lifecycle;

import infrastructure.repository.RedisElectionRepository;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;


@Startup
@ApplicationScoped
public class Subscribe {
    private static final Logger LOGGER = Logger.getLogger(Subscribe.class);

    public Subscribe(ReactiveRedisDataSource dataSource,
                     RedisElectionRepository repository) {
        LOGGER.info("Startup: Subscribe");

        dataSource.pubsub(String.class)
                  .subscribe("elections")
                  .emitOn(Infrastructure.getDefaultWorkerPool())
                  .subscribe()
                  .with(id -> {
                      LOGGER.info("Election " + id + " received from subscription");
                      LOGGER.info("Election " + repository.findById(id) + " starting");
                  });
    }
}
