package life.coachy.backend.exercise;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;

public class ExerciseDto extends AbstractDto<Exercise> {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private int sets;
  @NotNull(message = "{notNull}")
  private int reps;
  private int miniSets;
  private ExerciseTemplate template;

  public String getName() {
    return this.name;
  }

  public int getSets() {
    return this.sets;
  }

  public int getReps() {
    return this.reps;
  }

  public int getMiniSets() {
    return this.miniSets;
  }

  public ExerciseTemplate getTemplate() {
    return this.template;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

  @Override
  public Exercise toEntity() {
    return new ExerciseBuilder()
        .withName(this.name)
        .withSets(this.sets)
        .withReps(this.reps)
        .withMiniSets(this.miniSets)
        .withTemplate(this.template)
        .build();
  }

}
