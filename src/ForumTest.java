import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;
import java.io.FileReader;
import java.util.Random;
import com.google.gson.Gson;

public class ForumTest {
    public static void main(String[] args) {
        ProjectPopulator populate = new ProjectPopulator();
        populate.main(args);
        DataStorageManager dataStorageManager = populate.getDataStorageManager();
        System.out.println(dataStorageManager.getPosts().size());



    }
}
