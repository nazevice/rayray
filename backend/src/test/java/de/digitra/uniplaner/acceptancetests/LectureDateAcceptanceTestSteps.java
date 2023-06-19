package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.repository.LectureDateRepository;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.PendingException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static de.digitra.uniplaner.acceptancetests.UniplanerGlobalAcceptanceTest.printJsonFromObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class LectureDateAcceptanceTestSteps extends CucumberSpringContextConfiguration {
    @Autowired
    private TestRestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(LectureDateAcceptanceTestSteps.class);
    private final String ENTITY_NAME = "LectureDate";

    @LocalServerPort
    private int port;



    @Autowired
    private LectureDateRepository lecturedateRepository;

    ResponseEntity<LectureDate> lecturedateResponse=null;
    ResponseEntity<LectureDate> updatedLectureDateResponse =null;
    LectureDate lecturedate = null;
    List<LectureDate> lecturedates = null;
    RestClient restClient = null;
    private final LocalDateTime END_DATE_DEFAULT_VALUE = LocalDateTime.now();
	private final LocalDateTime END_DATE_UPDATE_VALUE = LocalDateTime.now().plusDays(1);
	private final LocalDateTime endDate = END_DATE_DEFAULT_VALUE;
	private final LocalDateTime START_DATE_DEFAULT_VALUE = LocalDateTime.now();
	private final LocalDateTime START_DATE_UPDATE_VALUE = LocalDateTime.now().plusDays(1);
	private final LocalDateTime startDate = START_DATE_DEFAULT_VALUE;
	

    //this method executes after every scenario
    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
    }

    //this method executes after every step
    @AfterStep
    public void afterStep() {
        //log.info(">>> AfterStep!");
        //placeholder for after step logic
    }
    //this method executes before every scenario
    public void before() {
        lecturedateRepository.deleteAll();
        restClient = new RestClient(restTemplate,port);
    }

    //this method executes before every step
    @BeforeStep
    public void beforeStep() {
        if(restClient == null){
            restClient = new RestClient(restTemplate,port);
        }
    }
    @Given("a new Lecture Date instance")
    public void aNewLectureDate() {
        newLectureDate();
        lecturedate.setEndDate(END_DATE_DEFAULT_VALUE);
        lecturedate.setStartDate(START_DATE_DEFAULT_VALUE);

    }
    @When("the user updates the Lecture Date")
    public void the_user_updates_the_Lecture_Date() {
        lecturedate.setEndDate(END_DATE_UPDATE_VALUE);
        lecturedate.setStartDate(START_DATE_UPDATE_VALUE);

        updatedLectureDateResponse =restClient.updateLectureDate(lecturedate.getId(),lecturedate);
    }

    @When("the user deletes a Lecture Date with the wrong id")
    public void theUserDeletesALectureDateWithTheWrongId() {
        restClient.deleteLectureDateById(0L);
    }
    @Then("the system should return status code resource not found for the requested Lecture Date")
    public void theSystemShouldReturnWrongIdForLectureDate() {
        assertThat(lecturedateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Then("the system should return for the Lecture Date status code resource not found")
    public void the_system_should_return_for_the_Lecture_Date_status_code_resource_not_found() {
        assertTrue((lecturedateResponse.getStatusCode() == HttpStatus.NOT_FOUND)
                || (lecturedateResponse.getStatusCode() == HttpStatus.BAD_REQUEST));
    }
    @When("the user requests the Lecture Date with a wrong id")
    public void theUserRequestsTheLectureDateWithAWrongId() {
        lecturedateResponse =restClient.getLectureDateById(0L);
    }

    @When("the user requests the list of Lecture Date instances")
    public void theUserCallsTheLectureDates() throws JsonProcessingException {
        lecturedates = restClient.getLectureDates();
        printJsonFromObject(lecturedates);
    }

    @Then("the result list contains one Lecture Date instance")
    public void theResultListWillContainOneLectureDate() {
        assertThat(lecturedates.size()).isGreaterThan(0);
    }

    private void newLectureDate() {

        lecturedate = new LectureDate();

    }

    @Given("the user saves the Lecture Date instance in the system")
    public void the_user_saves_the_Lecture_Date_instance_in_the_system()  {
        lecturedateResponse = restClient.createLectureDate(lecturedate);
        lecturedate = lecturedateResponse.getBody();
    }

    @Given("a Lecture Date in the system")
    public void a_Lecture_Date_in_the_system() {
        aNewLectureDate();
        the_user_saves_the_Lecture_Date_instance_in_the_system();
    }

    @When("the user requests the Lecture Date with a correct id")
    public void the_user_requests_the_Lecture_Date_with_a_correct_id() {
        lecturedateResponse = restClient.getLectureDateById(lecturedate.getId());
    }
    @When("the user requests the Lecture Date instance")
    public void theUserRequestsTheLectureDate() {
        lecturedateResponse = restClient.getLectureDateById(lecturedate.getId());
    }
    @Then("the system should return the updated Lecture Date")
    public void the_system_should_return_the_updated_Lecture_Date() {
        assertThat(updatedLectureDateResponse.getBody().getStartDate()).isEqualTo(START_DATE_UPDATE_VALUE);
        assertThat(updatedLectureDateResponse.getBody().getEndDate()).isEqualTo(END_DATE_UPDATE_VALUE);

    }
    @Then("the result list contains the requested Lecture Date")
    public void the_result_list_contains_the_requested_Lecture_Date() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    @Then("the system returns the new Lecture Date instance")
    public void theNewResultDateWillBeInTheListOfAllLectureDates() {
        ResponseEntity<LectureDate> fromSystem = restClient.getLectureDateById(lecturedate.getId());
        assertThat(fromSystem).isNotNull();
        assertThat(fromSystem.getBody().getId())
                .isEqualTo(lecturedate.getId());

    }
    @Then("the system should return the requested Lecture Date")
    public void theSystemShouldReturnTheRequestedLectureDate() {
        assertThat(lecturedate.getId()).isEqualTo(lecturedateResponse.getBody().getId());
    }

    @When("the user deletes the Lecture Date")
    public void theUserDeletesTheLectureDate() {
        restClient.deleteLectureDateById(lecturedate.getId());
    }

    @Then("the Lecture Date will not be contained in the list of Lecture Date instances of the system")
    public void theLectureDateWillNotBeContainedInTheListOfLectureDatesOfTheSystem() {
        lecturedates = restClient.getLectureDates();
        List<LectureDate> result = lecturedates.stream()
                .filter(e->e.getId() == lecturedateResponse.getBody().getId())
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }
}