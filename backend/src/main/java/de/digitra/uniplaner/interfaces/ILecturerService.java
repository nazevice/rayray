package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Lecturer;

import de.digitra.uniplaner.interfaces.ILecturerService;
import de.digitra.uniplaner.repository.LecturerRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ILecturerService {

    public Lecturer save(Lecturer lecturer);

    public void delete(Long id);

    public List<Lecturer> findAll();

    public Optional<Lecturer> findOne(Long id);
}
