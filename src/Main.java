import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Initialize DataStorageManager
        DataStorageManager dataStorageManager = new DataStorageManager();

        // Create Users
        User user1 = new User("user1", "password1", "regular user");
        User user2 = new User("user2", "password2", "administrator");
        User user3 = new User("user3", "password3", "regular user");
        User user4 = new User("user4", "password4", "regular user");

        dataStorageManager.writeUserDataToFile(List.of(user1, user2, user3, user4));

        // Create Posts
        Post post1 = new Post("Post 1 Title", "This is the content of Post 1.", user1);
        Post post2 = new Post("Java Programming", "Java is a great programming language.", user2);
        Post post3 = new Post("Post 3 Title", "This is the content of Post 3.", user1);

        dataStorageManager.storePostData(post1);
        dataStorageManager.storePostData(post2);
        dataStorageManager.storePostData(post3);

        // Create Comments
        Comment comment1 = new Comment(post1, user2, "This is a comment on Post 1.");
        Comment comment2 = new Comment(post1, user1, "Another comment on Post 1.");
        Comment comment3 = new Comment(post2, user1, "Comment on Post 2.");

        dataStorageManager.storeCommentData(comment1);
        dataStorageManager.storeCommentData(comment2);
        dataStorageManager.storeCommentData(comment3);

        // Report Posts and Comments
        dataStorageManager.reportPost(post1, user2, "Inappropriate content");
        dataStorageManager.reportComment(comment2, user2, "Spam");

        // Resolve and Delete Reported Items
        List<Report> reports = dataStorageManager.getReports();
        for (Report report : reports) {
            dataStorageManager.resolveReport(report);
            dataStorageManager.deleteReportedItem(report);
        }

        // Likes and view counts
        post1.incrementLikeCount();
        post2.incrementViewCount();
        comment1.upvote();
        comment2.upvote();
        comment3.downvote();

        // Edit Posts and Comments
        dataStorageManager.editPost(post1, "Updated Title", "Updated content of Post 1");
        dataStorageManager.editComment(comment1, "Updated comment on Post 1");

        // Delete Posts and Comments
        dataStorageManager.deletePost(post3);
        dataStorageManager.deleteComment(comment3);

        // View Posts
        List<Post> allPosts = dataStorageManager.readAllPosts();
        for (Post post : allPosts) {
            System.out.println("Post: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("Likes: " + post.getLikeCount());
            System.out.println("Views: " + post.getViewCount());

            System.out.println("Comments:");
            for (Comment comment : post.getComments()) {
                System.out.println("Comment by: " + comment.getAuthor().getUsername());
                System.out.println("Content: " + comment.getContent());
                System.out.println("Upvotes: " + comment.getUpvotes());
                System.out.println("Downvotes: " + comment.getDownvotes());
            }

            System.out.println("Likes : " + post.getLikeCount());
            System.out.println("View : " + post.getViewCount());
            System.out.println("------------------------------");
        }

        // Sort Posts
        List<Post> sortedByDate = dataStorageManager.sortPostsByDate();
        List<Post> sortedByPopularity = dataStorageManager.sortPostsByPopularity();

        System.out.println("Posts Sorted by Date:");
        for (Post post : sortedByDate) {
            System.out.println("Post: " + post.getTitle());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("Likes: " + post.getLikeCount());
            System.out.println("Views: " + post.getViewCount());
            System.out.println("------------------------------");
        }

        System.out.println("Posts Sorted by Popularity:");
        for (Post post : sortedByPopularity) {
            System.out.println("Post: " + post.getTitle());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("Likes: " + post.getLikeCount());
            System.out.println("Views: " + post.getViewCount());
            System.out.println("------------------------------");
        }

        // Filter Posts by Author
        List<Post> user1Posts = dataStorageManager.filterPostsByAuthor("user1");
        System.out.println("Posts by user1:");
        for (Post post : user1Posts) {
            System.out.println("Post: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("Likes: " + post.getLikeCount());
            System.out.println("Views: " + post.getViewCount());
            System.out.println("------------------------------");
        }

        // Search Posts by Keywords
        List<Post> matchingPosts = dataStorageManager.searchPosts("Java");
        System.out.println("Posts containing 'Java':");
        for (Post post : matchingPosts) {
            System.out.println("Post: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("Likes: " + post.getLikeCount());
            System.out.println("Views: " + post.getViewCount());
            System.out.println("------------------------------");
        }

        // Search Posts by Date Range
        Date startDate = new Date(2023, 6, 1);
        Date endDate = new Date(2023, 6, 30);
        List<Post> postsInDateRange = dataStorageManager.searchPostsByDateRange(startDate, endDate);
        System.out.println("Posts from June 2023:");
        for (Post post : postsInDateRange) {
            System.out.println("Post: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("Likes: " + post.getLikeCount());
            System.out.println("Views: " + post.getViewCount());
            System.out.println("------------------------------");
        }

        // Mark Posts as Favorites
        user1.addFavoritePost(post1);
        user1.addFavoritePost(post2);

        // View Favorite Posts
        List<Post> favoritePosts = user1.getFavoritePosts();
        System.out.println("Favorite Posts for user1:");
        for (Post post : favoritePosts) {
            System.out.println("Post: " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Date: " + post.getTimestamp());
            System.out.println("Likes: " + post.getLikeCount());
            System.out.println("Views: " + post.getViewCount());
            System.out.println("------------------------------");
        }

        // Report Posts and Comments
        dataStorageManager.reportPost(post3, user4, "Inappropriate content");
        dataStorageManager.reportComment(comment2, user3, "Offensive language");

        // View Reports
        List<Report> reportList = dataStorageManager.getReports();
        System.out.println("Reports:");
        for (Report report : reportList) {
            System.out.println("Reporter: " + report.getReporter().getUsername());
            System.out.println("Reason: " + report.getReason());
            if (report.getReportedItem() instanceof Post) {
                Post reportedPost = (Post) report.getReportedItem();
                System.out.println("Reported Post: " + reportedPost.getTitle());
            } else if (report.getReportedItem() instanceof Comment) {
                Comment reportedComment = (Comment) report.getReportedItem();
                System.out.println("Reported Comment: " + reportedComment.getContent());
            }
            System.out.println("------------------------------");
        }

        // Resolve Reports
        dataStorageManager.resolveReport(reportList.get(0));
        dataStorageManager.resolveReport(reportList.get(1));

        // Delete Reported Items
        dataStorageManager.deleteReportedItem(reportList.get(0));
        dataStorageManager.deleteReportedItem(reportList.get(1));
    }
}
