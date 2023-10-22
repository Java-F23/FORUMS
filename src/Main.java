import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        DataStorageManager dataStorageManager = new DataStorageManager();
        // Create users and save them
        User user1 = new User("user1", "password1", "regular user");
        User user2 = new User("user2", "password2", "administrator");
        dataStorageManager.writeUserDataToFile(List.of(user1, user2));

        // Create and save posts
        Post post1 = new Post("Post 1 Title", "This is the content of Post 1.", user1);
        Post post2 = new Post("Java Programming", "Java is a great programming language.", user2);
        Post post3 = new Post("Post 3 Title", "This is the content of Post 3.", user1);

        dataStorageManager.storePostData(post1);
        dataStorageManager.storePostData(post2);
        dataStorageManager.storePostData(post3);

        // Create and save comments
        Comment comment1 = new Comment(post1, user2, "This is a comment on Post 1.");
        Comment comment2 = new Comment(post1, user1, "Another comment on Post 1.");
        Comment comment3 = new Comment(post2, user1, "Comment on Post 2.");

        dataStorageManager.storeCommentData(comment1);
        dataStorageManager.storeCommentData(comment2);
        dataStorageManager.storeCommentData(comment3);

        // Associate comments with posts
        post1.addComment(comment1);
        post1.addComment(comment2);
        post2.addComment(comment3);

        // Edit a post and a comment
        dataStorageManager.editPost(post1, "Edited Title for Post 1", "Updated content for Post 1");
        dataStorageManager.editComment(comment1, "Edited content for Comment 1");

        // Delete a post and a comment
        dataStorageManager.deletePost(post2);
        dataStorageManager.deleteComment(comment2);

// Define a date range (e.g., one week ago to today)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());  // Current date
        calendar.add(Calendar.DAY_OF_YEAR, -7);  // Subtract 7 days to get the start date
        Date startDate = calendar.getTime();
        Date endDate = new Date();  // Current date

        List<Post> matchingPosts = dataStorageManager.searchPostsByDateRange(startDate, endDate);

// Print the matching posts
        System.out.println("Matching Posts:");
        for (Post post : matchingPosts) {
            System.out.println("Title: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("------------------------------");
        }
    }


}