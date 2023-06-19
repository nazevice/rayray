package de.digitra.uniplaner.acceptancetests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.repository.LectureRepository;
import de.digitra.uniplaner.repository.StudyProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.DAYS;


@Transactional
public class UniplanerGlobalAcceptanceTest extends CucumberSpringContextConfiguration {

    private static final ZonedDateTime DATE_BEFORE_NOW = ZonedDateTime.now().minus(1, DAYS);
    private static final ZonedDateTime DATE_NOW = ZonedDateTime.now();
    private static final ZonedDateTime DATE_AFTER_NOW = ZonedDateTime.now().plus(1, DAYS);
    private final LocalDateTime DEFAULT_START_TIME = LocalDateTime.now();
    private final LocalDateTime UPDATE_START_TIME = LocalDateTime.now().plusHours(1);
    private final LocalDateTime DEFAULT_END_TIME = LocalDateTime.now();
    private final LocalDateTime UPDATE_END_TIME = LocalDateTime.now().plusHours(1);


    @Autowired
    private TestRestTemplate restTemplate;




    private final Logger log = LoggerFactory.getLogger(UniplanerGlobalAcceptanceTest.class);


    private final long DEFAULT_SEMESTER_NUMBER = 2;
    private final String DEFAULT_MODULE_NAME ="DEFAULT MODULE NAME" ;
    private final String DEFAULT_STUDY_PROGRAM_NAME ="DEFAULT STUDY PROGRAM NAME" ;

    @LocalServerPort
    private int port;

    @Autowired
    private LectureRepository lectureRepo;
    @Autowired
    private StudyProgramRepository studyProgramRepo;;

