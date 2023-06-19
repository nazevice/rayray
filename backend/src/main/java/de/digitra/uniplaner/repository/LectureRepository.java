package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.Lecture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface LectureRepository extends JpaRepository<Lecture, Long> {
}