package unpsjb.labprog.backend.validations;

public interface Validator<T> {
  void checkFormat(T object);

  void checkUpdate(T object);

  void checkCreate(T object);

  void checkExists(T object);
}
