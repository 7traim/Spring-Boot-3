package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienterepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrar(@RequestBody @Valid DatosRegistroPaciente datos, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienterepository.save(new Paciente(datos));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getUrbanizacion(), paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(), paciente.getDireccion().getCodigopostal(),
                        paciente.getDireccion().getComplemento(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getProvincia(), paciente.getDireccion().getCiudad()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(page = 0, size = 10) Pageable paginacion){
        return ResponseEntity.ok(pacienterepository.findAllByActivoTrue(paginacion).map(DatosListadoPaciente::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datos) {
        Paciente paciente = pacienterepository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getUrbanizacion(), paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(), paciente.getDireccion().getCodigopostal(),
                        paciente.getDireccion().getComplemento(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getProvincia(), paciente.getDireccion().getCiudad())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity removerPaciente(@PathVariable Long id) {
        Paciente paciente = pacienterepository.getReferenceById(id);
        paciente.inactivarPaciente();
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id) {
        Paciente paciente = pacienterepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getUrbanizacion(), paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(), paciente.getDireccion().getCodigopostal(),
                        paciente.getDireccion().getComplemento(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getProvincia(), paciente.getDireccion().getCiudad()));
        return  ResponseEntity.ok(datosPaciente);
    }








}
































