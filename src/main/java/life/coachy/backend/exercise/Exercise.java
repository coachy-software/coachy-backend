package life.coachy.backend.exercise;


import life.coachy.backend.util.IdentifiableEntity;

class Exercise implements IdentifiableEntity<String> {

  private String name;
  private int sets;
  private int reps;
  private int miniSets;
  private ExerciseTemplate template;

  Exercise(ExerciseBuilder builder) {
    this.name = builder.name;
    this.sets = builder.sets;
    this.reps = builder.reps;
    this.miniSets = builder.miniSets;
    this.template = builder.template;
  }

  Exercise() {
  }

  @Override
  public String getIdentifier() {
    return this.name;
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

  public ExerciseTemplate getTemplate() {
    return this.template;
  }

  public void setTemplate(ExerciseTemplate template) {
    this.template = template;
  }

  @Override
  public String toString() {
    return "Exercise{" +
        "name='" + this.name + '\'' +
        ", sets=" + this.sets +
        ", reps=" + this.reps +
        ", miniSets=" + this.miniSets +
        ", template=" + this.template +
        '}';
  }

}
