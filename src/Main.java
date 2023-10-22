import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class Main {
        public static void main(String[] args) {
            // Create a DataStorageManager
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

            // Retrieve and display all posts
            List<Post> allPosts = dataStorageManager.readAllPosts();
            System.out.println("All Posts:");
            for (Post post : allPosts) {
                System.out.println("Title: " + post.getTitle());
                System.out.println("Content: " + post.getContent());
                System.out.println("Author: " + post.getAuthor().getUsername());
                System.out.println("Date: " + post.getTimestamp());

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
        }



}