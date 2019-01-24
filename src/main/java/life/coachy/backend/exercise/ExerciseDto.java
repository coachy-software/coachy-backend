package life.coachy.backend.exercise;

import life.coachy.backend.util.AbstractDto;
import org.bson.types.ObjectId;

public class ExerciseDto extends AbstractDto<Exercise> {

  private ObjectId identifier;
  private String name;
  private int sets;
  private int reps;
  private int miniSets;
  private ExerciseTemplate template;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

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
        .withIdentifier(this.identifier)
        .withName(this.name)
        .withSets(this.sets)
        .withReps(this.reps)
        .withMiniSets(this.miniSets)
        .withTemplate(this.template)
        .build();
  }

}
