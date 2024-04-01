package infrastructure.resource;

import static io.restassured.RestAssured.given;

import api.dto.in.CreateCandidate;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import jakarta.ws.rs.core.MediaType;
import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
@TestHTTPEndpoint(CandidateResource.class)
class CandidateResourceIT {

    @Test
    void create() {
        var in = Instancio.create(CreateCandidate.class);

        given().contentType(MediaType.APPLICATION_JSON).body(in)
          .when().post()
          .then().statusCode(RestResponse.StatusCode.CREATED);
    }
}
