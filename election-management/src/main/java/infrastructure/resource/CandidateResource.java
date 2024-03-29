package infrastructure.resource;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/candidates")
public class CandidateResource {
    private final CandidateApi api;

    public CandidateResource(CandidateApi api) {
        this.api = api;
    }

    @POST
    @ResponseStatus(StatusCode.CREATED)
    public void create(CreateCandidate dto) {
        api.create(dto);
    }
}
