package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.interfaces.IStudyProgramController;
import de.digitra.uniplaner.service.StudyProgramService;
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


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/studyprograms")
public class StudyProgramController implements IStudyProgramController {

    private final Logger log = LoggerFactory.getLogger(StudyProgramController.class);
    private final String ENTITY_NAME = "StudyProgram";

    private final StudyProgramService studyprogramService;

    public StudyProgramController(StudyProgramService studyprogramService) {
        this.studyprogramService = studyprogramService;
    }


     /**
     * {@code POST  /studyprograms} : Methode erstellt eine Ressource vom Typ StudyProgram.
     *
     * @param studyprogram Instanz von StudyProgram, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls studyprogram nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
                 @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von StudyProgram zurückgeliefert."),
                 @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls der übergebene Parameter studyprogram nicht zulässig ist. Dies ist der Fall, falls er bereits eine Id hat, die nicht null ist.")
         })
    @PostMapping
    public ResponseEntity<StudyProgram> createStudyProgram(@RequestBody StudyProgram studyprogram) throws BadRequestException {
        log.debug("REST request to save StudyProgram : {}", studyprogram);
        if (studyprogram.getId() != null) {

            String message = "A new studyprogram cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);

        }
        StudyProgram result = studyprogramService.save(studyprogram);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /studyprograms} : aktualisiert eine existierende Ressource vom Typ StudyProgram.
     *
     * @param studyprogram Instanz von StudyProgram, die am Server aktualisiert werden soll.
     * Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls studyprogram nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von StudyProgram zurückgeliefert."),
         @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls falls der übergebene Parameter studyprogram nicht zulässig ist. Dies ist der Fall, falls er eine Id mit dem Wert null hat."),
         @ApiResponse(responseCode = "500", description = "Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.")

     })
    @PutMapping
    public ResponseEntity<StudyProgram> updateStudyProgram(@RequestBody StudyProgram studyprogram) throws  BadRequestException {
        log.debug("REST request to update StudyProgram : {}", studyprogram);
        if (studyprogram.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }
         log.info("REST request to update StudyProgram : {}", studyprogram);

        StudyProgram result = studyprogramService.save(studyprogram);
        return ResponseEntity.ok()
            .body(result);
    }

  /**
     * {@code PUT  /studyprograms/:id} : aktualisiert eine existierende Ressource vom Typ StudyProgram.
     *
     * @param id Id der Ressource vom Typ StudyProgram, die am Server aktualisiert werden soll.
     * @param studyprogramDetails Instanz von StudyProgram, die am Server aktualisiert werden soll.
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
    public ResponseEntity<StudyProgram> updateStudyProgram(@PathVariable(value = "id") Long id, @Valid @RequestBody StudyProgram studyprogramDetails) throws ResourceNotFoundException {
        Optional<StudyProgram> studyprogram = studyprogramService.findOne(id);
        StudyProgram result = null;
        if(studyprogram.isPresent()) {
            result = studyprogramService.save(studyprogramDetails);
        }
        else{
            throw new ResourceNotFoundException("StudyProgram mit dieser Id nicht gefunden: " + id);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET  /studyprograms} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ StudyProgram.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ StudyProgram.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<StudyProgram>> getAllstudyprograms() {
        log.debug("REST request to get all studyprograms");
        return ResponseEntity.ok(studyprogramService.findAll());
    }

    /**
     * {@code GET  /studyprograms/:id} : Liefert die Ressource vom Typ StudyProgram mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ StudyProgram, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ StudyProgram.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
      @Override
      @ApiResponses(value = {
              @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ StudyProgram zurückgeliefert."),
              @ApiResponse(responseCode = "404", description = "Status Code {@code 404 (Not Found)} wird zurückgeliefert, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.")
          })
    @GetMapping("/{id}")
    public ResponseEntity<StudyProgram> getStudyProgram(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get studyprogram : {}", id);
        Optional<StudyProgram> studyprogram = studyprogramService.findOne(id);
        if(studyprogram.isPresent()) {
            return ResponseEntity.ok(studyprogram.get());
        }
        else{
            throw new ResourceNotFoundException("studyprogram mit dieser Id nicht gefunden: " + id);
        }

    }
        /**
         * {@code DELETE  /studyprograms/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
         *
         * @param id Die Id der Ressource vom Typ StudyProgram, die gelöscht werden soll.
         * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
         */
        @Override
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteStudyProgram(@PathVariable Long id) {
            log.debug("REST request to delete studyprogram : {}", id);
            studyprogramService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
