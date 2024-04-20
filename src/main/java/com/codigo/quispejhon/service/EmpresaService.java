package com.codigo.quispejhon.service;

import com.codigo.quispejhon.entity.EmpresaEntity;
import com.codigo.quispejhon.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {

    EmpresaEntity crear(EmpresaRequest empresaRequest);
    Optional<EmpresaEntity> buscarPorId(Long id);
    List<EmpresaEntity> buscarAll();
    EmpresaEntity actualizar(Long id, EmpresaRequest empresaRequest);
    EmpresaEntity borrar(Long id);

}
