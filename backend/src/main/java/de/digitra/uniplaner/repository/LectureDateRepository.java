package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.LectureDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface LectureDateRepository extends JpaRepository<LectureDate, Long> {
}