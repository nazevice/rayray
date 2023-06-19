package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.StudyProgram;

import de.digitra.uniplaner.interfaces.IStudyProgramService;
import de.digitra.uniplaner.repository.StudyProgramRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface IStudyProgramService {

    public StudyProgram save(StudyProgram studyprogram);

    public void delete(Long id);

    public List<StudyProgram> findAll();

    public Optional<StudyProgram> findOne(Long id);
}
