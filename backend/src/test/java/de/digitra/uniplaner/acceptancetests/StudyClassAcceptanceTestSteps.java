package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.repository.StudyClassRepository;
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
public class StudyClassAcceptanceTestSteps extends CucumberSpringContextConfiguration {
    @Autowired
    private TestRestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(StudyClassAcceptanceTestSteps.class);
    private final String ENTITY_NAME = "StudyClass";

    @LocalServerPort
    private int port;



    @Autowired
    private StudyClassRepository studyclassRepository;

    ResponseEntity<StudyClass> studyclassResponse=null;
    ResponseEntity<StudyClass> updatedStudyClassResponse =null;
    StudyClass studyclass = null;
    List<StudyClass> studyclasss = null;
    RestClient restClient = null;
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
        studyclassRepository.deleteAll();
        restClient = new RestClient(restTemplate,port);
    }

    //this method executes before every step
    @BeforeStep
    public void beforeStep() {
        if(restClient == null){
            restClient = new RestClient(restTemplate,port);
        }
    }
    @Given("a new Study Class instance")
    public void aNewStudyClass() {
        newStudyClass();
        studyclass.setEndDate(END_DATE_DEFAULT_VALUE);
studyclass.setName(NAME_DEFAULT_VALUE);
studyclass.setStartDate(START_DATE_DEFAULT_VALUE);

    }
    @When("the user updates the Study Class")
    public void the_user_updates_the_Study_Class() {
        studyclass.setEndDate(END_DATE_UPDATE_VALUE);
studyclass.setName(NAME_UPDATE_VALUE);
studyclass.setStartDate(START_DATE_UPDATE_VALUE);

        updatedStudyClassResponse =restClient.updateStudyClass(studyclass.getId(),studyclass);
    }

    @When("the user deletes a Study Class with the wrong id")
    public void theUserDeletesAStudyClassWithTheWrongId() {
        restClient.deleteStudyClassById(0L);
    }
    @Then("the system should return status code resource not found for the requested Study Class")
    public void theSystemShouldReturnWrongIdForStudyClass() {
        assertThat(studyclassResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Then("the system should return for the Study Class status code resource not found")
    public void the_system_should_return_for_the_Study_Class_status_code_resource_not_found() {
        assertTrue((studyclassResponse.getStatusCode() == HttpStatus.NOT_FOUND)
                || (studyclassResponse.getStatusCode() == HttpStatus.BAD_REQUEST));
    }
    @When("the user requests the Study Class with a wrong id")
    public void theUserRequestsTheStudyClassWithAWrongId() {
        studyclassResponse =restClient.getStudyClassById(0L);
    }

    @When("the user requests the list of Study Class instances")
    public void theUserCallsTheStudyClasss() throws JsonProcessingException {
        studyclasss = restClient.getStudyClasss();
        printJsonFromObject(studyclasss);
    }

    @Then("the result list contains one Study Class instance")
    public void theResultListWillContainOneStudyClass() {
        assertThat(studyclasss.size()).isGreaterThan(0);
    }

    private void newStudyClass() {

        studyclass = new StudyClass();

    }

    @Given("the user saves the Study Class instance in the system")
    public void the_user_saves_the_Study_Class_instance_in_the_system()  {
        studyclassResponse = restClient.createStudyClass(studyclass);
        studyclass = studyclassResponse.getBody();
    }

    @Given("a Study Class in the system")
    public void a_Study_Class_in_the_system() {
                aNewStudyClass();
        the_user_saves_the_Study_Class_instance_in_the_system();
    }

    @When("the user requests the Study Class with a correct id")
    public void the_user_requests_the_Study_Class_with_a_correct_id() {
        studyclassResponse = restClient.getStudyClassById(studyclass.getId());
    }
    @When("the user requests the Study Class instance")
    public void theUserRequestsTheStudyClass() {
        studyclassResponse = restClient.getStudyClassById(studyclass.getId());
    }
    @Then("the system should return the updated Study Class")
    public void the_system_should_return_the_updated_Study_Class() {
            assertThat(updatedStudyClassResponse.getBody().getStartDate()).isEqualTo(START_DATE_UPDATE_VALUE);
            assertThat(updatedStudyClassResponse.getBody().getName()).isEqualTo(NAME_UPDATE_VALUE);
            assertThat(updatedStudyClassResponse.getBody().getEndDate()).isEqualTo(END_DATE_UPDATE_VALUE);
    
    }
    @Then("the result list contains the requested Study Class")
    public void the_result_list_contains_the_requested_Study_Class() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    @Then("the system returns the new Study Class instance")
    public void theNewResultDateWillBeInTheListOfAllStudyClasss() {
        ResponseEntity<StudyClass> fromSystem = restClient.getStudyClassById(studyclass.getId());
        assertThat(fromSystem).isNotNull();
        assertThat(fromSystem.getBody().getId())
                .isEqualTo(studyclass.getId());

    }
    @Then("the system should return the requested Study Class")
    public void theSystemShouldReturnTheRequestedStudyClass() {
        assertThat(studyclass.getId()).isEqualTo(studyclassResponse.getBody().getId());
    }

    @When("the user deletes the Study Class")
    public void theUserDeletesTheStudyClass() {
        restClient.deleteStudyClassById(studyclass.getId());
    }

    @Then("the Study Class will not be contained in the list of Study Class instances of the system")
    public void theStudyClassWillNotBeContainedInTheListOfStudyClasssOfTheSystem() {
        studyclasss = restClient.getStudyClasss();
        List<StudyClass> result = studyclasss.stream()
                .filter(e->e.getId() == studyclassResponse.getBody().getId())
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


}