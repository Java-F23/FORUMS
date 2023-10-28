import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectPopulator {
    public static DataStorageManager populate() {

        DataStorageManager dataStorageManager = getDataStorageManager();

        // Perform various operations on the loaded data
        User user = new User();
        Post post = new Post();
        Comment comment = new Comment();


        // Test upvoting and downvoting comments, liking posts, and viewing posts
        for (int i = 0; i < 105; i++) {
            user = dataStorageManager.getUsers().get(i % dataStorageManager.getUsers().size());
            post = dataStorageManager.getPosts().get(i % dataStorageManager.getPosts().size());
            comment = dataStorageManager.getComments().get(i % dataStorageManager.getComments().size());

            dataStorageManager.upvoteComment(user, comment);
            dataStorageManager.likePost(user, post);
            dataStorageManager.downvoteComment(user, comment);
            dataStorageManager.viewPost(user, post);
        }


        return dataStorageManager;


    }

    public static DataStorageManager getDataStorageManager() {
        DataStorageManager dataStorageManager = new DataStorageManager();

        Map<String, Object> statistics = dataStorageManager.generateStatistics();
        System.out.println("Before population: ");

        // Load user, post, and comment data from JSON files
        dataStorageManager.loadUsersDataFromJSON("users_data.json");
        dataStorageManager.loadPostsDataFromJSON("posts_data.json");
        dataStorageManager.loadCommentsDataFromJSON("comments_data.json");
        return dataStorageManager;
    }
}
