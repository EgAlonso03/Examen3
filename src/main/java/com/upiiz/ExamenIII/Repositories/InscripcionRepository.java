package com.upiiz.ExamenIII.Repositories;

import com.upiiz.ExamenIII.Models.InscripcionModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository {
    public List<InscripcionModel> findAllInscripciones();
    public InscripcionModel findInscripcionById(int id);

    public InscripcionModel save(InscripcionModel model);

    public int update(InscripcionModel model);
    public int delete(int id);
}