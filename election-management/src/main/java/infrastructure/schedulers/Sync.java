package infrastructure.schedulers;

import infrastructure.repository.RedisElectionRepository;
import infrastructure.repository.SQLElectionRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Sync {
    private final SQLElectionRepository sqlRepository;
    private final RedisElectionRepository redisRepository;

    public Sync(SQLElectionRepository sqlRepository, RedisElectionRepository redisRepository) {
        this.sqlRepository = sqlRepository;
        this.redisRepository = redisRepository;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    void syncWorker() {
        sqlRepository.findAll().forEach(election -> sqlRepository.sync(redisRepository.sync(election)));
    }
}
