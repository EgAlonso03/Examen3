package com.upiiz.ExamenIII.Models;

import java.math.BigDecimal;

public class InscripcionModel {
    private Long id;
    private int estudiante;
    private String curso;
    private BigDecimal calificacion;

    public InscripcionModel() {

    }

    public InscripcionModel(int estudiante, String curso, BigDecimal calificacion) {
        this.calificacion = calificacion;
        this.curso = curso;
        this.estudiante = estudiante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(int estudiante) {
        this.estudiante = estudiante;
    }
}