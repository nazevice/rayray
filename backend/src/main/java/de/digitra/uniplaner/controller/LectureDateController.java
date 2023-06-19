package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.interfaces.ILectureDateController;
import de.digitra.uniplaner.service.LectureDateService;
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
@RequestMapping("/lecturedates")
public class LectureDateController implements ILectureDateController {

    private final Logger log = LoggerFactory.getLogger(LectureDateController.class);
    private final String ENTITY_NAME = "LectureDate";

    private final LectureDateService lecturedateService;

    public LectureDateController(LectureDateService lecturedateService) {
        this.lecturedateService = lecturedateService;
    }


     /**
     * {@code POST  /lecturedates} : Methode erstellt eine Ressource vom Typ LectureDate.
     *
     * @param lecturedate Instanz von LectureDate, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls lecturedate nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
                 @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von LectureDate zurückgeliefert."),
                 @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls der übergebene Parameter lecturedate nicht zulässig ist. Dies ist der Fall, falls er bereits eine Id hat, die nicht null ist.")
         })
    @PostMapping
    public ResponseEntity<LectureDate> createLectureDate(@RequestBody LectureDate lecturedate) throws BadRequestException {
        log.debug("REST request to save LectureDate : {}", lecturedate);
        if (lecturedate.getId() != null) {

            String message = "A new lecturedate cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);

        }
        LectureDate result = lecturedateService.save(lecturedate);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /lecturedates} : aktualisiert eine existierende Ressource vom Typ LectureDate.
     *
     * @param lecturedate Instanz von LectureDate, die am Server aktualisiert werden soll.
     * Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls lecturedate nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von LectureDate zurückgeliefert."),
         @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls falls der übergebene Parameter lecturedate nicht zulässig ist. Dies ist der Fall, falls er eine Id mit dem Wert null hat."),
         @ApiResponse(responseCode = "500", description = "Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.")

     })
    @PutMapping
    public ResponseEntity<LectureDate> updateLectureDate(@RequestBody LectureDate lecturedate) throws  BadRequestException {
        log.debug("REST request to update LectureDate : {}", lecturedate);
        if (lecturedate.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        LectureDate result = lecturedateService.save(lecturedate);
        return ResponseEntity.ok()
            .body(result);
    }

  /**
     * {@code PUT  /lecturedates/:id} : aktualisiert eine existierende Ressource vom Typ LectureDate.
     *
     * @param id Id der Ressource vom Typ LectureDate, die am Server aktualisiert werden soll.
     * @param lecturedateDetails Instanz von LectureDate, die am Server aktualisiert werden soll.
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
    public ResponseEntity<LectureDate> updateLectureDate(@PathVariable(value = "id") Long id, @Valid @RequestBody LectureDate lecturedateDetails) throws ResourceNotFoundException {
        Optional<LectureDate> lecturedate = lecturedateService.findOne(id);
        LectureDate result = null;
        if(lecturedate.isPresent()) {
            result = lecturedateService.save(lecturedateDetails);
        }
        else{
            throw new ResourceNotFoundException("LectureDate mit dieser Id nicht gefunden: " + id);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET  /lecturedates} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ LectureDate.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ LectureDate.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<LectureDate>> getAlllecturedates() {
        log.debug("REST request to get all lecturedates");
        return ResponseEntity.ok(lecturedateService.findAll());
    }

    /**
     * {@code GET  /lecturedates/:id} : Liefert die Ressource vom Typ LectureDate mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ LectureDate, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ LectureDate.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
      @Override
      @ApiResponses(value = {
              @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ LectureDate zurückgeliefert."),
              @ApiResponse(responseCode = "404", description = "Status Code {@code 404 (Not Found)} wird zurückgeliefert, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.")
          })
    @GetMapping("/{id}")
    public ResponseEntity<LectureDate> getLectureDate(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get lecturedate : {}", id);
        Optional<LectureDate> lecturedate = lecturedateService.findOne(id);
        if(lecturedate.isPresent()) {
            return ResponseEntity.ok(lecturedate.get());
        }
        else{
            throw new ResourceNotFoundException("lecturedate mit dieser Id nicht gefunden: " + id);
        }

    }
        /**
         * {@code DELETE  /lecturedates/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
         *
         * @param id Die Id der Ressource vom Typ LectureDate, die gelöscht werden soll.
         * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
         */
        @Override
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteLectureDate(@PathVariable Long id) {
            log.debug("REST request to delete lecturedate : {}", id);
            lecturedateService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
