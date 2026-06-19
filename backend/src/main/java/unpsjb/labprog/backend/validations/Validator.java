package unpsjb.labprog.backend.validations;

public interface Validator<T> {
    void checkCreate(T entity);
    void checkUpdate(T entity);
    void checkFormat(T entity);
}
