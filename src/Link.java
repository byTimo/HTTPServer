/**
 * Created by Racet_000 on 29.11.2014.
 */
public class Link {
    String URI;
    String Name;

    public Link(String URI, String name)
    {
        this.URI = URI;
        this.Name = name;
    }

    public String getURI()
    {
        return this.URI;
    }
    public String getName()
    {
        return this.Name;
    }
}
