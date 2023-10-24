import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectPopulator {
    public static void main(String[] args) {
        DataStorageManager dataStorageManager = new DataStorageManager();

        Map<String, Object> statistics = dataStorageManager.generateStatistics();
        System.out.println("Before population: ");
        dataStorageManager.printStatistics(statistics);

        // Load user, post, and comment data from JSON files
        dataStorageManager.loadUsersDataFromJSON("users_data.json");
        dataStorageManager.loadPostsDataFromJSON("posts_data.json");
        dataStorageManager.loadCommentsDataFromJSON("comments_data.json");


        // Perform various operations on the loaded data
        User user = new User();
        Post post = new Post();
        Comment comment = new Comment();


        // Test upvoting and downvoting comments, liking posts, and viewing posts
        for (int i = 0; i < 68; i++) {
            user = dataStorageManager.getUsers().get(i % dataStorageManager.getUsers().size());
            post = dataStorageManager.getPosts().get(i % dataStorageManager.getPosts().size());
            comment = dataStorageManager.getComments().get(i % dataStorageManager.getComments().size());

            dataStorageManager.upvoteComment(user, comment);
            dataStorageManager.likePost(user, post);
            dataStorageManager.downvoteComment(user, comment);
            dataStorageManager.viewPost(user, post);
        }

        System.out.println("");
        System.out.println("After population: ");
        statistics = dataStorageManager.generateStatistics();
        dataStorageManager.printStatistics(statistics);


    }
}
