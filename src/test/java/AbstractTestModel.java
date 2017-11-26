public abstract class AbstractTestModel<T> {

    protected T object;

    abstract public void beforeTest();
    abstract public void afterTest();
}
