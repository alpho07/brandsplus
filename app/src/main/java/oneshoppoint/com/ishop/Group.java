package oneshoppoint.com.ishop;

/**
 * Created by stephineosoro on 22/05/16.
 */
import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string,path;
    public final List<String> children = new ArrayList<String>();
    public final List<String> id = new ArrayList<String>();
    public final List<String> image = new ArrayList<String>();

    public Group(String string,String Path) {
        this.string = string;
        this.path=Path;
    }

}