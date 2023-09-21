package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico= medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getUrbanizacion(),
                        medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCodigopostal(), medico.getDireccion().getComplemento(),
                        medico.getDireccion().getNumero(), medico.getDireccion().getProvincia(),
                        medico.getDireccion().getCiudad()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return  ResponseEntity.created(url).body(datosRespuestaMedico);
        //retorna 201 created
        //URL donde encontrar al medico
        // GET http://localhost:8080/medicos/xx
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>>  listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){
        /*return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);*/
        return ResponseEntity.ok(medicoRepository.findAllByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getUrbanizacion(),
                medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                medico.getDireccion().getCodigopostal(), medico.getDireccion().getComplemento(),
                medico.getDireccion().getNumero(), medico.getDireccion().getProvincia(),
                medico.getDireccion().getCiudad()
                )));
    }

    /*METODO PARA BORRAR UN REGISTRO EN BASE DE DATOS*/
    /*@DeleteMapping("/{id")
    @Transactional
    public  void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/

    /*METODO PARA DELETE UN REGISTRO LOGICO*/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivadoMedico();
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getUrbanizacion(),
                        medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCodigopostal(), medico.getDireccion().getComplemento(),
                        medico.getDireccion().getNumero(), medico.getDireccion().getProvincia(),
                        medico.getDireccion().getCiudad()
                ));
        return ResponseEntity.ok(datosMedico);

    }



}



















