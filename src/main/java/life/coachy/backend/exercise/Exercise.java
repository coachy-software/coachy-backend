package life.coachy.backend.exercise;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

class Exercise implements IdentifiableEntity<ObjectId> {

  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId identifier;
  private String name;
  private int sets;
  private int reps;
  private int miniSets;
//  private ExerciseTemplate template;

  Exercise(ExerciseBuilder builder) {
    this.name = builder.name;
    this.sets = builder.sets;
    this.reps = builder.reps;
    this.miniSets = builder.miniSets;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSets() {
    return this.sets;
  }

  public void setSets(int sets) {
    this.sets = sets;
  }

  public int getReps() {
    return this.reps;
  }

  public void setReps(int reps) {
    this.reps = reps;
  }

  public int getMiniSets() {
    return this.miniSets;
  }

  public void setMiniSets(int miniSets) {
    this.miniSets = miniSets;
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

}
