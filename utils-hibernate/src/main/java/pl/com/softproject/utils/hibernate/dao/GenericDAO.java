package pl.com.softproject.utils.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Genieric DAO interface
 *
 * @author Adrian Lapierre 
 * @param <T> - domain object type
 * @param <PK> - primary key type
 */
public interface GenericDAO <T, PK extends Serializable> {

    /** Persist the newInstance object into database */
    PK create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    T load(PK id);

    /** 
     * Save changes made to a persistent object.  
     */
    void update(T transientObject);
    
    /** 
     * Save changes made to a persistent object.  
     */
    public void saveOrUpdate(T o);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);
    
    void flush();
    
    /**
     * returns all entity
     * @return all entity list
     */
    public List<T> searchAll();

    /**
     *
     * @param sortProperity
     * @param ascending
     * @return
     */
    public List<T> searchAllOrdered(String sortProperity, boolean ascending);
    
    public List<T> searchAllOrdered(final String sortProperity, final boolean ascending, final boolean distinct);
    
    /**
     * Wyszukuje encje wed³ug podanego przyk³adu. Wszystkie w³aœciwoœci, ró¿ne od null i 0 
     * bêd¹ brane pod uwagê. Do porównywania stringów u¿ywany jest operator like, wielkoœæ znaków
     * jest ignorowana.
     * 
     * @param exampleEntity przyk³adowa encja
     * @return lista encji
     */
    public List<T> searchByExample(final T exampleEntity);
    
    /**
     * Wyszukuje encje wed³ug podanego przyk³adu i zwraca posortowan¹ listê
     * @param exampleEntity przyk³adowa encja
     * @param sortProperity nazwa w³aœciwoœci po któej ma siê sotrowaæ
     * @param ascending czy rosn¹co czy malej¹co
     * @return
     */
    public List<T> searchByExample(final T exampleEntity, String sortProperity, boolean ascending);
    
    /**
     * Wyszukuje encje wed³ug podanego przyk³adu. W³aœciwoœci o nazwach podanych w  excludeProperty
     * nie bêd¹ porównywane.
     * 
     * @param exampleEntity przyk³adowa encja
     * @param excludeProperty lista w³aœciwoœci, które maj¹ nie byæ uwzglêdnione
     * @return lista encji
     */
    public List<T> searchByExample(final T exampleEntity, List<String> excludeProperty);

    void persist(T o);

    List<T> searchByCriteria(DetachedCriteria criteria);

    List<T> searchByHQLQuery(String query, Object... params);
    
    Object executeHibernateCallback(HibernateCallback callback);
}