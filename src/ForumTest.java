import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;
import java.io.FileReader;

import com.google.gson.Gson;

// ...

public class ForumTest {
    public static void main(String[] args) {
        DataStorageManager dataStorageManager = new DataStorageManager();

        // Load user, post, and comment data from JSON files
        dataStorageManager.loadUsersDataFromJSON("users_data.json");
        dataStorageManager.loadPostsDataFromJSON("posts_data.json");
        dataStorageManager.loadCommentsDataFromJSON("comments_data.json");

        // Test filtering posts by author
        String testName1 = "Fatima";
        String testName2 = "Muhamemed";

        ArrayList<Post> user1Posts = dataStorageManager.filterPostsByAuthor(testName1);
        ArrayList<Post> user2Posts = dataStorageManager.filterPostsByAuthor(testName2);

        System.out.println(dataStorageManager.getPosts().size());
        System.out.println(dataStorageManager.getComments().size());
        System.out.println(dataStorageManager.getUsers().size());

        System.out.println( testName2 + "'s Posts:");
        user2Posts.forEach(System.out::println);

        System.out.println( testName1 + "'s Posts:");
        user1Posts.forEach(System.out::println);

        // Test upvoting and downvoting comments
        User user = dataStorageManager.getUsers().get(0);
        Post post = dataStorageManager.getPosts().get(0);
        Comment comment = dataStorageManager.getComments().get(0);

        dataStorageManager.upvoteComment(user, comment);
        dataStorageManager.upvoteComment(user, comment);
        dataStorageManager.downvoteComment(user, comment);

        System.out.println("\nComment Upvotes: " + comment.getUpvotes());
        System.out.println("Comment Downvotes: " + comment.getDownvotes());

        // Test liking posts multiple times
        dataStorageManager.likePost(user, post);
        dataStorageManager.likePost(user, post);

        System.out.println("\nPost Likes: " + post.getLikeCount());

        // Test reporting a comment
        User reporter = dataStorageManager.getUsers().get(1);
        dataStorageManager.reportComment(comment, reporter, "Inappropriate content");

        ArrayList<Report> reports = dataStorageManager.getReports();
        System.out.println("\nReports:");
        reports.forEach(System.out::println);

        // Try editing a comment
        dataStorageManager.editComment(comment, "Edited content");

        // Display the edited comment
        System.out.println("\nEdited Comment: " + comment.getContent());

    }
}
