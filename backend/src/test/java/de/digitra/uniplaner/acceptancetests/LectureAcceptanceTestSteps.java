package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.repository.LectureRepository;
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
public class LectureAcceptanceTestSteps extends CucumberSpringContextConfiguration {
    @Autowired
    private TestRestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(LectureAcceptanceTestSteps.class);
    private final String ENTITY_NAME = "Lecture";

    @LocalServerPort
    private int port;



    @Autowired
    private LectureRepository lectureRepository;

    ResponseEntity<Lecture> lectureResponse=null;
    ResponseEntity<Lecture> updatedLectureResponse =null;
    Lecture lecture = null;
    List<Lecture> lectures = null;
    RestClient restClient = null;
    private final Long DURATION_DEFAULT_VALUE = 17L;
	private final Long DURATION_UPDATE_VALUE = 111L;
	private final Long duration = DURATION_DEFAULT_VALUE;
	private final String LECTURE_NAME_DEFAULT_VALUE = "LECTURE_NAME_DEFAULT_VALUE";
	private final String LECTURE_NAME_UPDATE_VALUE = "LECTURE_NAME_UPDATE_VALUE";
	private final String lectureName = LECTURE_NAME_DEFAULT_VALUE;
	private final String MODUL_NAME_DEFAULT_VALUE = "MODUL_NAME_DEFAULT_VALUE";
	private final String MODUL_NAME_UPDATE_VALUE = "MODUL_NAME_UPDATE_VALUE";
	private final String modulName = MODUL_NAME_DEFAULT_VALUE;
	


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
    @Given("a new Lecture instance")
    public void aNewLecture() {
        newLecture();
        lecture.setDuration(DURATION_DEFAULT_VALUE);
lecture.setLectureName(LECTURE_NAME_DEFAULT_VALUE);
lecture.setModulName(MODUL_NAME_DEFAULT_VALUE);

    }
    @When("the user updates the Lecture")
    public void the_user_updates_the_Lecture() {
        lecture.setDuration(DURATION_UPDATE_VALUE);
lecture.setLectureName(LECTURE_NAME_UPDATE_VALUE);
lecture.setModulName(MODUL_NAME_UPDATE_VALUE);

        updatedLectureResponse =restClient.updateLecture(lecture.getId(),lecture);
    }

    @When("the user deletes a Lecture with the wrong id")
    public void theUserDeletesALectureWithTheWrongId() {
        restClient.deleteLectureById(0L);
    }
    @Then("the system should return status code resource not found for the requested Lecture")
    public void theSystemShouldReturnWrongIdForLecture() {
        assertThat(lectureResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Then("the system should return for the Lecture status code resource not found")
    public void the_system_should_return_for_the_Lecture_status_code_resource_not_found() {
        assertTrue((lectureResponse.getStatusCode() == HttpStatus.NOT_FOUND)
                || (lectureResponse.getStatusCode() == HttpStatus.BAD_REQUEST));
    }
    @When("the user requests the Lecture with a wrong id")
    public void theUserRequestsTheLectureWithAWrongId() {
        lectureResponse =restClient.getLectureById(0L);
    }

    @When("the user requests the list of Lecture instances")
    public void theUserCallsTheLectures() throws JsonProcessingException {
        lectures = restClient.getLectures();
        printJsonFromObject(lectures);
    }

    @Then("the result list contains one Lecture instance")
    public void theResultListWillContainOneLecture() {
        assertThat(lectures.size()).isGreaterThan(0);
    }

    private void newLecture() {

        lecture = new Lecture();

    }

    @Given("the user saves the Lecture instance in the system")
    public void the_user_saves_the_Lecture_instance_in_the_system()  {
        lectureResponse = restClient.createLecture(lecture);
        lecture = lectureResponse.getBody();
    }

    @Given("a Lecture in the system")
    public void a_Lecture_in_the_system() {
                aNewLecture();
        the_user_saves_the_Lecture_instance_in_the_system();
    }

    @When("the user requests the Lecture with a correct id")
    public void the_user_requests_the_Lecture_with_a_correct_id() {
        lectureResponse = restClient.getLectureById(lecture.getId());
    }
    @When("the user requests the Lecture instance")
    public void theUserRequestsTheLecture() {
        lectureResponse = restClient.getLectureById(lecture.getId());
    }
    @Then("the system should return the updated Lecture")
    public void the_system_should_return_the_updated_Lecture() {
            assertThat(updatedLectureResponse.getBody().getLectureName()).isEqualTo(LECTURE_NAME_UPDATE_VALUE);
            assertThat(updatedLectureResponse.getBody().getDuration()).isEqualTo(DURATION_UPDATE_VALUE);
            assertThat(updatedLectureResponse.getBody().getModulName()).isEqualTo(MODUL_NAME_UPDATE_VALUE);
    
    }
    @Then("the result list contains the requested Lecture")
    public void the_result_list_contains_the_requested_Lecture() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    @Then("the system returns the new Lecture instance")
    public void theNewResultDateWillBeInTheListOfAllLectures() {
        ResponseEntity<Lecture> fromSystem = restClient.getLectureById(lecture.getId());
        assertThat(fromSystem).isNotNull();
        assertThat(fromSystem.getBody().getId())
                .isEqualTo(lecture.getId());

    }
    @Then("the system should return the requested Lecture")
    public void theSystemShouldReturnTheRequestedLecture() {
        assertThat(lecture.getId()).isEqualTo(lectureResponse.getBody().getId());
    }

    @When("the user deletes the Lecture")
    public void theUserDeletesTheLecture() {
        restClient.deleteLectureById(lecture.getId());
    }

    @Then("the Lecture will not be contained in the list of Lecture instances of the system")
    public void theLectureWillNotBeContainedInTheListOfLecturesOfTheSystem() {
        lectures = restClient.getLectures();
        List<Lecture> result = lectures.stream()
                .filter(e->e.getId() == lectureResponse.getBody().getId())
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


}