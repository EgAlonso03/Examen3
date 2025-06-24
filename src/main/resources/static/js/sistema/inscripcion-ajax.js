let idInscripcionEliminar=0;
let idInscripcionActualizar=0;

function obtenerInscripcion() {
    $.ajax({
        method:"GET",
        url: "/v1/api/inscripcion",
        data: {},
        success: function( resultado ) {
            if(resultado.estado===1){
                let tabla=$('#example').DataTable();
                inscripciones = resultado.inscripciones;

                inscripciones.forEach(inscripcion =>{
                    let botones ='<button type="button" class="btn btn-primary mb-2" data-bs-toggle="modal" data-bs-target="#editModal" onclick="seleccionarInscripcionActualizar('+inscripcion.id+');">Edit</button>';
                    botones = botones + ' <button type="button" class="btn btn-danger mb-2" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="seleccionarInscripcionEliminar('+inscripcion.id+');">Delete</button>\n';
                    tabla.row.add([
                        inscripcion.id,
                        inscripcion.calificacion,
                        inscripcion.curso,
                        inscripcion.estudiante,
                        botones
                    ]).node.id='renglon_'+inscripcion.id;
                    tabla.draw()
                })
            }
        },
        error:function (xhr,error,mensaje){

        }
    });
}

function guardarInscripcion(){
    calificacion_inscripcion = document.getElementById("calificacion_inscripcion").value;
    curso_inscripcion = document.getElementById("curso_inscripcion").value;
    estudiante_inscripcion = document.getElementById("estudiante_inscripcion").value;
    //Validar de forma simple los campos - EXPRESIONES REGULARES
    if(!isNaN(calificacion_inscripcion) && calificacion_inscripcion > 0 &&
        curso_inscripcion.trim().length > 0 &&
        !isNaN(estudiante_inscripcion) && estudiante_inscripcion > 0){
        $.ajax({
            url: "/v1/api/inscripcion",
            contentType:"application/json",
            method:"POST",
            data: JSON.stringify({
                calificacion: parseFloat(calificacion_inscripcion),
                curso: curso_inscripcion,
                estudiante: parseInt(estudiante_inscripcion),
            }),
            success: function( resultado ) {
                if(resultado.estado==1){
                    let botones ='<button type="button" class="btn btn-primary mb-2" data-bs-toggle="modal" data-bs-target="#editModal" onclick="seleccionarInscripcionActualizar('+resultado.inscripcion.id+');">Edit</button>';
                    botones = botones + ' <button type="button" class="btn btn-danger mb-2" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="seleccionarInscripcionEliminar('+resultado.inscripcion.id+');">Delete</button>\n';

                    let tabla = $('#example').DataTable();
                    tabla.row.add([
                        resultado.inscripcion.id,
                        resultado.inscripcion.calificacion,
                        resultado.inscripcion.curso,
                        resultado.inscripcion.estudiante,
                        botones
                    ]).node().id='renglon_'+resultado.inscripcion.id;

                    tabla.draw()
                    //Ocultar la Modal JQuery
                    $('#basicModal').hide()
                    alert(resultado.mensaje);
                }else{
                    alert(resultado.mensaje);
                }
            },
            error:function (xhr,error,mensaje) {
                alert("Error de comunicacion: "+error);
            }
        });
    }else{
        alert("Ingresa los datos correctamente")
    }
}

function seleccionarInscripcionActualizar(id) {
    //1.- Seleccionar el id a actualizar
    idInscripcionActualizar=id;
    //2.- Consultar la API para obtener los datos de la mascota - GET

    $.ajax({
        method:"GET",
        url: "/v1/api/inscripcion/actualizar/"+idInscripcionActualizar,
        data: {},
        success: function( resultado ) {
            if(resultado.estado===1){
                let inscripcion = resultado.inscripcion;
                $('#calificacion_inscripcion_editar').val(inscripcion.calificacion);
                $('#curso_inscripcion_editar').val(inscripcion.curso);
                $('#estudiante_inscripcion_editar').val(inscripcion.estudiante);
            }else{
                alert(resultado.mensaje);
            }
        },
        error:function (xhr,error, mensaje) {
            alert(mensaje);
        }
    });
    //3.- Mostrar los datos en el Modal
}

function actualizarInscripcion() {
    //1.- Obtener los datos que existen en el modal
    calificacion_inscripcion = $('#calificacion_inscripcion_editar').val();
    curso_inscripcion = $('#curso_inscripcion_editar').val();
    estudiante_inscripcion = $('#estudiante_inscripcion_editar').val();
    if (calificacion_inscripcion > 0 && curso_inscripcion && estudiante_inscripcion > 0) {
        $.ajax({
            url: "/v1/api/inscripcion/actualizar/" + idInscripcionActualizar,
            contentType: "application/json",
            method: "POST",
            data: JSON.stringify({
                id: idInscripcionActualizar,
                calificacion: parseFloat(calificacion_inscripcion),
                curso: curso_inscripcion,
                estudiante: parseInt(estudiante_inscripcion),
            }),
            success: function (resultado) {
                if (resultado.estado == 1) {
                    let tabla = $('#example').DataTable();
                    datos = tabla.row("#renglon_" + idInscripcionActualizar).data()
                    datos[1] = calificacion_inscripcion;
                    datos[2] = curso_inscripcion;
                    datos[3] = estudiante_inscripcion;
                    tabla.row("#renglon_" + idInscripcionActualizar).data(datos);
                    tabla.draw()
                    alert(resultado.mensaje);
                } else {
                    //Todo mal
                    alert(resultado.mensaje);
                }
            },
            error: function (xhr, error, mensaje) {
                //Se dispara la funcion si no conexion al servidor
                alert("Error de comunicacion: " + error);
            }
        });
    } else {
        alert("Ingresa los datos correctamente")
    }
}

function seleccionarInscripcionEliminar(id){
    let datosInscripcion=$('#example').DataTable().row('#renglon_'+id).data()
    $('#nombre_eliminar').text(datosInscripcion[1]+' :(')
    idInscripcionEliminar=id
}

console.log(datosInscripcion);

function eliminarInscripcion() {
    $.ajax({
        method: "POST",
        url: "/v1/api/inscripcion/eliminar",
        contentType:"application/json",
        data:JSON.stringify({
            id:idInscripcionEliminar,
        }),
        success: function( resultado ) {
            if(resultado.estado===1){
                //Eliminar el renglon del DataTable
                $('#example').DataTable().row('#renglon_'+idInscripcionEliminar).remove().draw();
                alert(resultado.mensaje);
            }else{
                alert(resultado.mensaje)
            }
        },
        error:function (xhr,error,mensaje){
            alert("Error de comunicacion "+error)
        }
    });
}