import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        // Save the posts to the DataStorageManager
        dataStorageManager.storePostData(post1);
        dataStorageManager.storePostData(post2);
        dataStorageManager.storePostData(post3);

        // User 1 marks post 1 as a favorite
        user1.addFavoritePost(post1);

        // User 2 marks post 2 as a favorite
        user2.addFavoritePost(post2);

        // User 2 unmarks post 2 as a favorite
        user2.removeFavoritePost(post2);

        // Retrieve and display favorite posts for users
        System.out.println("User 1's Favorite Posts:");
        List<Post> favoritePostsUser1 = user1.getFavoritePosts();
        for (Post post : favoritePostsUser1) {
            System.out.println("Title: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("------------------------------");
        }

        System.out.println("User 2's Favorite Posts:");
        List<Post> favoritePostsUser2 = user2.getFavoritePosts();
        for (Post post : favoritePostsUser2) {
            System.out.println("Title: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("------------------------------");
        }
    }
}
