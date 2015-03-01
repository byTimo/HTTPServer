import java.sql.SQLException;
import java.util.List;

/**
 * Created by Racet_000 on 03.12.2014.
 */
public interface SnowboardDAO {
    public long addSnowboard(Snowboard snowboard) throws SQLException;
    public void updateSnowboard(Snowboard snowboard) throws SQLException;
    public void deleteSnowboard(Snowboard snowboard) throws SQLException;
    public List<Snowboard> getAllSnowboard() throws SQLException;
}
