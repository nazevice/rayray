package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.Lecturer;
import de.digitra.uniplaner.repository.LectureDateRepository;
import de.digitra.uniplaner.repository.LectureRepository;
import de.digitra.uniplaner.repository.LecturerRepository;
import io.cucumber.java.After;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static de.digitra.uniplaner.acceptancetests.UniplanerGlobalAcceptanceTest.printJsonFromObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class LecturerAcceptanceTestSteps extends CucumberSpringContextConfiguration {
    @Autowired
    private TestRestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(LecturerAcceptanceTestSteps.class);
    private final String ENTITY_NAME = "Lecturer";

    @LocalServerPort
    private int port;



    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    LectureDateRepository lectureDateRepository;

    ResponseEntity<Lecturer> lecturerResponse=null;
    ResponseEntity<Lecturer> updatedLecturerResponse =null;
    Lecturer lecturer = null;
    List<Lecturer> lecturers = null;
    RestClient restClient = null;
    private final String FIRST_NAME_DEFAULT_VALUE = "FIRST_NAME_DEFAULT_VALUE";
    private final String FIRST_NAME_UPDATE_VALUE = "FIRST_NAME_UPDATE_VALUE";
    private final String firstName = FIRST_NAME_DEFAULT_VALUE;
    private final String LAST_NAME_DEFAULT_VALUE = "LAST_NAME_DEFAULT_VALUE";
    private final String LAST_NAME_UPDATE_VALUE = "LAST_NAME_UPDATE_VALUE";
    private final String lastName = LAST_NAME_DEFAULT_VALUE;
    private final String EMAIL_DEFAULT_VALUE = "EMAIL_DEFAULT_VALUE";
    private final String EMAIL_UPDATE_VALUE = "EMAIL_UPDATE_VALUE";
    private final String email = EMAIL_DEFAULT_VALUE;



    //this method executes after every scenario
    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
        lectureDateRepository.deleteAll();
        lecturerRepository.deleteAll();
        lectureRepository.deleteAll();
    }


    //this method executes before every scenario
    @Transactional
    public void before() {

        lectureDateRepository.deleteAll();
        lecturerRepository.deleteAll();
        lectureRepository.deleteAll();

        restClient = new RestClient(restTemplate,port);
    }

    //this method executes before every step
    @BeforeStep
    public void beforeStep() {
        if(restClient == null){
            restClient = new RestClient(restTemplate,port);
        }
    }
    @Given("a new Lecturer instance")
    public void aNewLecturer() {
        lecturer = new Lecturer();
        lecturer.setFirstName(FIRST_NAME_DEFAULT_VALUE);
        lecturer.setLastName(LAST_NAME_DEFAULT_VALUE);
        lecturer.setEmail(EMAIL_DEFAULT_VALUE);

    }
    @When("the user updates the Lecturer")
    public void the_user_updates_the_Lecturer() {
        lecturer.setFirstName(FIRST_NAME_UPDATE_VALUE);
        lecturer.setLastName(LAST_NAME_UPDATE_VALUE);
        lecturer.setEmail(EMAIL_UPDATE_VALUE);
        updatedLecturerResponse =restClient.updateLecturer(lecturer.getId(),lecturer);
    }

    @Given("a new lecturer instance with an already used email")
    public void a_new_lecturer_instance_with_an_already_used_email() {
        aNewLecturer();
        the_user_saves_the_Lecturer_instance_in_the_system();
        Lecturer lecturerUsedEmail = new Lecturer();
        lecturerUsedEmail.setFirstName(FIRST_NAME_UPDATE_VALUE);
        lecturerUsedEmail.setLastName(LAST_NAME_UPDATE_VALUE);
        lecturerUsedEmail.setEmail(EMAIL_DEFAULT_VALUE);
        lecturer = lecturerUsedEmail;

    }
    @Then("the system returns status code bad request")
    public void the_system_returns_status_code_bad_request() {
     assertThat(lecturerResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @When("the user deletes a Lecturer with the wrong id")
    public void theUserDeletesALecturerWithTheWrongId() {
        restClient.deleteLecturerById(0L);
    }
    @Then("the system should return status code resource not found for the requested Lecturer")
    public void theSystemShouldReturnWrongIdForLecturer() {
        assertThat(lecturerResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Then("the system should return for the Lecturer status code resource not found")
    public void the_system_should_return_for_the_Lecturer_status_code_resource_not_found() {
        assertTrue((lecturerResponse.getStatusCode() == HttpStatus.NOT_FOUND)
                || (lecturerResponse.getStatusCode() == HttpStatus.BAD_REQUEST));
    }
    @When("the user requests the Lecturer with a wrong id")
    public void theUserRequestsTheLecturerWithAWrongId() {
        lecturerResponse =restClient.getLecturerById(0L);
    }

    @When("the user requests the list of Lecturer instances")
    public void theUserCallsTheLecturers() throws JsonProcessingException {
        lecturers = restClient.getLecturers();
        printJsonFromObject(lecturers);
    }

    @Then("the result list contains one Lecturer instance")
    public void theResultListWillContainOneLecturer() {
        assertThat(lecturers.size()).isGreaterThan(0);
    }


    @Given("the user saves the Lecturer instance in the system")
    public void the_user_saves_the_Lecturer_instance_in_the_system()  {
        lecturerResponse = restClient.createLecturer(lecturer);
        lecturer = lecturerResponse.getBody();
    }

    @Given("a Lecturer in the system")
    public void a_Lecturer_in_the_system() {

        aNewLecturer();
        the_user_saves_the_Lecturer_instance_in_the_system();
    }

    @When("the user requests the Lecturer with a correct id")
    public void the_user_requests_the_Lecturer_with_a_correct_id() {
        lecturerResponse = restClient.getLecturerById(lecturer.getId());
    }
    @When("the user requests the Lecturer instance")
    public void theUserRequestsTheLecturer() {
        lecturerResponse = restClient.getLecturerById(lecturer.getId());
    }
    @Then("the system should return the updated Lecturer")
    public void the_system_should_return_the_updated_Lecturer() {
        assertThat(updatedLecturerResponse.getBody().getEmail()).isEqualTo(EMAIL_UPDATE_VALUE);
        assertThat(updatedLecturerResponse.getBody().getFirstName()).isEqualTo(FIRST_NAME_UPDATE_VALUE);
        assertThat(updatedLecturerResponse.getBody().getLastName()).isEqualTo(LAST_NAME_UPDATE_VALUE);

    }
    @Then("the system returns the new Lecturer instance")
    public void theNewResultDateWillBeInTheListOfAllLecturers() {
        ResponseEntity<Lecturer> fromSystem = restClient.getLecturerById(lecturer.getId());
        assertThat(fromSystem).isNotNull();
        assertThat(fromSystem.getBody().getId())
                .isEqualTo(lecturer.getId());

    }
    @Then("the system should return the requested Lecturer")
    public void theSystemShouldReturnTheRequestedLecturer() {
        assertThat(lecturer.getId()).isEqualTo(lecturerResponse.getBody().getId());
    }

    @When("the user deletes the Lecturer")
    public void theUserDeletesTheLecturer() {
        restClient.deleteLecturerById(lecturer.getId());
    }

    @Then("the Lecturer will not be contained in the list of Lecturer instances of the system")
    public void theLecturerWillNotBeContainedInTheListOfLecturersOfTheSystem() {
        lecturers = restClient.getLecturers();
        List<Lecturer> result = lecturers.stream()
                .filter(e->e.getId() == lecturerResponse.getBody().getId())
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


}