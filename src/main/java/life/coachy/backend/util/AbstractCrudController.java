package life.coachy.backend.util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import javax.validation.Valid;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.validation.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AbstractCrudController<
    T extends IdentifiableEntity<ID>, ID,
    U extends AbstractDto,
    C extends AbstractDto> {

  private final CrudOperationsService<T, ID> service;

  protected AbstractCrudController(CrudOperationsService<T, ID> service) {
    this.service = service;
  }

  @ApiOperation("Displays specified entity by it's identifier")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Entity could not be found"),
      @ApiResponse(code = 200, message = "Entity found and displayed")
  })
  @GetMapping("/{id}")
  protected ResponseEntity<T> read(@PathVariable @ApiParam("Entity identifier") ID id) {
    return this.service.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @ApiOperation("Creates entity")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Validation error occurred"),
      @ApiResponse(code = 201, message = "Entity created")
  })
  @PostMapping
  protected ResponseEntity<?> create(@RequestBody @Valid @ApiParam("Entity data transfer object") C dto,
      BindingResult result) {
    return this.createEntity(dto, result);
  }

  @ApiOperation("Updates entity")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Entity created"),
      @ApiResponse(code = 400, message = "Validation error occured"),
      @ApiResponse(code = 204, message = "Entity updated")
  })
  @PutMapping("/{id}")
  protected ResponseEntity<?> update(
      @RequestBody @Valid @ApiParam("Entity data transfer object") U dto,
      @PathVariable @ApiParam("Entity identifier") ID id,
      BindingResult result) {
    Optional<T> entity = this.service.findById(id);

    if (!entity.isPresent()) {
      return this.createEntity(dto, result);
    }

    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationUtil.toDto(result.getFieldErrors()));
    }

    BeanUtil.copyNonNullProperties(dto, entity.get());
    this.service.save(entity.get());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @ApiOperation("Partial updates entity")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Entity could not be found"),
      @ApiResponse(code = 204, message = "Entity updated")
  })
  @PatchMapping("/{id}")
  protected ResponseEntity<?> partialUpdate(
      @RequestBody @ApiParam("Entity data transfer object") U dto,
      @PathVariable @ApiParam("Entity identifier") ID id) {
    Optional<T> optionalEntity = this.service.findById(id);

    if (!optionalEntity.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    BeanUtil.copyNonNullProperties(dto, optionalEntity.get());
    this.service.save(optionalEntity.get());
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Deletes entity")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Entity could not be found"),
      @ApiResponse(code = 204, message = "Entity deleted")
  })
  @DeleteMapping("/{id}")
  protected ResponseEntity<T> remove(@PathVariable @ApiParam("Entity identifier") ID id) {
    if (!this.service.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    this.service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private <X extends AbstractDto> ResponseEntity<?> createEntity(X dto, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationUtil.toDto(result.getFieldErrors()));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dto));
  }

}
