# DTO Pattern Implementation in Repository Layer

This project demonstrates the use of Data Transfer Objects (DTOs) in the repository layer instead of exposing domain models directly.

## Architecture Overview

The implementation follows a layered architecture with proper separation of concerns:

### Model Layer (`br.com.efransys.model`)
- **OsDgPedidosModel**: JPA entity that represents the database table structure
- Contains JPA annotations and persistence logic
- Encapsulated within the persistence layer

### DTO Layer (`br.com.efransys.dto`)
- **OsDgPedidosDto**: Data Transfer Object for external communication
- Pure data structure without persistence annotations
- Used for data transfer between application layers

### Repository Layer (`br.com.efransys.repository`)
- **OsDgPedidosRepository**: Repository class that handles DTO-to-Model conversion
- **OsDgPedidosJpaRepository**: JPA repository interface for database operations
- Demonstrates the recommended approach using DTOs instead of models

## Why Use DTOs in Repository Layer?

### ✅ Benefits

1. **Decoupling**: Separates the external API from internal database structure
2. **Flexibility**: Database schema changes don't affect external interfaces
3. **Security**: Prevents accidental exposure of sensitive model fields
4. **Clean Architecture**: Maintains clear boundaries between layers
5. **Versioning**: Easier to version APIs without changing domain models

### ❌ Without DTOs (Original Approach)

```java
// Not recommended - Exposing domain model directly
public Future<int> savePedido(OsDgPedidosModel pedido) {
    // Direct exposure of domain model
    // Tight coupling between persistence and external layers
}
```

### ✅ With DTOs (Recommended Approach)

```java
// Recommended - Using DTOs for data transfer
public Long savePedido(OsDgPedidosDto pedidoDto) {
    // Convert DTO to Model for persistence
    OsDgPedidosModel model = convertDtoToModel(pedidoDto);
    
    // Save through JPA repository
    OsDgPedidosModel savedModel = jpaRepository.save(model);
    
    // Return only the ID
    return savedModel.getId();
}
```

## Example Usage

```java
// Create a DTO for data transfer
OsDgPedidosDto pedidoDto = new OsDgPedidosDto(
    "PED-001", 
    "Sample order", 
    "PENDING", 
    100.50
);

// Save using DTO
Long savedId = repository.savePedido(pedidoDto);

// Retrieve as DTO
Optional<OsDgPedidosDto> result = repository.findPedidoById(savedId);
```

## Key Methods in Repository

- `savePedido(OsDgPedidosDto)` - Save new orders using DTO
- `findPedidoById(Long)` - Find orders by ID, returns DTO
- `findAllPedidos()` - Get all orders as DTO list
- `updatePedido(OsDgPedidosDto)` - Update existing orders
- `deletePedido(Long)` - Delete orders by ID

## Testing

The repository includes comprehensive tests that validate:
- DTO-based CRUD operations
- Proper data conversion between DTOs and models
- Error handling and validation
- Integration with JPA and H2 database

Run tests with:
```bash
./mvnw test
```

## Conclusion

This implementation demonstrates the best practice of using DTOs in repository layers to maintain clean architecture, proper separation of concerns, and loose coupling between application layers.