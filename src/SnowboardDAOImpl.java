import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Racet_000 on 03.12.2014.
 */
public class SnowboardDAOImpl implements SnowboardDAO {
    public long addSnowboard(Snowboard snowboard) throws SQLException{
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(snowboard);
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Bad");
        }finally {
            if(session != null &&session.isOpen())
                session.close();
        }
        return snowboard.getId();
    }

    public  void updateSnowboard(Snowboard snowboard)throws  SQLException{
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(snowboard);
            session.getTransaction().commit();
        }catch (Exception e)
        {
            System.out.println("Bad");
        }finally {
            if(session != null && session.isOpen())
                session.close();
        }
    }

    public void deleteSnowboard(Snowboard snowboard) throws SQLException{
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(snowboard);
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Bad");
        }finally {
            if(session != null && session.isOpen())
                session.close();
        }
    }

    public List<Snowboard> getAllSnowboard() throws SQLException{
        Session session = null;
        List<Snowboard> snows = new ArrayList<Snowboard>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            snows = session.createCriteria(Snowboard.class).list();
        }catch (Exception e){
            System.out.println("Bad");
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return snows;
    }
}
