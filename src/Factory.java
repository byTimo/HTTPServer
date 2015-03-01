/**
 * Created by Racet_000 on 04.12.2014.
 */
public class Factory {


    private static SnowboardDAO snowboardDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if(instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public SnowboardDAO getSnowboardDAO() {
        if (snowboardDAO == null) {
            snowboardDAO = new SnowboardDAOImpl();
        }
        return snowboardDAO;
    }
}
