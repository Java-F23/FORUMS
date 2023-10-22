import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class Main {public static void main(String[] args) {
    // Create a DataStorageManager
    DataStorageManager dataStorageManager = new DataStorageManager();

    // Create users and save them
    User user1 = new User(1, "user1", "password1", "regular user");
    User user2 = new User(2, "user2", "password2", "administrator");
    dataStorageManager.writeUserDataToFile(List.of(user1, user2));

    // Create and save posts
    Post post1 = new Post().createPost("Post 1 Title", "This is the content of Post 1.", user1);
    Post post2 = new Post().createPost("Java Programming", "Java is a great programming language.", user2);
    Post post3 = new Post().createPost("Post 3 Title", "This is the content of Post 3.", user1);

    // Create and save comments
    Comment comment1 = new Comment(1, post1, user2, "This is a comment on Post 1.");
    Comment comment2 = new Comment(2, post1, user1, "Another comment on Post 1.");
    Comment comment3 = new Comment(3, post2, user1, "Comment on Post 2.");

    // Add comments to the DataStorageManager
    dataStorageManager.writeCommentDataToFile(List.of(comment1, comment2, comment3));

    // Associate comments with posts
    post1.addComment(comment1);
    post1.addComment(comment2);
    post2.addComment(comment3);

    // Save the posts to the DataStorageManager
    dataStorageManager.saveToDataStorage(post1);
    dataStorageManager.saveToDataStorage(post2);
    dataStorageManager.saveToDataStorage(post3);

    // Upvote and downvote posts and comments
    post1.upvote();
    post2.downvote();
    comment1.upvote();
    comment2.upvote();
    comment3.downvote();

    List<Post> matchingPosts = dataStorageManager.searchPosts("Java");

    // Retrieve and display all posts
    List<Post> allPosts = dataStorageManager.readAllPosts();
    System.out.println("All Posts:");
    for (Post post : allPosts) {
        System.out.println("Title: " + post.getTitle());
        System.out.println("Content: " + post.getContent());
        System.out.println("Author: " + post.getAuthor().getUsername());

        // Display comments
        System.out.println("Comments:");
        for (Comment comment : post.getComments()) {
            System.out.println("Comment by: " + comment.getAuthor().getUsername());
            System.out.println("Content: " + comment.getContent());
            System.out.println("Upvotes: " + comment.getUpvotes());
            System.out.println("Downvotes: " + comment.getDownvotes());
        }

        System.out.println("Upvotes: " + post.getUpvotes());
        System.out.println("Downvotes: " + post.getDownvotes());

        System.out.println("------------------------------");
    }

    System.out.println("Matching Posts:");
    for (Post post : matchingPosts) {
        System.out.println("Title: " + post.getTitle());
        System.out.println("Content: " + post.getContent());
        System.out.println("Author: " + post.getAuthor().getUsername());
        System.out.println("------------------------------");
    }

}





}