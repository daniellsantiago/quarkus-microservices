package infrastructure.repository.entity;

import domain.Election;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "elections")
public class ElectionEntity {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static ElectionEntity fromDomain(Election domain) {
        var entity = new ElectionEntity();

        entity.setId(domain.id());

        return entity;
    }
}
