package com.upiiz.ExamenIII.Controllers;

import com.upiiz.ExamenIII.Models.InscripcionModel;
import com.upiiz.ExamenIII.Services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping("/inscripcion")
    public String inscripcion() {
        return "inscripcion";
    }

    @GetMapping("/v1/api/inscripcion")
    public ResponseEntity<Map<String, Object>> getInscripcion() {
        List<InscripcionModel> inscripciones=inscripcionService.findAllInscripciones();
        return ResponseEntity.ok(Map.of(
                "estado",1,
                "mensaje","Listado de inscripciones",
                "inscripciones", inscripciones
        ));
    }

    @PostMapping("/v1/api/inscripcion")
    public ResponseEntity<Map<String, Object>> inscripcionPost(@RequestBody Map<String, Object> objetoInscripcion) {
        InscripcionModel inscripcion = new InscripcionModel(
                Integer.parseInt(objetoInscripcion.get("estudiante").toString()),
                objetoInscripcion.get("curso").toString(),
                new BigDecimal(objetoInscripcion.get("calificacion").toString())
        );

        InscripcionModel inscripcionGuardada = inscripcionService.save(inscripcion);

        if (inscripcionGuardada != null) {
            return ResponseEntity.ok(Map.of(
                    "estado", 1,
                    "mensaje", "Inscripción guardada correctamente",
                    "inscripcion", inscripcionGuardada
            ));
        } else {
            return ResponseEntity.ok(Map.of(
                    "estado", 0,
                    "mensaje", "Error: No se pudo guardar la inscripción",
                    "inscripcion", objetoInscripcion
            ));
        }
    }

    @PostMapping("/v1/api/inscripcion/eliminar")
    public ResponseEntity<Map<String,Object>> inscripcionDelete(@RequestBody Map<String,Object> objetoInscripcion) {
        int id = Integer.parseInt(objetoInscripcion.get("id").toString());

        if(inscripcionService.delete(id) > 0){
            return ResponseEntity.ok(Map.of(
                    "estado",1,
                    "mensaje","Inscripción eliminada"
            ));
        }else {
            return ResponseEntity.ok(Map.of(
                    "estado",0,
                    "mensaje","No se pudo eliminar la inscripción"
            ));
        }
    }

    @GetMapping("/v1/api/inscripcion/actualizar/{id}")
    public ResponseEntity<Map<String,Object>> inscripcionActualizar(@PathVariable int id) {
        InscripcionModel inscripcion = inscripcionService.findInscripcionById(id);
        return ResponseEntity.ok(Map.of(
                "estado",1,
                "mensaje","Inscripción encontrada",
                "inscripcion", inscripcion
        ));
    }

    @PostMapping("/v1/api/inscripcion/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> inscripcionActualizarDatos(@PathVariable Long id, @RequestBody Map<String, Object> objetoInscripcion) {
        InscripcionModel inscripcion = new InscripcionModel(
                Integer.parseInt(objetoInscripcion.get("estudiante").toString()),
                objetoInscripcion.get("curso").toString(),
                new BigDecimal(objetoInscripcion.get("calificacion").toString())
        );
        inscripcion.setId(id);

        if (inscripcionService.update(inscripcion) > 0) {
            return ResponseEntity.ok(Map.of(
                    "estado", 1,
                    "mensaje", "Inscripción actualizada correctamente",
                    "inscripcion", inscripcion
            ));
        } else {
            return ResponseEntity.ok(Map.of(
                    "estado", 0,
                    "mensaje", "Error: No se pudo actualizar la inscripción",
                    "inscripcion", objetoInscripcion
            ));
        }
    }
}