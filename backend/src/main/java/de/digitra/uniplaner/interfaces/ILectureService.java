package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Lecture;

import de.digitra.uniplaner.interfaces.ILectureService;
import de.digitra.uniplaner.repository.LectureRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ILectureService {

    public Lecture save(Lecture lecture);

    public void delete(Long id);

    public List<Lecture> findAll();

    public Optional<Lecture> findOne(Long id);
}
