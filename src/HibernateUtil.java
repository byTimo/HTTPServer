import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Racet_000 on 04.12.2014.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
