package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.StudyClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface StudyClassRepository extends JpaRepository<StudyClass, Long> {
}