package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.LectureDate;

import de.digitra.uniplaner.interfaces.ILectureDateService;
import de.digitra.uniplaner.repository.LectureDateRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ILectureDateService {

    public LectureDate save(LectureDate lecturedate);

    public void delete(Long id);

    public List<LectureDate> findAll();

    public Optional<LectureDate> findOne(Long id);
}
