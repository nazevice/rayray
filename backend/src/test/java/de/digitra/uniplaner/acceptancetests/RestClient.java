package de.digitra.uniplaner.acceptancetests;

import de.digitra.uniplaner.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class RestClient {

    private final String SERVER_URL = "http://localhost";

    private final String LECTURE_RESOURCE = "lectures";
    private final String LECTURE_DATE_RESOURCE = "lecturedates";
    private final String LECTURER_RESOURCE = "lecturers";
    private final String SEMESTER_RESOURCE = "semesters";
    private final String STUDY_CLASS_RESOURCE = "studyclasses";
    private final String STUDY_PROGRAM_RESOURCE = "studyprograms";




    private TestRestTemplate restTemplate;
    private int port;


    public RestClient(TestRestTemplate restTemplate, int port){
        this.restTemplate = restTemplate;
        this.port = port;
    }
    private final Logger log = LoggerFactory.getLogger(RestClient.class);


    private String getResourceEndpoint(String resource) {
        String endPoint = "";
        if (resource.equals(LECTURE_RESOURCE)) {
            endPoint = SERVER_URL + ":" + port + "/" + LECTURE_RESOURCE;
        }
        if (resource.equals(LECTURE_DATE_RESOURCE)) {
            endPoint = SERVER_URL + ":" + port + "/" + LECTURE_DATE_RESOURCE;
        }
        if (resource.equals(LECTURER_RESOURCE)) {
            endPoint = SERVER_URL + ":" + port + "/" + LECTURER_RESOURCE;
        }
        if (resource.equals(SEMESTER_RESOURCE)) {
            endPoint = SERVER_URL + ":" + port + "/" + SEMESTER_RESOURCE;
        }
        if (resource.equals(STUDY_CLASS_RESOURCE)) {
            endPoint = SERVER_URL + ":" + port + "/" + STUDY_CLASS_RESOURCE;
        }
        if (resource.equals(STUDY_PROGRAM_RESOURCE)) {
            endPoint = SERVER_URL + ":" + port + "/" + STUDY_PROGRAM_RESOURCE;
        }
        return endPoint;
    }

    private String getResourceEndpointForId(String resource, Long id) {
        String endPoint = null;
        if (resource.equals(LECTURE_RESOURCE)) {
            endPoint = getResourceEndpoint(LECTURE_RESOURCE) + "/"+id;
        }
        if (resource.equals(LECTURE_DATE_RESOURCE)) {
            endPoint = getResourceEndpoint(LECTURE_DATE_RESOURCE) + "/"+id;
        }
        if (resource.equals(LECTURER_RESOURCE)) {
            endPoint = getResourceEndpoint(LECTURER_RESOURCE) + "/"+id;
        }
        if (resource.equals(SEMESTER_RESOURCE)) {
            endPoint = getResourceEndpoint(SEMESTER_RESOURCE) + "/"+id;
        }
        if (resource.equals(STUDY_CLASS_RESOURCE)) {
            endPoint = getResourceEndpoint(STUDY_CLASS_RESOURCE) + "/"+id;
        }
        if (resource.equals(STUDY_PROGRAM_RESOURCE)) {
            endPoint = getResourceEndpoint(STUDY_PROGRAM_RESOURCE) + "/"+id;
        }
        return endPoint;

    }

    public List<Lecture> getLectures(){

        String uri = getResourceEndpoint(LECTURE_RESOURCE );
        ResponseEntity<Lecture[]> lectures = restTemplate.getForEntity(uri , Lecture[].class);
        return Arrays.stream(lectures.getBody()).collect(Collectors.toList());
    }
    public List<LectureDate> getLectureDates(){

        String uri = getResourceEndpoint(LECTURE_DATE_RESOURCE );
        ResponseEntity<LectureDate[]> lectureDates = restTemplate.getForEntity(uri , LectureDate[].class);
        return Arrays.stream(lectureDates.getBody()).collect(Collectors.toList());
    }
    public List<Lecturer> getLecturers(){

        String uri = getResourceEndpoint(LECTURER_RESOURCE );
        ResponseEntity<Lecturer[]> lecturers = restTemplate.getForEntity(uri , Lecturer[].class);
        return Arrays.stream(lecturers.getBody()).collect(Collectors.toList());
    }
    public List<Semester> getSemesters(){

        String uri = getResourceEndpoint(SEMESTER_RESOURCE );
        ResponseEntity<Semester[]> semesters = restTemplate.getForEntity(uri , Semester[].class);
        return Arrays.stream(semesters.getBody()).collect(Collectors.toList());
    }
    public List<StudyClass> getStudyClasss(){

        String uri = getResourceEndpoint(STUDY_CLASS_RESOURCE );
        ResponseEntity<StudyClass[]> studyClasss = restTemplate.getForEntity(uri , StudyClass[].class);
        return Arrays.stream(studyClasss.getBody()).collect(Collectors.toList());
    }
    public List<StudyProgram> getStudyPrograms(){

        String uri = getResourceEndpoint(STUDY_PROGRAM_RESOURCE );
        ResponseEntity<StudyProgram[]> studyPrograms = restTemplate.getForEntity(uri , StudyProgram[].class);
        return Arrays.stream(studyPrograms.getBody()).collect(Collectors.toList());
    }

    public ResponseEntity<Lecture> createLecture(Lecture lecture) {
        String uri = getResourceEndpoint(LECTURE_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Lecture> requestEntity = new HttpEntity<>(lecture, headers);
        ResponseEntity<Lecture> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Lecture.class);
        return response;
    }
    public ResponseEntity<LectureDate> createLectureDate(LectureDate lectureDate) {
        String uri = getResourceEndpoint(LECTURE_DATE_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LectureDate> requestEntity = new HttpEntity<>(lectureDate, headers);
        ResponseEntity<LectureDate> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, LectureDate.class);
        return response;
    }
    public ResponseEntity<Lecturer> createLecturer(Lecturer lecturer) {
        String uri = getResourceEndpoint(LECTURER_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Lecturer> requestEntity = new HttpEntity<>(lecturer, headers);
        ResponseEntity<Lecturer> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Lecturer.class);
        return response;
    }
    public ResponseEntity<Semester> createSemester(Semester semester) {
        String uri = getResourceEndpoint(SEMESTER_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Semester> requestEntity = new HttpEntity<>(semester, headers);
        ResponseEntity<Semester> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Semester.class);
        return response;
    }
    public ResponseEntity<StudyClass> createStudyClass(StudyClass studyClass) {
        String uri = getResourceEndpoint(STUDY_CLASS_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<StudyClass> requestEntity = new HttpEntity<>(studyClass, headers);
        ResponseEntity<StudyClass> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, StudyClass.class);
        return response;
    }
    public ResponseEntity<StudyProgram> createStudyProgram(StudyProgram studyProgram) {
        String uri = getResourceEndpoint(STUDY_PROGRAM_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<StudyProgram> requestEntity = new HttpEntity<>(studyProgram, headers);
        ResponseEntity<StudyProgram> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, StudyProgram.class);
        return response;
    }

    public ResponseEntity<Lecture> getLectureById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_RESOURCE, id);
        ResponseEntity<Lecture> response = restTemplate.getForEntity(uri, Lecture.class);
        return response;
    }
    public ResponseEntity<LectureDate> getLectureDateById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        ResponseEntity<LectureDate> response = restTemplate.getForEntity(uri, LectureDate.class);
        return response;
    }
    public ResponseEntity<Lecturer> getLecturerById(Long id) {
        String uri = getResourceEndpointForId(LECTURER_RESOURCE, id);
        ResponseEntity<Lecturer> response = restTemplate.getForEntity(uri, Lecturer.class);
        return response;
    }
    public ResponseEntity<Semester> getSemesterById(Long id) {
        String uri = getResourceEndpointForId(SEMESTER_RESOURCE, id);
        ResponseEntity<Semester> response = restTemplate.getForEntity(uri, Semester.class);
        return response;
    }
    public ResponseEntity<StudyClass> getStudyClassById(Long id) {
        String uri = getResourceEndpointForId(STUDY_CLASS_RESOURCE, id);
        ResponseEntity<StudyClass> response = restTemplate.getForEntity(uri, StudyClass.class);
        return response;
    }
    public ResponseEntity<StudyProgram> getStudyProgramById(Long id) {
        String uri = getResourceEndpointForId(STUDY_PROGRAM_RESOURCE, id);
        ResponseEntity<StudyProgram> response = restTemplate.getForEntity(uri, StudyProgram.class);
        return response;
    }
    public void deleteLectureById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_RESOURCE, id);
        restTemplate.delete(uri);
    }
    public void deleteLectureDateById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        restTemplate.delete(uri);
    }
    public void deleteLecturerById(Long id) {
        String uri = getResourceEndpointForId(LECTURER_RESOURCE, id);
        restTemplate.delete(uri);
    }
    public void deleteSemesterById(Long id) {
        String uri = getResourceEndpointForId(SEMESTER_RESOURCE, id);
        restTemplate.delete(uri);
    }
    public void deleteStudyClassById(Long id) {
        String uri = getResourceEndpointForId(STUDY_CLASS_RESOURCE, id);
        restTemplate.delete(uri);
    }
    public void deleteStudyProgramById(Long id) {
        String uri = getResourceEndpointForId(STUDY_PROGRAM_RESOURCE, id);
        restTemplate.delete(uri);
    }
    public ResponseEntity<Lecture> updateLecture(Long id, Lecture lecture) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Lecture> requestEntity = new HttpEntity<>(lecture, headers);
        String uri = this.getResourceEndpointForId(LECTURE_RESOURCE, id);
        ResponseEntity<Lecture> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Lecture.class);
        return response;

    }
    public ResponseEntity<LectureDate> updateLectureDate(Long id, LectureDate lectureDate) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LectureDate> requestEntity = new HttpEntity<>(lectureDate, headers);
        String uri = this.getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        ResponseEntity<LectureDate> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, LectureDate.class);
        return response;

    }
    public ResponseEntity<Lecturer> updateLecturer(Long id, Lecturer lecturer) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Lecturer> requestEntity = new HttpEntity<>(lecturer, headers);
        String uri = this.getResourceEndpointForId(LECTURER_RESOURCE, id);
        ResponseEntity<Lecturer> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Lecturer.class);
        return response;

    }
    public ResponseEntity<Semester> updateSemester(Long id, Semester semester) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Semester> requestEntity = new HttpEntity<>(semester, headers);
        String uri = this.getResourceEndpointForId(SEMESTER_RESOURCE, id);
        ResponseEntity<Semester> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Semester.class);
        return response;

    }
    public ResponseEntity<StudyClass> updateStudyClass(Long id, StudyClass studyClass) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<StudyClass> requestEntity = new HttpEntity<>(studyClass, headers);
        String uri = this.getResourceEndpointForId(STUDY_CLASS_RESOURCE, id);
        ResponseEntity<StudyClass> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, StudyClass.class);
        return response;

    }
    public ResponseEntity<StudyProgram> updateStudyProgram(Long id, StudyProgram studyProgram) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<StudyProgram> requestEntity = new HttpEntity<>(studyProgram, headers);
        String uri = this.getResourceEndpointForId(STUDY_PROGRAM_RESOURCE, id);
        ResponseEntity<StudyProgram> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, StudyProgram.class);
        return response;

    }
}





























