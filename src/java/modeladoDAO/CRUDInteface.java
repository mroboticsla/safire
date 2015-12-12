package modeladoDAO;

import java.util.List;

/**
 *
 * @author Nieto Mendoza
 */
public interface CRUDInteface {
    public String create (Object obj);
    public List<?> read ();
    public String update (Object obj);
    public String delete (Object obj);
    public List<?> search(Object obj);
}
