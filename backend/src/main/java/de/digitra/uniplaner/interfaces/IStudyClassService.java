package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.StudyClass;

import de.digitra.uniplaner.interfaces.IStudyClassService;
import de.digitra.uniplaner.repository.StudyClassRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface IStudyClassService {

    public StudyClass save(StudyClass studyclass);

    public void delete(Long id);

    public List<StudyClass> findAll();

    public Optional<StudyClass> findOne(Long id);
}
