package infrastructure.resource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import api.dto.out.CandidatePersonalInfo;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import java.util.Arrays;
import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(CandidateResource.class)
class CandidateResourceTest {
    @InjectMock
    CandidateApi api;

    @Test
    void create() {
        var in = Instancio.create(CreateCandidate.class);

        given().contentType(MediaType.APPLICATION_JSON).body(in)
          .when().post()
          .then().statusCode(StatusCode.CREATED);

        verify(api).create(in);
    }

    @Test
    void list() {
        var out = Instancio.stream(CandidatePersonalInfo.class).limit(4).toList();

        when(api.findCandidatesPersonalInfo()).thenReturn(out);

        var response = given()
          .when().get()
          .then().statusCode(RestResponse.StatusCode.OK).extract().as(CandidatePersonalInfo[].class);

        verify(api).findCandidatesPersonalInfo();
        verifyNoMoreInteractions(api);

        assertEquals(out, Arrays.stream(response).toList());
    }
}
