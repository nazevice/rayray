package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Semester;

import de.digitra.uniplaner.interfaces.ISemesterService;
import de.digitra.uniplaner.repository.SemesterRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ISemesterService {

    public Semester save(Semester semester);

    public void delete(Long id);

    public List<Semester> findAll();

    public Optional<Semester> findOne(Long id);
}
