package life.coachy.backend.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AbstractCrudController<T extends IdentifiableEntity<ID>, ID, C extends AbstractDto<T>> {

  private static final String SPEL_EXPRESSION = "(isAuthenticated() && principal.user.identifier.equals(#id)) || hasAuthority('ADMIN')";
  private final CrudOperationsService<T, ID> service;

  protected AbstractCrudController(CrudOperationsService<T, ID> service) {
    this.service = service;
  }

  @GetMapping
  protected ResponseEntity<List<T>> readAll() {
    return ResponseEntity.ok(this.service.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<T> read(@PathVariable ID id) {
    return this.service.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  protected ResponseEntity<?> create(@RequestBody @Valid C dto, BindingResult result) {
    return this.createEntity(dto, result);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @PutMapping("/{id}")
  protected ResponseEntity<?> update(@RequestBody @Valid C dto, @PathVariable ID id, BindingResult result) {
    T entity = dto.toEntity();

    if (!this.service.findById(id).isPresent()) {
      return this.createEntity(dto, result);
    }

    if (result.hasErrors()) {
      return RequestUtil.errorResponse(result);
    }

    this.service.save(entity);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @PatchMapping("/{id}")
  protected ResponseEntity<C> partialUpdate(@RequestBody C dto, @PathVariable ID id)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    Optional<T> optionalEntity = this.service.findById(id);
    T originEntity = dto.toEntity();

    if (!optionalEntity.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    BeanUtil.copyNonNullProperties(optionalEntity.get(), originEntity);

    this.service.save(optionalEntity.get());
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @DeleteMapping("/{id}")
  protected ResponseEntity<T> remove(@PathVariable ID id) {
    if (!this.service.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    this.service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private ResponseEntity<?> createEntity(C dto, BindingResult result) {
    T entity = dto.toEntity();

    if (this.service.findByName(dto.getName()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    if (result.hasErrors()) {
      return RequestUtil.errorResponse(result);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(entity));
  }

}
