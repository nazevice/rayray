package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.interfaces.ILectureController;
import de.digitra.uniplaner.service.LectureService;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("/lectures")
public class LectureController implements ILectureController {

    private final Logger log = LoggerFactory.getLogger(LectureController.class);
    private final String ENTITY_NAME = "Lecture";

    @Autowired
    private LectureService lectureService;

    public LectureController() {}


     /**
     * {@code POST  /lectures} : Methode erstellt eine Ressource vom Typ Lecture.
     *
     * @param lecture Instanz von Lecture, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls lecture nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
                 @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von Lecture zurückgeliefert."),
                 @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls der übergebene Parameter lecture nicht zulässig ist. Dies ist der Fall, falls er bereits eine Id hat, die nicht null ist.")
         })
    @PostMapping
    public ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) throws BadRequestException {
        log.debug("REST request to save Lecture : {}", lecture);
        if (lecture.getId() != null) {
            String message = "A new lecture cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }
        Lecture result = lectureService.save(lecture);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /lectures} : aktualisiert eine existierende Ressource vom Typ Lecture.
     *
     * @param lecture Instanz von Lecture, die am Server aktualisiert werden soll.
     * Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls lecture nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von Lecture zurückgeliefert."),
         @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls falls der übergebene Parameter lecture nicht zulässig ist. Dies ist der Fall, falls er eine Id mit dem Wert null hat."),
         @ApiResponse(responseCode = "500", description = "Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.")

     })
    @PutMapping
    public ResponseEntity<Lecture> updateLecture(@RequestBody Lecture lecture) throws  BadRequestException {
        log.debug("REST request to update Lecture : {}", lecture);
        if (lecture.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        Lecture result = lectureService.save(lecture);
        return ResponseEntity.ok(result);
    }

  /**
     * {@code PUT  /lectures/:id} : aktualisiert eine existierende Ressource vom Typ Lecture.
     *
     * @param id Id der Ressource vom Typ Lecture, die am Server aktualisiert werden soll.
     * @param lectureDetails Instanz von Lecture, die am Server aktualisiert werden soll.
     * Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
     @Override
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die aktualisierte Ressource zurückgeliefert."),
         @ApiResponse(responseCode = "404", description = "Status Code {@code 404 (Not Found)} wird zurückgeliefert, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.")
     })

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecture lectureDetails) throws ResourceNotFoundException {
        Optional<Lecture> lecture = lectureService.findOne(id);
        Lecture result = null;
        if(lecture.isPresent()) {
            result = lectureService.save(lectureDetails);
        }
        else{
            throw new ResourceNotFoundException("Lecture mit dieser Id nicht gefunden: " + id);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET  /lectures} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ Lecture.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ Lecture.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Lecture>> getAlllectures() {
        log.debug("REST request to get all lectures");
        List<Lecture> result = lectureService.findAll();
        return ResponseEntity.ok(result);
        //return ResponseEntity.ok(lectureService.findAll());
    }

    /**
     * {@code GET  /lectures/:id} : Liefert die Ressource vom Typ Lecture mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ Lecture, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ Lecture.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
      @Override
      @ApiResponses(value = {
              @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ Lecture zurückgeliefert."),
              @ApiResponse(responseCode = "404", description = "Status Code {@code 404 (Not Found)} wird zurückgeliefert, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.")
          })
    @GetMapping("/{id}")

    public ResponseEntity<Lecture> getLecture(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get lecture : {}", id);
        Optional<Lecture> lecture = lectureService.findOne(id);
        if(lecture.isEmpty()){
            throw new ResourceNotFoundException("lecture mit dieser Id nicht gefunden: " + id);
        }
        return ResponseEntity.ok(lecture.get());


    }
        /**
         * {@code DELETE  /lectures/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
         *
         * @param id Die Id der Ressource vom Typ Lecture, die gelöscht werden soll.
         * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
         */
        @Override
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
            log.debug("REST request to delete lecture : {}", id);
            lectureService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
