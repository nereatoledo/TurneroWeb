package unpsjb.labprog.backend.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.PacienteRepository;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.AtributoInvalidoException;

@Component
public class PacienteValidator implements Validator<Paciente> {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void checkCreate(Paciente paciente) {
        checkFormat(paciente);
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            throw new IllegalStateException("El dni ya existe en el sistema");
        }
    }

    @Override
    public void checkFormat(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty())
            throw new AtributoInvalidoException("El Nombre es obligatorio");

        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty())
            throw new AtributoInvalidoException("El apellido es obligatorio");

        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty())
            throw new AtributoInvalidoException("El dni es obligatorio");

        if (!paciente.getDni().matches("^[0-9]+$"))
            throw new AtributoInvalidoException("dni incorrecto, débe contener sólo números");

        if (paciente.getFechaNacimiento() == null)
            throw new AtributoInvalidoException("La fecha de nacimiento es obligatoria");
    }

    @Override
    public void checkUpdate(Paciente paciente) {
    }

}