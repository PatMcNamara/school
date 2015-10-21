package sparse_array;

/**
 * An single element of the sparse array.
 * @author pjmcnamara2
 */
public interface MatrixElem<E>
{
    public abstract int rowIndex();
    public abstract int columnIndex();
    public abstract E value();
}