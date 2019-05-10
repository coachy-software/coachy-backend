package life.coachy.backend.exercise.template;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import life.coachy.backend.exercise.template.domain.ExerciseTemplateFacade;
import life.coachy.backend.exercise.template.query.ExerciseTemplateQueryBinder;
import life.coachy.backend.exercise.template.query.ExerciseTemplateQueryDto;
import life.coachy.backend.exercise.template.query.ExerciseTemplateQueryDtoRepository;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.EXERCISE_TEMPLATES)
class ExerciseTemplateEndpoints {

  private final QueryOperationsFactory queryOperationsFactory;
  private final ExerciseTemplateQueryDtoRepository repository;
  private final ExerciseTemplateFacade facade;

  @Autowired
  public ExerciseTemplateEndpoints(QueryOperationsFactory queryOperationsFactory, ExerciseTemplateQueryDtoRepository repository,
      ExerciseTemplateFacade facade) {
    this.queryOperationsFactory = queryOperationsFactory;
    this.repository = repository;
    this.facade = facade;
  }

  @RequiresAuthenticated
  @ApiOperation("Displays exercise templates that matches criteria, only if criteria present, otherwise displays all")
  @GetMapping
  public ResponseEntity<List<ExerciseTemplateQueryDto>> fetchAll(@QuerydslPredicate(bindings = ExerciseTemplateQueryBinder.class) Predicate predicate,
      Pageable pageable) {
    return ResponseEntity.ok(this.queryOperationsFactory.obtainOperation(predicate, pageable, this.repository));
  }

  @RequiresAuthenticated
  @ApiOperation("Displays specified exercise template by identifier")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Template not found"),
      @ApiResponse(code = 200, message = "Successfully displayed")
  })
  @GetMapping("{id}")
  public ResponseEntity<ExerciseTemplateQueryDto> fetchOne(@PathVariable ObjectId id) {
    return ResponseEntity.ok(this.facade.fetchOne(id));
  }

}
