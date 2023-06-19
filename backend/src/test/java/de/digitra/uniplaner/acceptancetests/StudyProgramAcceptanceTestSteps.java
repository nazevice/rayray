package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.repository.StudyProgramRepository;
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

import java.util.List;
import java.util.stream.Collectors;

import static de.digitra.uniplaner.acceptancetests.UniplanerGlobalAcceptanceTest.printJsonFromObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class StudyProgramAcceptanceTestSteps extends CucumberSpringContextConfiguration {
    @Autowired
    private TestRestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(StudyProgramAcceptanceTestSteps.class);
    private final String ENTITY_NAME = "StudyProgram";

    @LocalServerPort
    private int port;



    @Autowired
    private StudyProgramRepository studyprogramRepository;

    ResponseEntity<StudyProgram> studyprogramResponse=null;
    ResponseEntity<StudyProgram> updatedStudyProgramResponse =null;
    StudyProgram studyprogram = null;
    List<StudyProgram> studyprograms = null;
    RestClient restClient = null;
    private final String NAME_DEFAULT_VALUE = "NAME_DEFAULT_VALUE";
    private final String NAME_UPDATE_VALUE = "NAME_UPDATE_VALUE";
    private final String name = NAME_DEFAULT_VALUE;
    private final String SHORT_NAME_DEFAULT_VALUE = "SHORT_NAME_DEFAULT_VALUE";
    private final String SHORT_NAME_UPDATE_VALUE = "SHORT_NAME_UPDATE_VALUE";
    private final String shortName = SHORT_NAME_DEFAULT_VALUE;



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
        studyprogramRepository.deleteAll();
        restClient = new RestClient(restTemplate,port);
    }

    //this method executes before every step
    @BeforeStep
    public void beforeStep() {
        if(restClient == null){
            restClient = new RestClient(restTemplate,port);
        }
    }
    @Given("a new Study Program instance")
    public void aNewStudyProgram() {
        newStudyProgram();
        studyprogram.setName(NAME_DEFAULT_VALUE);
        studyprogram.setShortName(SHORT_NAME_DEFAULT_VALUE);

    }
    @When("the user updates the Study Program")
    public void the_user_updates_the_Study_Program() {
        studyprogram.setName(NAME_UPDATE_VALUE);
        studyprogram.setShortName(SHORT_NAME_UPDATE_VALUE);

        updatedStudyProgramResponse =restClient.updateStudyProgram(studyprogram.getId(),studyprogram);
    }

    @When("the user deletes a Study Program with the wrong id")
    public void theUserDeletesAStudyProgramWithTheWrongId() {
        restClient.deleteStudyProgramById(0L);
    }
    @Then("the system should return status code resource not found")
    public void theSystemShouldReturnWrongId() {
        assertThat(studyprogramResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Then("the system should return for the Study Program status code resource not found")
    public void the_system_should_return_for_the_Study_Program_status_code_resource_not_found() {
        assertTrue((studyprogramResponse.getStatusCode() == HttpStatus.NOT_FOUND)
                || (studyprogramResponse.getStatusCode() == HttpStatus.BAD_REQUEST));
    }
    @When("the user requests the Study Program with a wrong id")
    public void theUserRequestsTheStudyProgramWithAWrongId() {
        studyprogramResponse =restClient.getStudyProgramById(0L);
    }

    @When("the user requests the list of Study Program instances")
    public void theUserCallsTheStudyPrograms() throws JsonProcessingException {
        studyprograms = restClient.getStudyPrograms();
        printJsonFromObject(studyprograms);
    }

    @Then("the result list contains one Study Program instance")
    public void theResultListWillContainOneStudyProgram() {
        assertThat(studyprograms.size()).isGreaterThan(0);
    }

    private void newStudyProgram() {

        studyprogram = new StudyProgram();

    }

    @Given("the user saves the Study Program instance in the system")
    public void the_user_saves_the_Study_Program_instance_in_the_system()  {
        studyprogramResponse = restClient.createStudyProgram(studyprogram);
        studyprogram = studyprogramResponse.getBody();
    }

    @Given("a Study Program in the system")
    public void a_Study_Program_in_the_system() {
        newStudyProgram();
        the_user_saves_the_Study_Program_instance_in_the_system();
    }

    @When("the user requests the Study Program with a correct id")
    public void the_user_requests_the_Study_Program_with_a_correct_id() {
        studyprogramResponse = restClient.getStudyProgramById(studyprogram.getId());
    }
    @When("the user requests the Study Program instance")
    public void theUserRequestsTheStudyProgram() {
        studyprogramResponse = restClient.getStudyProgramById(studyprogram.getId());
    }
    @Then("the system should return the updated Study Program")
    public void the_system_should_return_the_updated_Study_Program() {
        assertThat(updatedStudyProgramResponse.getBody().getName()).isEqualTo(NAME_UPDATE_VALUE);
        assertThat(updatedStudyProgramResponse.getBody().getShortName()).isEqualTo(SHORT_NAME_UPDATE_VALUE);

    }
    @Then("the result list contains the requested Study Program")
    public void the_result_list_contains_the_requested_Study_Program() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    @Then("the system returns the new Study Program instance")
    public void theNewResultDateWillBeInTheListOfAllStudyPrograms() {
        ResponseEntity<StudyProgram> fromSystem = restClient.getStudyProgramById(studyprogram.getId());
        assertThat(fromSystem).isNotNull();
        assertThat(fromSystem.getBody().getId())
                .isEqualTo(studyprogram.getId());

    }
    @Then("the system should return the requested Study Program")
    public void theSystemShouldReturnTheRequestedStudyProgram() {
        assertThat(studyprogram.getId()).isEqualTo(studyprogramResponse.getBody().getId());
    }

    @When("the user deletes the Study Program")
    public void theUserDeletesTheStudyProgram() {
        restClient.deleteStudyProgramById(studyprogram.getId());
    }

    @Then("the Study Program will not be contained in the list of Study Program instances of the system")
    public void theStudyProgramWillNotBeContainedInTheListOfStudyProgramsOfTheSystem() {
        studyprograms = restClient.getStudyPrograms();
        List<StudyProgram> result = studyprograms.stream()
                .filter(e->e.getId() == studyprogramResponse.getBody().getId())
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


}