package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.interfaces.IStudyClassController;
import de.digitra.uniplaner.service.StudyClassService;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/studyclasses")
public class StudyClassController implements IStudyClassController {

    private final Logger log = LoggerFactory.getLogger(StudyClassController.class);
    private final String ENTITY_NAME = "StudyClass";

    private final StudyClassService studyclassService;

    public StudyClassController(StudyClassService studyclassService) {
        this.studyclassService = studyclassService;
    }


     /**
     * {@code POST  /studyclasses} : Methode erstellt eine Ressource vom Typ StudyClass.
     *
     * @param studyclass Instanz von StudyClass, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls studyclass nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
                 @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von StudyClass zurückgeliefert."),
                 @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls der übergebene Parameter studyclass nicht zulässig ist. Dies ist der Fall, falls er bereits eine Id hat, die nicht null ist.")
         })
    @PostMapping
    public ResponseEntity<StudyClass> createStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException {
        log.debug("REST request to save StudyClass : {}", studyclass);
        if (studyclass.getId() != null) {

            String message = "A new studyclass cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);

        }
        StudyClass result = studyclassService.save(studyclass);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /studyclasses} : aktualisiert eine existierende Ressource vom Typ StudyClass.
     *
     * @param studyclass Instanz von StudyClass, die am Server aktualisiert werden soll.
     * Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls studyclass nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von StudyClass zurückgeliefert."),
         @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls falls der übergebene Parameter studyclass nicht zulässig ist. Dies ist der Fall, falls er eine Id mit dem Wert null hat."),
         @ApiResponse(responseCode = "500", description = "Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.")

     })
    @PutMapping
    public ResponseEntity<StudyClass> updateStudyClass(@RequestBody StudyClass studyclass) throws  BadRequestException {
        log.debug("REST request to update StudyClass : {}", studyclass);
        if (studyclass.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        StudyClass result = studyclassService.save(studyclass);
        return ResponseEntity.ok()
            .body(result);
    }

  /**
     * {@code PUT  /studyclasses/:id} : aktualisiert eine existierende Ressource vom Typ StudyClass.
     *
     * @param id Id der Ressource vom Typ StudyClass, die am Server aktualisiert werden soll.
     * @param studyclassDetails Instanz von StudyClass, die am Server aktualisiert werden soll.
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
    public ResponseEntity<StudyClass> updateStudyClass(@PathVariable(value = "id") Long id, @Valid @RequestBody StudyClass studyclassDetails) throws ResourceNotFoundException {
        Optional<StudyClass> studyclass = studyclassService.findOne(id);
        StudyClass result = null;
        if(studyclass.isPresent()) {
            result = studyclassService.save(studyclassDetails);
        }
        else{
            throw new ResourceNotFoundException("StudyClass mit dieser Id nicht gefunden: " + id);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET  /studyclasses} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ StudyClass.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ StudyClass.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<StudyClass>> getAllstudyclasss() {
        log.debug("REST request to get all studyclasses");
        return ResponseEntity.ok(studyclassService.findAll());
    }

    /**
     * {@code GET  /studyclasses/:id} : Liefert die Ressource vom Typ StudyClass mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ StudyClass, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ StudyClass.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
      @Override
      @ApiResponses(value = {
              @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ StudyClass zurückgeliefert."),
              @ApiResponse(responseCode = "404", description = "Status Code {@code 404 (Not Found)} wird zurückgeliefert, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.")
          })
    @GetMapping("/{id}")
    public ResponseEntity<StudyClass> getStudyClass(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get studyclass : {}", id);
        Optional<StudyClass> studyclass = studyclassService.findOne(id);
        if(studyclass.isPresent()) {
            return ResponseEntity.ok(studyclass.get());
        }
        else{
            throw new ResourceNotFoundException("studyclass mit dieser Id nicht gefunden: " + id);
        }

    }
        /**
         * {@code DELETE  /studyclasses/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
         *
         * @param id Die Id der Ressource vom Typ StudyClass, die gelöscht werden soll.
         * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
         */
        @Override
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteStudyClass(@PathVariable Long id) {
            log.debug("REST request to delete studyclass : {}", id);
            studyclassService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
