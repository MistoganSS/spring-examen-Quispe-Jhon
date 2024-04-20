package com.codigo.quispejhon.service;

import com.codigo.quispejhon.constants.Constans;
import com.codigo.quispejhon.dao.EmpresaRepository;
import com.codigo.quispejhon.entity.EmpresaEntity;
import com.codigo.quispejhon.request.EmpresaRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaService{
    private final EmpresaRepository empresaRepository;
    @Override
    public EmpresaEntity crear(EmpresaRequest empresaRequest) {
        return empresaRepository.save(getEmpresaCreate(empresaRequest));
    }

    @Override
    public Optional<EmpresaEntity> buscarPorId(Long id) {
        Optional<EmpresaEntity> empresaEntity=empresaRepository.findById(id);
        if (empresaEntity.isEmpty()) return null;

        return empresaEntity;
    }

    @Override
    public List<EmpresaEntity> buscarAll() {
        return empresaRepository.findAll();
    }

    @Override
    public EmpresaEntity actualizar(Long id, EmpresaRequest empresaRequest) {
        Optional<EmpresaEntity> empresaEntity=empresaRepository.findById(id);
        if(empresaEntity.isEmpty()) return null;


        return empresaRepository.save(getEmpresaModif(empresaEntity.get(),empresaRequest));
    }

    @Override
    public EmpresaEntity borrar(Long id) {
        Optional<EmpresaEntity> empresaEntity= empresaRepository.findById(id);
        if(empresaEntity.isEmpty()) return  null;

        return empresaRepository.save(getEmpresaDelet(empresaEntity.get()));
    }
    private EmpresaEntity getEmpresaCreate(EmpresaRequest empresaRequest){
        EmpresaEntity empresaEntity= new EmpresaEntity();

        getEmpresaAttributes(empresaEntity,empresaRequest);

        empresaEntity.setEstado(Constans.STATUS_ACTIVE);
        empresaEntity.setUsuaCrea(Constans.USU_ADMIN);
        empresaEntity.setDateCreate(getTimesTamp());
        return  empresaEntity;
    }

    private EmpresaEntity getEmpresaDelet(EmpresaEntity empresaEntity){
        empresaEntity.setEstado(Constans.STATUS_INACTIVE);
        empresaEntity.setUsuaDelet(Constans.USU_ADMIN);
        empresaEntity.setDateDelet(getTimesTamp());
        return empresaEntity;
    }
    private EmpresaEntity getEmpresaModif(EmpresaEntity empresaEntity, EmpresaRequest empresaRequest){

        getEmpresaAttributes(empresaEntity, empresaRequest);

        empresaEntity.setUsuaModif(Constans.USU_ADMIN);
        empresaEntity.setDateModif(getTimesTamp());
        return empresaEntity;
    }
    private EmpresaEntity getEmpresaAttributes(EmpresaEntity empresaEntity,EmpresaRequest empresaRequest){
        empresaEntity.setRazonSocial(empresaRequest.getRazonSocial());
        empresaEntity.setTipoDocumento(empresaRequest.getTipoDocumento());
        empresaEntity.setNumeroDocumento(empresaRequest.getNumeroDocumento());
        empresaEntity.setCondicion(empresaRequest.getCondicion());
        empresaEntity.setDireccion(empresaRequest.getDireccion());
        empresaEntity.setDistrito(empresaRequest.getDistrito());
        empresaEntity.setProvincia(empresaRequest.getProvincia());
        empresaEntity.setDepartamento(empresaRequest.getDepartamento());
        empresaEntity.setEsAgenteRetencion(empresaRequest.isEsAgenteRetencion());
        return empresaEntity;
    }

    private Timestamp getTimesTamp(){
        return new Timestamp(System.currentTimeMillis());
    }
}