    Lecture lecture = null;
    int oldCount =0;
    StudyProgram studyProgram;



/*
    @Given("new lecture with a standard name")
    public void new_lecture_with_a_standard_name() {
        lecture = new Lecture();
        lecture.setLectureName("LOGIK");
        lecture.setModulName(DEFAULT_MODULE_NAME);
    }

    @When("the new lecture is passed to create it")
    public void the_new_lecture_is_passed_to_create_it() {
        lecture = lectureRepo.save(lecture);
        System.out.println(lecture);

    }

    @Then("a new lecture will be created")
    public void a_new_lecture_will_be_created() {
        assertThat(lecture.getId()).isNotNull();
    }
    @Given("lecture in the system")
    public void lecture_in_the_system() {
        lecture = new Lecture();
        lecture.setLectureName("LOGIK");
        lecture.setModulName(DEFAULT_MODULE_NAME);
        lecture = lectureRepo.save(lecture);
    }
    @When("the lecture is deleted")
    public void the_lecture_is_deleted() {
        oldCount = lectureRepo.findAll().size();
        System.out.println("old count="+oldCount);
        System.out.println("lecture="+lecture);

        lectureRepo.deleteById(lecture.getId());
    }
    @Then("the lecture will be not found in the system")
    public void the_lecture_will_be_not_found_in_the_system() {
        Optional<Lecture> lectureOpt = lectureRepo.findById(lecture.getId());
        assertThat(lectureOpt.isEmpty()).isTrue();

    }
    @Then("the lecture will not be contained in the list of lectures of the study program")
    public void the_lecture_will_not_be_contained_in_the_list_of_lectures_of_the_study_program() {
        System.out.println(studyProgram.getLectures().size());
        studyProgram.getLectures().forEach(e->System.out.println(e));
        List<Lecture> result = studyProgram.getLectures()
                .stream()
                .filter(e -> e.getLectureName().equals(lecture.getLectureName()))
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


    private final String DEFAULT_STUDY_CLASS_NAME ="WWI_INF2022" ;
    StudyClass studyClass = null;
    @Given("new study class with a standard name")
    public void new_study_class_with_a_standard_name() {
        studyClass = new StudyClass();
        studyClass.setName(DEFAULT_STUDY_CLASS_NAME);

    }
    @When("the new study class is added to the study program")
    public void the_new_study_class_is_added_to_the_study_program() throws JsonProcessingException {
        studyProgram.addStudyClass(studyClass);
        studyProgram = studyProgramRepo.save(studyProgram);
        System.out.println("####################");
        System.out.println("Printing study program after adding study class....");
        printJsonFromObject(studyProgram);
        System.out.println("####################");
    }
    @Then("a new study class will be contained in the list of study classes of the study program")
    public void a_new_study_class_will_be_contained_in_the_list_of_study_classes_of_the_study_program() throws JsonProcessingException {
        System.out.println(studyProgram.getStudyClasses().size());
        assertThat(studyProgram.getStudyClasses().size()).isGreaterThan(0);
        studyProgram.getStudyClasses().forEach(e->System.out.println(e));

        List<StudyClass> result = studyProgram.getStudyClasses()
                .stream()
                .filter(e -> e.getName().equals(studyClass.getName()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(1);
    }


    Semester semester = null;
    private final String DEFAULT_SEMESTER_NAME ="1-6 SEMESTER" ;
    @Autowired
    private StudyClassRepository studyClassRepo;

    @Autowired
    private SemesterRepository semesterRepo;

    @Given("a study class in the system")
    public void a_study_class_in_the_system() {
        new_study_class_with_a_standard_name();
        studyClass = studyClassRepo.save(studyClass);

    }
    @When("the study class is added to the study program")
    public void the_study_class_is_added_to_the_study_program() throws JsonProcessingException {
        printJsonFromObject(studyClass);
        studyProgram.addStudyClass(studyClass);
        studyProgram = studyProgramRepo.save(studyProgram);
        printJsonFromObject(studyProgram);
    }
    @When("the new study class is removed from the study program")
    public void the_new_study_class_is_removed_from_the_study_program() {
        studyProgram.removeStudyClass(studyClass);
        studyProgram = studyProgramRepo.save(studyProgram);
    }

    @When("a new semester is added to the study class")
    public void a_new_semester_is_added_to_the_study_class() {
        semester = new Semester();
        semester.setName(DEFAULT_SEMESTER_NAME);
        semester.setNumber(DEFAULT_SEMESTER_NUMBER);
        studyClass.addSemester(semester);
        studyClass = studyClassRepo.save(studyClass);
        semester = studyClass.getSemesters().stream().collect(Collectors.toList()).get(0);
    }
    @Then("the new study semester will be contained in the list of semesters of the the study class")
    public void the_new_study_semester_will_be_contained_in_the_list_of_semesters_of_the_the_study_class() throws JsonProcessingException {
        System.out.println(studyClass.getSemesters().size());
        List<Semester> result = studyClass.getSemesters()
                .stream()
                .filter(e -> e.getName().equals(semester.getName()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isNotNull();
    }
    @Then("the new study class will not be contained in the list of study classes of the study program")
    public void the_new_study_class_will_not_be_contained_in_the_list_of_study_classes_of_the_study_program() throws JsonProcessingException {


        studyProgram.getStudyClasses().forEach(e->System.out.println(e));

        List<StudyClass> result = studyProgram.getStudyClasses()
                .stream()
                .filter(e -> e.getName().equals(studyClass.getName()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(0);
    }

    @Given("a semester in the list of semesters of the study class")
    public void a_semester_in_the_list_of_semesters_of_the_study_class() {
        a_new_semester_is_added_to_the_study_class();
    }
    @When("the semester is removed from the study class")
    public void the_semester_is_removed_from_the_study_class() {
        studyClass.removeSemester(semester);
        studyClass = studyClassRepo.save(studyClass);
    }

    @Then("the semester will not be contained in the list of semesters of the the study class")
    public void the_semester_will_not_be_contained_in_the_list_of_semesters_of_the_the_study_class() throws JsonProcessingException {
        studyClass.getSemesters().forEach(e->System.out.println(e));

        List<Semester> result = studyClass.getSemesters()
                .stream()
                .filter(e -> e.getName().equals(semester.getName()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(0);

    }

    @Autowired
    private LectureDateRepository lectureDateRepo;
    ResponseEntity<LectureDate> lectureDateResponse=null;
    ResponseEntity<LectureDate> updatedLectureResponse =null;
    LectureDate lectureDate = null;
    List<LectureDate> lectureDates = null;
    RestClient restClient = null;


    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
    }


    @AfterStep
    public void afterStep() {

    }

    public void before() {
        lectureDateRepo.deleteAll();
        restClient = new RestClient(restTemplate,port);
    }


    @BeforeStep
    public void beforeStep() {
        if(restClient == null){
            restClient = new RestClient(restTemplate,port);
        }
    }
    @Given("a new lecture date instance")
    public void aNewLectureDate() {
        newLectureDate();
    }
    @When("the user updates the lecture date")
    public void the_user_updates_the_lecture_date() {
        lectureDate.setStartDate(UPDATE_START_TIME);
        updatedLectureResponse =restClient.updateLectureDate(lectureDate.getId(),lectureDate);
    }

    @When("the user deletes a lecture date with the wrong id")
    public void theUserDeletesALectureDateWithTheWrongId() {
        restClient.deleteLectureDateById(0L);
    }

    @Then("the system should return status code resource not found for the lecture date")
    public void theSystemShouldReturnWrongId() {
        assertThat(lectureDateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Then("the system should return for the lecture date status code resource not found")
    public void the_system_should_return_for_the_lecture_date_status_code_resource_not_found() {
        assertTrue((lectureDateResponse.getStatusCode() == HttpStatus.NOT_FOUND)
                || (lectureDateResponse.getStatusCode() == HttpStatus.BAD_REQUEST));
    }
    @When("the user requests the lecture date with a wrong id")
    public void theUserRequestsTheLectureDateWithAWrongId() {
        lectureDateResponse =restClient.getLectureDateById(0L);
    }
    @When("the user requests the list of lecture dates")
    public void theUserCallsTheLectureDates() throws JsonProcessingException {
        lectureDates = restClient.getLectureDates();
        printJsonFromObject(lectureDates);
    }

    @Then("the result list contains one lecture date")
    public void theResultListWillContainOneLectureDate() {
        assertThat(lectureDates.size()).isGreaterThan(0);
    }


    private void newLectureDate() {
        lectureDate = new LectureDate();
        lectureDate.setStartDate(DEFAULT_START_TIME);
    }

    @Given("the user saves the lecture date instance in the system")
    public void the_user_saves_the_lecture_date_instance_in_the_system()  {
        lectureDateResponse = restClient.createLectureDate(lectureDate);
        lectureDate = lectureDateResponse.getBody();
    }

    @Given("a lecture date in the system")
    public void a_lecture_date_in_the_system() {
        newLectureDate();
        the_user_saves_the_lecture_date_instance_in_the_system();
    }

    @When("the user requests the lecture date with a correct id")
    public void the_user_requests_the_lecture_date_with_a_correct_id() {
        lectureDateResponse = restClient.getLectureDateById(lectureDate.getId());
    }
    @When("the user requests the lecture date")
    public void theUserRequestsTheLectureDate() {
        lectureDateResponse = restClient.getLectureDateById(lectureDate.getId());
    }
    @Then("the system should return the updated lecture date")
    public void the_system_should_return_the_updated_lecture_date() {
        assertThat(updatedLectureResponse.getBody().getStartDate()).isEqualTo(UPDATE_START_TIME);
    }


    @Then("the system returns the new lecture date")
    public void theNewResultDateWillBeInTheListOfAllLectureDates() {
        ResponseEntity<LectureDate> fromSystem = restClient.getLectureDateById(lectureDate.getId());
        assertThat(fromSystem).isNotNull();
        assertThat(fromSystem.getBody().getId())
                .isEqualTo(lectureDate.getId());

    }
    @Then("the system should return the requested lecture date")
    public void theSystemShouldReturnTheRequestedLectureDate() {
        assertThat(lectureDate.getId()).isEqualTo(lectureDateResponse.getBody().getId());
    }

    @When("the user deletes the lecture date")
    public void theUserDeletesTheLectureDate() {
        restClient.deleteLectureDateById(lectureDate.getId());
    }

    @Then("the lecture date will not be contained in the list of lecture dates of the system")
    public void theLectureDateWillNotBeContainedInTheListOfLectureDatesOfTheSystem() {
        lectureDates = restClient.getLectureDates();
        List<LectureDate> result = lectureDates.stream()
                .filter(e->e.getId() == lectureDateResponse.getBody().getId())
                .collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


    @When("the lecture date is added to semester")
    public void the_lecture_date_is_added_to_semester() {
        semester.addLectureDate(lectureDate);
        semester = semesterRepo.save(semester);


    }
    @Then("the lecture date will be contained in the list of lecture dates of the semester")
    public void the_lecture_date_will_be_contained_in_the_list_of_lecture_dates_of_the_semester() throws JsonProcessingException {
        semester.getLectureDates().forEach(e->System.out.println(e));

        List<LectureDate> result = semester.getLectureDates()
                .stream()
                .filter(e -> e.getStartDate().equals(lectureDate.getStartDate()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(1);

    }
    @And("a lecture date in the system in the list of lecture dates of the semester")
    public void aLectureDateInTheSystemInTheListOfLectureDatesOfTheSemester() {
        a_lecture_date_in_the_system();
        the_lecture_date_is_added_to_semester();
    }

    @When("the lecture date is removed from the semester")
    public void theLectureDateIsRemovedFromTheSemester() throws JsonProcessingException {
        printJsonFromObject(lectureDate);
        semester.removeLectureDate(lectureDate);
        semesterRepo.save(semester);
    }
    @Then("the lecture date will not be contained in the list of lecture dates of the semester")
    public void theLectureDateWillNotBeContainedInTheListOfLectureDatesOfTheSemester() throws JsonProcessingException {
        semester.getLectureDates().forEach(e->System.out.println(e));

        List<LectureDate> result = semester.getLectureDates()
                .stream()
                .filter(e -> e.getId().equals(lectureDate.getId()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(0);
    }

    @Autowired
    private LecturerRepository lecturerRepo;

    Lecturer lecturer = null;

    private LectureDate createLectureDate(Lecture lecture,LocalDateTime startTime, LocalDateTime endTime, Lecturer lecturer){
        LectureDate lectureDate = new LectureDate();
        lectureDate.setLecture(lecture);
        lectureDate.setLecturer(lecturer);
        lectureDate.setStartDate(startTime);
        lectureDate.setEndDate(endTime);
        lectureDate = lectureDateRepo.save(lectureDate);
        return lectureDate;
    }



    List<LectureDate> lectureDatesForLecturer = null;
    @When("the lecture dates for the lecturer are searched")
    public void theLectureDatesForTheLecturerAreSearched() {
        lectureDatesForLecturer = lectureDateRepo.findByLecturerId(lecturer.getId());

    }

    @Then("the result list will contain three lecture dates")
    public void theResultListWillContainThreeLectureDates() {

        assertThat(lectureDatesForLecturer.size()).isEqualTo(3);
    }




    ResponseEntity<Lecturer> lecturerResponse=null;



    @Given("a lecture in the system")
    public void aLectureInTheSystem() {

        lecture_in_the_system();
    }

    @When("a lecturer is created")
    public void aLecturerIsCreated() {
        lecturer = new Lecturer();
        lecturer.setFirstName("Max");
        lecturer.setLastName("Müller");
        lecturer.setEmail("mail@mail.de");
        lecturer = lecturerRepo.save(lecturer);

    }
    @Then("a new lecturer will be available in the system")
    public void aNewLecturerWillBeAvailableInTheSystem() throws JsonProcessingException {

        List<Lecturer> result = lecturerRepo.findAll()
                .stream()
                .filter(e -> e.getLastName().equals(lecturer.getLastName()))
                .collect(Collectors.toList());

        assertThat(result.size()).isGreaterThan(0);

    }

    @When("the lecturer is assigned to the lecture")
    public void theLecturerIsAssignedToTheLecture() {
        lecture.addLecturer(lecturer);
        lecture = lectureRepo.save(lecture);
    }

    @Then("the lecturer will be contained in the list of lecturer of the lecture")
    @Transactional
    public void theLectureWillBeContainedInTheListOfLecturerForTheLecture() throws JsonProcessingException {

        List<Lecture> result= lecturer.getLectures()
                .stream()
                .filter(e -> e.getLectureName().equals(lecture.getLectureName()))
                .collect(Collectors.toList());

        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(1);
    }


    @And("a lecturer in the system")
    public void aLecturerInTheSystem() {
        aLecturerIsCreated();
    }

    @When("the lecturer is added to the study program")
    public void theLecturerIsAddedToTheStudyProgram() {
        studyProgram.addLecturer(lecturer);
    }

    @Then("the new lecturer will be contained in the list of lecturers of the study program")
    public void aNewLecturerWillBeContainedInTheListOfLecturersOfTheStudyProgram() throws JsonProcessingException {
        List<Lecturer> result = studyProgram.getLecturers()
                .stream()
                .filter(e -> e.getLastName().equals(lecturer.getLastName()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(1);
    }

    @When("the lecturer is removed from the study program")
    public void theLecturerIsRemovedFromTheStudyProgram() {
        studyProgram.removeLecturer(lecturer);
        studyProgram = studyProgramRepo.save(studyProgram);
    }

    @Then("the lecturer will not be contained in the list of lecturers of the study program")
    public void theLecturerWillNotBeContainedInTheListOfLecturersOfTheStudyProgram() throws JsonProcessingException {
        List<Lecturer> result = studyProgram.getLecturers()
                .stream()
                .filter(e -> e.getLastName().equals(lecturer.getLastName()))
                .collect(Collectors.toList());
        printJsonFromObject(result);
        assertThat(result.size()).isEqualTo(0);
    }


    public static void printJsonFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        mapper.setDateFormat(df);
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        System.out.println(jsonInString);
    }






    @Given("a new lecturer instance")
    public void aNewLecturerInstance() {
        lecturer = new Lecturer();
        lecturer.setFirstName("Max");
        lecturer.setLastName("Müller");
        lecturer.setEmail("mail@mail.de");
    }

    @Given("a new lecturer instance with an already used email")
    public void aNewLecturerInstanceWithAnAlreadyUsedEmail() {
        aNewLecturerInstance();
        lecturerRepo.save(lecturer);
        Lecturer newLecturer = new Lecturer();
        newLecturer.setFirstName("NEW_FIRSTNAME");
        newLecturer.setLastName("NEW_LASTNAME");
        newLecturer.setEmail("mail@mail.de");
        lecturerResponse = restClient.createLecturer(newLecturer);

    }
    @Then("the system returns status code bad request")
    public void the_system_returns_status_code_bad_request() {
        assertThat(lecturerResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }*/
public static void printJsonFromObject(Object object) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    mapper.setDateFormat(df);
    String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    System.out.println(jsonInString);
}
}
