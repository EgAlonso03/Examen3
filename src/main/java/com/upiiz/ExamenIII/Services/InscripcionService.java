package com.upiiz.ExamenIII.Services;

import com.upiiz.ExamenIII.Models.InscripcionModel;
import com.upiiz.ExamenIII.Repositories.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class InscripcionService implements InscripcionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<InscripcionModel> findAllInscripciones() {
        return jdbcTemplate.query("SELECT * FROM inscripciones",
                new BeanPropertyRowMapper<>(InscripcionModel.class));
    }

    @Override
    public InscripcionModel findInscripcionById(int id) {
        return jdbcTemplate.query("SELECT * FROM inscripciones WHERE id=?",
                        new BeanPropertyRowMapper<>(InscripcionModel.class),id)
                .stream().findFirst().orElse(new InscripcionModel());
    }

    @Override
    public InscripcionModel save(InscripcionModel inscripcion) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO inscripciones(estudiante,curso,calificacion)VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, inscripcion.getEstudiante());
            ps.setString(2, inscripcion.getCurso());
            ps.setBigDecimal(3, inscripcion.getCalificacion());
            return ps;
        },keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            inscripcion.setId(generatedId.longValue());
        }else {
            inscripcion.setId(0L);
        }
        return inscripcion;
    }

    @Override
    public int update(InscripcionModel inscripcion) {
        int registrosAfectados = jdbcTemplate.update(
                "UPDATE inscripciones SET estudiante = ?, curso = ?, calificacion = ? WHERE id = ?",
                inscripcion.getEstudiante(),
                inscripcion.getCurso(),
                inscripcion.getCalificacion(),
                inscripcion.getId()
        );
        return registrosAfectados;
    }

    @Override
    public int delete(int id) {
        int registrosAfectados=jdbcTemplate.update("DELETE FROM inscripciones WHERE id=?",id);
        return registrosAfectados;
    }
}
