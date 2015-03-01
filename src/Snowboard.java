import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Racet_000 on 04.12.2014.
 */
@Entity
@Table(name = "Snowboard")
public class Snowboard {
    private long id;
    private String name;
    private String brand;
    private String style;

    public Snowboard(){
        name = null;
    }
    public Snowboard(Snowboard s){
        name = s.getName();
    }
    public Snowboard(String n, String b, String s){
        name = n;
        brand = b;
        style = s;
    }
    public Snowboard(String i,String n, String b, String s){
        id = Long.parseLong(i);
        name = n;
        brand = b;
        style = s;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public long getId(){
        return  id;
    }

    @Column(name = "name")
    public  String getName(){
        return name;
    }

    @Column(name = "brand")
    public String getBrand(){
        return brand;
    }

    @Column(name = "style")
    public String getStyle(){
        return style;
    }

    public void setId(long i){
        id = i;
    }

    public void setName(String n){
        name = n;
    }
    public void setBrand(String b){
        brand = b;
    }
    public void setStyle(String s){
        style =s;
    }
}
