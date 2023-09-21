package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(Long id, @NotNull @JsonAlias("paciente_id") Long idPaciente, @JsonAlias("medico_id") Long idMedico,
                                   @NotNull @Future @JsonAlias({"data", "fecha_consulta"})
                                   @JsonFormat(pattern = "dd/MM/yyy HH:mm") LocalDateTime fecha,
                                   Especialidad especialidad) {
//future es para validar que sea una fecha posterior a la fecha actual



}
