/*package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.domain.Lecturer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class RestClient {


    private final String SERVER_URL = "http://localhost";
    private final String LECTURE_DATE_RESOURCE = "lecturedates";
    private static final String LECTURER_RESOURCE = "lecturers";;

    private TestRestTemplate restTemplate;

    private int port;

    public RestClient(TestRestTemplate restTemplate, int port){
        this.restTemplate = restTemplate;
        this.port = port;
    }

    private String getResourceEndpointForId(String resource, Long id) {
        String entityUrl = "";
        if (resource.equals(LECTURE_DATE_RESOURCE)) {
            entityUrl = getResourceEndpoint(LECTURE_DATE_RESOURCE) + "/"+id;
        }
        return entityUrl;
    }
    private String getResourceEndpoint(String resource) {
        String endPoint = "";
        if (resource.equals(LECTURE_DATE_RESOURCE)) {
            endPoint= SERVER_URL + ":" + port + "/"+LECTURE_DATE_RESOURCE;
        }
        if (resource.equals(LECTURER_RESOURCE)) {
            endPoint= SERVER_URL + ":" + port + "/"+LECTURER_RESOURCE;
        }
        return endPoint;
    }

    public List<LectureDate> getLectureDates(){
        String uri = getResourceEndpoint(LECTURE_DATE_RESOURCE);
        ResponseEntity<LectureDate[]> lectureDates = restTemplate.getForEntity(uri , LectureDate[].class);
        return Arrays.stream(lectureDates.getBody()).collect(Collectors.toList());
    }
    public ResponseEntity<LectureDate> createLectureDate(LectureDate lectureDate) {
        String uri = getResourceEndpoint(LECTURE_DATE_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LectureDate> requestEntity = new HttpEntity<>(lectureDate, headers);
        ResponseEntity<LectureDate> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, LectureDate.class);
        return response;
    }

    public ResponseEntity<LectureDate> getLectureDateById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        ResponseEntity<LectureDate> response = restTemplate.getForEntity(uri, LectureDate.class);
        return response;
    }

    public void deleteLectureDateById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        restTemplate.delete(uri);
    }

    public ResponseEntity<LectureDate> updateLectureDate(Long id, LectureDate lectureDate) {

        //lectureDate.setStartDate(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LectureDate> requestEntity = new HttpEntity<>(lectureDate, headers);
        String uri = this.getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        ResponseEntity<LectureDate> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, LectureDate.class);
        return response;

    }

    public ResponseEntity<Lecturer> createLecturer(Lecturer lecturer) {
        String uri = getResourceEndpoint(LECTURER_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Lecturer> requestEntity = new HttpEntity<>(lecturer, headers);
        ResponseEntity<Lecturer> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Lecturer.class);

        return response;
    }


}*/
