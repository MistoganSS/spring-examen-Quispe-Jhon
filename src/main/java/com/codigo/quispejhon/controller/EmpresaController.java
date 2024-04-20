package com.codigo.quispejhon.controller;

import com.codigo.quispejhon.entity.EmpresaEntity;
import com.codigo.quispejhon.request.EmpresaRequest;
import com.codigo.quispejhon.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/examen/v1/empresa")
@AllArgsConstructor
@Tag(
        name = "API DE MANTENIMIENTO DE EMPRESA",
        description = "Esta api contiene todos los end points para realizar el mantenimeinto de la entidad empresa"
)
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping("/crear")
    @Operation(
            summary = "Crear una nueva Empresa",
            description = "Este endPoint necesita un Objeto Empresa, para guardar en la BD"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Empresa creada exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))})
    })
    public ResponseEntity<EmpresaEntity> crear(@RequestBody EmpresaRequest empresaRequest){
        return ResponseEntity.ok(empresaService.crear(empresaRequest));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una Empresa guardada",
            description = "Esta end point obtendra los datos de la empresa atravez del ID",
            parameters = {
                    @Parameter(name = "id", description = "Id de la empresa a obtener", required = true)
            }
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200, Operacion Exitosa",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS 404, Empresa a buscar no encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<EmpresaEntity> buscarPorId(@PathVariable Long id){
        Optional<EmpresaEntity> empresaEntity=empresaService.buscarPorId(id);
        if(empresaEntity.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(empresaEntity.get());
    }

    @GetMapping("/todos")
    @Operation(
            summary = "Obtener todas las Empresas guardadas",
            description = "Se obtendra todas las empresa guardadas en la BD"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200, Operacion Exitosa",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmpresaEntity.class)))})
    })
    public ResponseEntity<List<EmpresaEntity>> buscarTodos(){
        return ResponseEntity.ok(empresaService.buscarAll());
    }

    @PutMapping("/actualizar/{id}")
    @Operation(
            summary = "Actualizar la empresa atravez del ID",
            description = "Esta end point necesita el ID y el objeto de la empresa a actualizar",
            parameters = {
                    @Parameter(name = "id", description = "Id de la empresa a actualizar", required = true)
            }
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200, Operacion Exitosa",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS 404, Empresa a actualizar no encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<EmpresaEntity> actualizar(@PathVariable Long id, @RequestBody EmpresaRequest empresaRequest){
        EmpresaEntity empresaEntity= empresaService.actualizar(id, empresaRequest);
        if(empresaEntity == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(empresaEntity);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(
            summary = "Eliminar la empresa atravez del ID",
            description = "Esta end point necesita el ID de la empresa a eliminar, eliminado logico.",
            parameters = {
                    @Parameter(name = "id", description = "Id de la empresa a eliminar", required = true)
            }
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200, Operacion Exitosa",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS 404, Empresa a eliminar no encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<EmpresaEntity> eliminar(@PathVariable Long id){
        EmpresaEntity empresaEntity= empresaService.borrar(id);
        if(empresaEntity == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(empresaEntity);
    }
}
