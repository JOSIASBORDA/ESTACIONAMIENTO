package com.estacionamiento.estacionamiento.Respository;

import com.estacionamiento.estacionamiento.Entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

}
