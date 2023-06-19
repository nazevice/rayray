package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.Semester;
import de.digitra.uniplaner.repository.SemesterRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static de.digitra.uniplaner.acceptancetests.UniplanerGlobalAcceptanceTest.printJsonFromObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class SemesterAcceptanceTestSteps extends CucumberSpringContextConfiguration {
    @Autowired
    private TestRestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(SemesterAcceptanceTestSteps.class);
    private final String ENTITY_NAME = "Semester";

    @LocalServerPort
    private int port;



    @Autowired
    private SemesterRepository semesterRepository;

    ResponseEntity<Semester> semesterResponse=null;
    ResponseEntity<Semester> updatedSemesterResponse =null;
    Semester semester = null;
    List<Semester> semesters = null;
    RestClient restClient = null;
    private final Long NUMBER_DEFAULT_VALUE = 17L;
	private final Long NUMBER_UPDATE_VALUE = 111L;
	private final Long number = NUMBER_DEFAULT_VALUE;
	private final LocalDate END_DATE_DEFAULT_VALUE = LocalDate.now();
	private final LocalDate END_DATE_UPDATE_VALUE = LocalDate.now().plusDays(11);
	private final LocalDate endDate = END_DATE_DEFAULT_VALUE;
	private final String NAME_DEFAULT_VALUE = "NAME_DEFAULT_VALUE";
	private final String NAME_UPDATE_VALUE = "NAME_UPDATE_VALUE";
	private final String name = NAME_DEFAULT_VALUE;
	private final LocalDate START_DATE_DEFAULT_VALUE = LocalDate.now();
	private final LocalDate START_DATE_UPDATE_VALUE = LocalDate.now().plusDays(11);
	private final LocalDate startDate = START_DATE_DEFAULT_VALUE;
	


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
        semesterRepository.deleteAll();
        restClient = new RestClient(restTemplate,port);
    }

    //this method executes before every step
    @BeforeStep
    public void beforeStep() {
        if(restClient == null){
            restClient = new RestClient(restTemplate,port);
        }
    }
    @Given("a new Semester instance")
    public void aNewSemester() {
        newSemester();
        semester.setNumber(NUMBER_DEFAULT_VALUE);
semester.setEndDate(END_DATE_DEFAULT_VALUE);
semester.setName(NAME_DEFAULT_VALUE);
semester.setStartDate(START_DATE_DEFAULT_VALUE);

    }
    @When("the user updates the Semester")
    public void the_user_updates_the_Semester() {
        semester.setNumber(NUMBER_UPDATE_VALUE);
semester.setEndDate(END_DATE_UPDATE_VALUE);
semester.setName(NAME_UPDATE_VALUE);
semester.setStartDate(START_DATE_UPDATE_VALUE);

        updatedSemesterResponse =restClient.updateSemester(semester.getId(),semester);
    }

    @When("the user deletes a Semester with the wrong id")
    public void theUserDeletesASemesterWithTheWrongId() {
        restClient.deleteSemesterById(0L);
    }
    @Then("the system should return status code resource not found for the requested Semester")
    public void theSystemShouldReturnWrongIdForSemester() {
        assertThat(semesterResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Then("the system should return for the Semester status code resource not found")
    public void the_system_should_return_for_the_Semester_status_code_resource_not_found() {
        assertTrue((semesterResponse.getStatusCode() == HttpStatus.NOT_FOUND)
                || (semesterResponse.getStatusCode() == HttpStatus.BAD_REQUEST));
    }
    @When("the user requests the Semester with a wrong id")
    public void theUserRequestsTheSemesterWithAWrongId() {
        semesterResponse =restClient.getSemesterById(0L);
    }

    @When("the user requests the list of Semester instances")
    public void theUserCallsTheSemesters() throws JsonProcessingException {
        semesters = restClient.getSemesters();
        printJsonFromObject(semesters);
    }

    @Then("the result list contains one Semester instance")
    public void theResultListWillContainOneSemester() {
        assertThat(semesters.size()).isGreaterThan(0);
    }

    private void newSemester() {

        semester = new Semester();

    }

    @Given("the user saves the Semester instance in the system")
    public void the_user_saves_the_Semester_instance_in_the_system()  {
        semesterResponse = restClient.createSemester(semester);
        semester = semesterResponse.getBody();
    }

    @Given("a Semester in the system")
    public void a_Semester_in_the_system() {
        newSemester();
        the_user_saves_the_Semester_instance_in_the_system();
    }

    @When("the user requests the Semester with a correct id")
    public void the_user_requests_the_Semester_with_a_correct_id() {
        semesterResponse = restClient.getSemesterById(semester.getId());
    }
    @When("the user requests the Semester instance")
    public void theUserRequestsTheSemester() {
        semesterResponse = restClient.getSemesterById(semester.getId());
    }
    @Then("the system should return the updated Semester")
    public void the_system_should_return_the_updated_Semester() {
            assertThat(updatedSemesterResponse.getBody().getNumber()).isEqualTo(NUMBER_UPDATE_VALUE);
            assertThat(updatedSemesterResponse.getBody().getStartDate()).isEqualTo(START_DATE_UPDATE_VALUE);
            assertThat(updatedSemesterResponse.getBody().getName()).isEqualTo(NAME_UPDATE_VALUE);
            assertThat(updatedSemesterResponse.getBody().getEndDate()).isEqualTo(END_DATE_UPDATE_VALUE);
    
    }
    @Then("the result list contains the requested Semester")
    public void the_result_list_contains_the_requested_Semester() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    @Then("the system returns the new Semester instance")
    public void theNewResultDateWillBeInTheListOfAllSemesters() {
        ResponseEntity<Semester> fromSystem = restClient.getSemesterById(semester.getId());
        assertThat(fromSystem).isNotNull();
        assertThat(fromSystem.getBody().getId())
                .isEqualTo(semester.getId());

    }
    @Then("the system should return the requested Semester")
    public void theSystemShouldReturnTheRequestedSemester() {
        assertThat(semester.getId()).isEqualTo(semesterResponse.getBody().getId());
    }

    @When("the user deletes the Semester")
    public void theUserDeletesTheSemester() {
        restClient.deleteSemesterById(semester.getId());
    }

    @Then("the Semester will not be contained in the list of Semester instances of the system")
    public void theSemesterWillNotBeContainedInTheListOfSemestersOfTheSystem() {
        semesters = restClient.getSemesters();
        List<Semester> result = semesters.stream()
                .filter(e->e.getId() == semesterResponse.getBody().getId())
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


}