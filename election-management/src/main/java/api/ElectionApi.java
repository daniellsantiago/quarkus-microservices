package api;

import api.dto.out.Election;
import domain.ElectionService;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ElectionApi {
    private final ElectionService service;

    public ElectionApi(ElectionService service) {
        this.service = service;
    }

    public void submit() {
        service.submit();
    }

    public List<Election> list() {
        return service.findAll().stream().map(Election::fromDomain).toList();
    }
}
