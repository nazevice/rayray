package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Semester;
import de.digitra.uniplaner.interfaces.ISemesterController;
import de.digitra.uniplaner.service.SemesterService;
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
@RequestMapping("/semesters")
public class SemesterController implements ISemesterController {

    private final Logger log = LoggerFactory.getLogger(SemesterController.class);
    private final String ENTITY_NAME = "Semester";

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }


     /**
     * {@code POST  /semesters} : Methode erstellt eine Ressource vom Typ Semester.
     *
     * @param semester Instanz von Semester, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls semester nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
                 @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von Semester zurückgeliefert."),
                 @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls der übergebene Parameter semester nicht zulässig ist. Dies ist der Fall, falls er bereits eine Id hat, die nicht null ist.")
         })
    @PostMapping
    public ResponseEntity<Semester> createSemester(@RequestBody Semester semester) throws BadRequestException {
        log.debug("REST request to save Semester : {}", semester);
        if (semester.getId() != null) {

            String message = "A new semester cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);

        }
        Semester result = semesterService.save(semester);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /semesters} : aktualisiert eine existierende Ressource vom Typ Semester.
     *
     * @param semester Instanz von Semester, die am Server aktualisiert werden soll.
     * Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls semester nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von Semester zurückgeliefert."),
         @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls falls der übergebene Parameter semester nicht zulässig ist. Dies ist der Fall, falls er eine Id mit dem Wert null hat."),
         @ApiResponse(responseCode = "500", description = "Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.")

     })
    @PutMapping
    public ResponseEntity<Semester> updateSemester(@RequestBody Semester semester) throws  BadRequestException {
        log.debug("REST request to update Semester : {}", semester);
        if (semester.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        Semester result = semesterService.save(semester);
        return ResponseEntity.ok()
            .body(result);
    }

  /**
     * {@code PUT  /semesters/:id} : aktualisiert eine existierende Ressource vom Typ Semester.
     *
     * @param id Id der Ressource vom Typ Semester, die am Server aktualisiert werden soll.
     * @param semesterDetails Instanz von Semester, die am Server aktualisiert werden soll.
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
    public ResponseEntity<Semester> updateSemester(@PathVariable(value = "id") Long id, @Valid @RequestBody Semester semesterDetails) throws ResourceNotFoundException {
        Optional<Semester> semester = semesterService.findOne(id);
        Semester result = null;
        if(semester.isPresent()) {
            result = semesterService.save(semesterDetails);
        }
        else{
            throw new ResourceNotFoundException("Semester mit dieser Id nicht gefunden: " + id);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET  /semesters} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ Semester.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ Semester.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Semester>> getAllsemesters() {
        log.debug("REST request to get all semesters");
        return ResponseEntity.ok(semesterService.findAll());
    }

    /**
     * {@code GET  /semesters/:id} : Liefert die Ressource vom Typ Semester mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ Semester, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ Semester.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
      @Override
      @ApiResponses(value = {
              @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ Semester zurückgeliefert."),
              @ApiResponse(responseCode = "404", description = "Status Code {@code 404 (Not Found)} wird zurückgeliefert, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.")
          })
    @GetMapping("/{id}")
    public ResponseEntity<Semester> getSemester(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get semester : {}", id);
        Optional<Semester> semester = semesterService.findOne(id);
        if(semester.isPresent()) {
            return ResponseEntity.ok(semester.get());
        }
        else{
            throw new ResourceNotFoundException("semester mit dieser Id nicht gefunden: " + id);
        }

    }
        /**
         * {@code DELETE  /semesters/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
         *
         * @param id Die Id der Ressource vom Typ Semester, die gelöscht werden soll.
         * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
         */
        @Override
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
            log.debug("REST request to delete semester : {}", id);
            semesterService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
