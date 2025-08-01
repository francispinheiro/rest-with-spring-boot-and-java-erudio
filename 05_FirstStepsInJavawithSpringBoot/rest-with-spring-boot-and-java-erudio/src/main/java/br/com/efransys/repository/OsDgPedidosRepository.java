package br.com.efransys.repository;

import br.com.efransys.dto.OsDgPedidosDto;
import br.com.efransys.model.OsDgPedidosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA Repository interface for database operations
 */
interface OsDgPedidosJpaRepository extends JpaRepository<OsDgPedidosModel, Long> {
    // Additional custom query methods can be added here if needed
}

/**
 * Repository class for OsDgPedidos that demonstrates the use of DTOs
 * instead of exposing domain models directly.
 * 
 * This implementation follows the principle of using DTOs for data transfer
 * while keeping the domain model (OsDgPedidosModel) encapsulated within
 * the persistence layer.
 */
@Repository
public class OsDgPedidosRepository {

    private final OsDgPedidosJpaRepository jpaRepository;

    public OsDgPedidosRepository(OsDgPedidosJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Save a pedido using DTO instead of Model directly
     * This method receives a DTO, converts it to a model for persistence,
     * and returns the ID of the saved entity.
     * 
     * @param pedidoDto the DTO containing pedido data
     * @return the ID of the saved pedido
     */
    public Long savePedido(OsDgPedidosDto pedidoDto) {
        // Convert DTO to Model for persistence
        OsDgPedidosModel model = convertDtoToModel(pedidoDto);
        
        // Save the model through JPA repository
        OsDgPedidosModel savedModel = jpaRepository.save(model);
        
        // Return the ID of the saved entity
        return savedModel.getId();
    }

    /**
     * Find a pedido by ID and return as DTO
     * 
     * @param id the ID of the pedido
     * @return Optional containing the DTO if found
     */
    public Optional<OsDgPedidosDto> findPedidoById(Long id) {
        return jpaRepository.findById(id)
                .map(this::convertModelToDto);
    }

    /**
     * Find all pedidos and return as DTOs
     * 
     * @return List of DTOs
     */
    public List<OsDgPedidosDto> findAllPedidos() {
        return jpaRepository.findAll()
                .stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a pedido using DTO
     * 
     * @param pedidoDto the DTO with updated data
     * @return the updated DTO
     */
    public Optional<OsDgPedidosDto> updatePedido(OsDgPedidosDto pedidoDto) {
        if (pedidoDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation");
        }

        return jpaRepository.findById(pedidoDto.getId())
                .map(existingModel -> {
                    // Update existing model with DTO data
                    updateModelFromDto(existingModel, pedidoDto);
                    OsDgPedidosModel savedModel = jpaRepository.save(existingModel);
                    return convertModelToDto(savedModel);
                });
    }

    /**
     * Delete a pedido by ID
     * 
     * @param id the ID of the pedido to delete
     * @return true if deleted, false if not found
     */
    public boolean deletePedido(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper methods for DTO <-> Model conversion

    /**
     * Convert DTO to Model
     */
    private OsDgPedidosModel convertDtoToModel(OsDgPedidosDto dto) {
        OsDgPedidosModel model = new OsDgPedidosModel();
        model.setId(dto.getId());
        model.setNumeroPedido(dto.getNumeroPedido());
        model.setDescricao(dto.getDescricao());
        model.setDataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : LocalDateTime.now());
        model.setStatus(dto.getStatus());
        model.setValorTotal(dto.getValorTotal());
        return model;
    }

    /**
     * Convert Model to DTO
     */
    private OsDgPedidosDto convertModelToDto(OsDgPedidosModel model) {
        return new OsDgPedidosDto(
                model.getId(),
                model.getNumeroPedido(),
                model.getDescricao(),
                model.getDataCriacao(),
                model.getStatus(),
                model.getValorTotal()
        );
    }

    /**
     * Update existing model with DTO data
     */
    private void updateModelFromDto(OsDgPedidosModel model, OsDgPedidosDto dto) {
        model.setNumeroPedido(dto.getNumeroPedido());
        model.setDescricao(dto.getDescricao());
        model.setStatus(dto.getStatus());
        model.setValorTotal(dto.getValorTotal());
        // Note: We don't update ID or dataCriacao as these should remain unchanged
    }
}