package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.StudyProgram;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
}