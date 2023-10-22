import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        // Create a DataStorageManager
        DataStorageManager dataStorageManager = new DataStorageManager();

        // Create users and save them
        User user1 = new User(1, "user1", "password1", "regular user");
        User user2 = new User(2, "user2", "password2", "administrator");
        dataStorageManager.writeUserDataToFile(List.of(user1, user2));

        // Create and save posts
        Post post1 = new Post().createPost("Post 1 Title", "This is the content of Post 1.", user1);
        Post post2 = new Post().createPost("Post 2 Title", "This is the content of Post 2.", user2);
        Post post3 = new Post().createPost("Post 3 Title", "This is the content of Post 3.", user1);
        dataStorageManager.writePostData(post1);
        dataStorageManager.writePostData(post2);
        dataStorageManager.writePostData(post3);

        // Retrieve and display all posts
        List<Post> allPosts = dataStorageManager.readAllPosts();
        System.out.println("All Posts:");
        for (Post post : allPosts) {
            System.out.println("Title: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("------------------------------");
        }

        // Retrieve a specific post by postID
        int postID = post1.getPostID(); // Replace with the postID you want to retrieve
        Post retrievedPost = dataStorageManager.readFromDataStorage(postID);
        if (retrievedPost != null) {
            System.out.println("Retrieved Post Details:");
            System.out.println("Title: " + retrievedPost.getTitle());
            System.out.println("Content: " + retrievedPost.getContent());
            System.out.println("Author: " + retrievedPost.getAuthor().getUsername());
        } else {
            System.out.println("Post not found.");
        }
    }


}