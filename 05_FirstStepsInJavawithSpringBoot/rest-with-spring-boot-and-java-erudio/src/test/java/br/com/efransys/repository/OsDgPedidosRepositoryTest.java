package br.com.efransys.repository;

import br.com.efransys.dto.OsDgPedidosDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for OsDgPedidosRepository demonstrating the use of DTOs
 * instead of exposing domain models directly
 */
@DataJpaTest
@ComponentScan(basePackages = "br.com.efransys.repository")
@ActiveProfiles("test")
class OsDgPedidosRepositoryTest {

    @Autowired
    private OsDgPedidosRepository repository;

    @Test
    void testSavePedidoWithDto() {
        // Given - Create a DTO instead of a model
        OsDgPedidosDto pedidoDto = new OsDgPedidosDto(
                "PED-001", 
                "Pedido de teste", 
                "PENDENTE", 
                100.50
        );

        // When - Save using DTO
        Long savedId = repository.savePedido(pedidoDto);

        // Then - Verify the save operation
        assertThat(savedId).isNotNull();
        assertThat(savedId).isGreaterThan(0);
    }

    @Test
    void testFindPedidoByIdReturnsDto() {
        // Given - Create and save a pedido
        OsDgPedidosDto pedidoDto = new OsDgPedidosDto(
                "PED-002", 
                "Segundo pedido de teste", 
                "PROCESSANDO", 
                250.75
        );
        Long savedId = repository.savePedido(pedidoDto);

        // When - Find by ID
        Optional<OsDgPedidosDto> foundPedido = repository.findPedidoById(savedId);

        // Then - Verify we get a DTO back, not a model
        assertThat(foundPedido).isPresent();
        assertThat(foundPedido.get()).isInstanceOf(OsDgPedidosDto.class);
        assertThat(foundPedido.get().getNumeroPedido()).isEqualTo("PED-002");
        assertThat(foundPedido.get().getDescricao()).isEqualTo("Segundo pedido de teste");
        assertThat(foundPedido.get().getStatus()).isEqualTo("PROCESSANDO");
        assertThat(foundPedido.get().getValorTotal()).isEqualTo(250.75);
    }

    @Test
    void testFindAllPedidosReturnsDtoList() {
        // Given - Create and save multiple pedidos
        OsDgPedidosDto pedido1 = new OsDgPedidosDto("PED-003", "Primeiro pedido", "PENDENTE", 100.0);
        OsDgPedidosDto pedido2 = new OsDgPedidosDto("PED-004", "Segundo pedido", "CONCLUIDO", 200.0);
        
        repository.savePedido(pedido1);
        repository.savePedido(pedido2);

        // When - Find all pedidos
        List<OsDgPedidosDto> allPedidos = repository.findAllPedidos();

        // Then - Verify we get DTOs back
        assertThat(allPedidos).hasSize(2);
        assertThat(allPedidos).allMatch(pedido -> pedido instanceof OsDgPedidosDto);
        assertThat(allPedidos).extracting(OsDgPedidosDto::getNumeroPedido)
                .containsExactlyInAnyOrder("PED-003", "PED-004");
    }

    @Test
    void testUpdatePedidoWithDto() {
        // Given - Create and save a pedido
        OsDgPedidosDto originalPedido = new OsDgPedidosDto(
                "PED-005", 
                "Pedido original", 
                "PENDENTE", 
                150.0
        );
        Long savedId = repository.savePedido(originalPedido);

        // When - Update using DTO
        OsDgPedidosDto updatedPedido = new OsDgPedidosDto(
                "PED-005-UPDATED", 
                "Pedido atualizado", 
                "CONCLUIDO", 
                300.0
        );
        updatedPedido.setId(savedId);
        
        Optional<OsDgPedidosDto> result = repository.updatePedido(updatedPedido);

        // Then - Verify the update
        assertThat(result).isPresent();
        assertThat(result.get().getNumeroPedido()).isEqualTo("PED-005-UPDATED");
        assertThat(result.get().getDescricao()).isEqualTo("Pedido atualizado");
        assertThat(result.get().getStatus()).isEqualTo("CONCLUIDO");
        assertThat(result.get().getValorTotal()).isEqualTo(300.0);
    }

    @Test
    void testDeletePedido() {
        // Given - Create and save a pedido
        OsDgPedidosDto pedidoDto = new OsDgPedidosDto(
                "PED-006", 
                "Pedido para deletar", 
                "PENDENTE", 
                75.0
        );
        Long savedId = repository.savePedido(pedidoDto);

        // When - Delete the pedido
        boolean deleted = repository.deletePedido(savedId);

        // Then - Verify deletion
        assertThat(deleted).isTrue();
        assertThat(repository.findPedidoById(savedId)).isEmpty();
    }

    @Test
    void testUpdatePedidoWithNullIdThrowsException() {
        // Given - DTO without ID
        OsDgPedidosDto pedidoDto = new OsDgPedidosDto(
                "PED-007", 
                "Pedido sem ID", 
                "PENDENTE", 
                50.0
        );

        // When/Then - Should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            repository.updatePedido(pedidoDto);
        });
    }

    @Test
    void testDeleteNonExistentPedidoReturnsFalse() {
        // When - Try to delete non-existent pedido
        boolean deleted = repository.deletePedido(99999L);

        // Then - Should return false
        assertThat(deleted).isFalse();
    }
}