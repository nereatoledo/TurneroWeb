package unpsjb.labprog.backend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.CentroAtencion;

@Service
public class CentroAtencionService {

    @Autowired
    CentroAtencionRepository repository;
    // Chequeamos si ya existe la combinación exacta de nombre y dirección
    public boolean existeConflicto(String nombre, String direccion) {
        return repository.findByNombreAndDireccion(nombre, direccion).isPresent();
    }

    @Transactional
    public CentroAtencion save(CentroAtencion centro) {
        return repository.save(centro);
    }
}