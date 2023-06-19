package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Lecturer;
import de.digitra.uniplaner.exceptions.DuplicateEmailException;
import de.digitra.uniplaner.interfaces.ILecturerController;
import de.digitra.uniplaner.service.LecturerService;
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
@RequestMapping("/lecturers")
public class LecturerController implements ILecturerController {

    private final Logger log = LoggerFactory.getLogger(LecturerController.class);
    private final String ENTITY_NAME = "Lecturer";

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }


     /**
     * {@code POST  /lecturers} : Methode erstellt eine Ressource vom Typ Lecturer.
     *
     * @param lecturer Instanz von Lecturer, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls lecturer nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
                 @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von Lecturer zurückgeliefert."),
                 @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls der übergebene Parameter lecturer nicht zulässig ist. Dies ist der Fall, falls er bereits eine Id hat, die nicht null ist.")
         })
    @PostMapping
     public ResponseEntity<Lecturer> createLecturer(@RequestBody Lecturer lecturer) throws BadRequestException, DuplicateEmailException {
         log.debug("REST request to save Lecturer : {}", lecturer);
         if (lecturer.getId() != null) {
             String message = "A new lecturer cannot already have an ID: " + ENTITY_NAME;
             throw new BadRequestException(message);
         }
         List<Lecturer> sameEmailList =lecturerService.findByEmail(lecturer.getEmail());
         if(sameEmailList!=null && sameEmailList.size()>0){
             String message = "the specified email is already in use: " + ENTITY_NAME;
             throw new DuplicateEmailException(message);
         }
         Lecturer result = lecturerService.save(lecturer);
         return ResponseEntity.ok(result);
     }

    /**
     * {@code PUT  /lecturers} : aktualisiert eine existierende Ressource vom Typ Lecturer.
     *
     * @param lecturer Instanz von Lecturer, die am Server aktualisiert werden soll.
     * Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls lecturer nicht zulässig ist.
     */
     @Override
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird  eine ResponseEntity mit Status Code {@code 200 (OK)} und im Body die erstellte Instanz von Lecturer zurückgeliefert."),
         @ApiResponse(responseCode = "400", description = "Status Code {@code 400 (Bad Request)} wird zurückgeliefert, falls falls der übergebene Parameter lecturer nicht zulässig ist. Dies ist der Fall, falls er eine Id mit dem Wert null hat."),
         @ApiResponse(responseCode = "500", description = "Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.")

     })
    @PutMapping
    public ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer) throws  BadRequestException {
        log.debug("REST request to update Lecturer : {}", lecturer);
        if (lecturer.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        Lecturer result = lecturerService.save(lecturer);
        return ResponseEntity.ok()
            .body(result);
    }

  /**
     * {@code PUT  /lecturers/:id} : aktualisiert eine existierende Ressource vom Typ Lecturer.
     *
     * @param id Id der Ressource vom Typ Lecturer, die am Server aktualisiert werden soll.
     * @param lecturerDetails Instanz von Lecturer, die am Server aktualisiert werden soll.
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
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecturer lecturerDetails) throws ResourceNotFoundException {
        Optional<Lecturer> lecturer = lecturerService.findOne(id);
        Lecturer result = null;
        if(lecturer.isPresent()) {
            result = lecturerService.save(lecturerDetails);
        }
        else{
            throw new ResourceNotFoundException("Lecturer mit dieser Id nicht gefunden: " + id);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET  /lecturers} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ Lecturer.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ Lecturer.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Lecturer>> getAlllecturers() {
        log.debug("REST request to get all lecturers");
        return ResponseEntity.ok(lecturerService.findAll());
    }

    /**
     * {@code GET  /lecturers/:id} : Liefert die Ressource vom Typ Lecturer mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ Lecturer, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ Lecturer.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
      @Override
      @ApiResponses(value = {
              @ApiResponse(responseCode = "200", description = "Bei einer erfolgreichen Ausführung wird Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ Lecturer zurückgeliefert."),
              @ApiResponse(responseCode = "404", description = "Status Code {@code 404 (Not Found)} wird zurückgeliefert, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.")
          })
    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getLecturer(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get lecturer : {}", id);
        Optional<Lecturer> lecturer = lecturerService.findOne(id);
        if(lecturer.isPresent()) {
            return ResponseEntity.ok(lecturer.get());
        }
        else{
            throw new ResourceNotFoundException("lecturer mit dieser Id nicht gefunden: " + id);
        }

    }
        /**
         * {@code DELETE  /lecturers/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
         *
         * @param id Die Id der Ressource vom Typ Lecturer, die gelöscht werden soll.
         * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
         */
        @Override
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
            log.debug("REST request to delete lecturer : {}", id);
            lecturerService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
