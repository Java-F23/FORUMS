import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


import com.google.gson.Gson;

class DataStorageManager {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Report> reports = new ArrayList<>();


    public Map<String, Object> generateStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        // Count the number of users, posts, comments, and reports
        int numberOfUsers = users.size();
        int numberOfPosts = posts.size();
        int numberOfComments = comments.size();
        int numberOfReports = reports.size();

        // Calculate the total number of interactions (upvotes, downvotes, likes, views)
        int totalUpvotes = 0;
        int totalDownvotes = 0;
        int totalLikes = 0;
        int totalViews = 0;

        for (Post post : posts) {
            totalLikes += post.getLikeCount();
            totalViews += post.getViewCount();
        }

        for(Comment comment : comments) {
            totalUpvotes += comment.getUpvotes();
            totalDownvotes += comment.getDownvotes();

        }
        statistics.put("NumberOfUsers", numberOfUsers);
        statistics.put("NumberOfPosts", numberOfPosts);
        statistics.put("NumberOfComments", numberOfComments);
        statistics.put("NumberOfReports", numberOfReports);
        statistics.put("TotalUpvotes", totalUpvotes);
        statistics.put("TotalDownvotes", totalDownvotes);
        statistics.put("TotalLikes", totalLikes);
        statistics.put("TotalViews", totalViews);

        return statistics;
    }

    public void printStatistics(Map<String, Object> statistics) {
        System.out.println("Statistics:");
        System.out.println("Number of Users: " + statistics.get("NumberOfUsers"));
        System.out.println("Number of Posts: " + statistics.get("NumberOfPosts"));
        System.out.println("Number of Comments: " + statistics.get("NumberOfComments"));
        System.out.println("Number of Reports: " + statistics.get("NumberOfReports"));
        System.out.println("Total Upvotes: " + statistics.get("TotalUpvotes"));
        System.out.println("Total Downvotes: " + statistics.get("TotalDownvotes"));
        System.out.println("Total Likes: " + statistics.get("TotalLikes"));
        System.out.println("Total Views: " + statistics.get("TotalViews"));

    }
    public void reportPost(Post post, User reporter, String reason) {
        Report report = new Report(reporter, post, reason);
        reports.add(report);
    }

    public void reportComment(Comment comment, User reporter, String reason) {
        Report report = new Report(reporter, comment, reason);
        reports.add(report);
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void resolveReport(Report report) {
        report.resolveReport();
    }

    public void deleteReportedItem(Report report) {
        Object reportedItem = report.getReportedItem();
        if (reportedItem instanceof Post) {
            deletePost((Post) reportedItem);
        } else if (reportedItem instanceof Comment) {
            deleteComment((Comment) reportedItem);
        }
        reports.remove(report);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public Post getPostById(int postID) {
        for (Post post : posts) {
            if (post.getPostID() == postID) {
                return post;
            }
        }
        return null;
    }

    public void loadTestData(ArrayList<User> users, ArrayList<Post> posts, ArrayList<Comment> comments) {
        this.users = users;
        this.posts = posts;
        this.comments = comments;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Comment getCommentById(int commentID) {
        for (Comment comment : comments) {
            if (comment.getCommentID() == commentID) {
                return comment;
            }
        }
        return null;
    }

    public void loadUsersDataFromJSON(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            User[] userArray = gson.fromJson(reader, User[].class);
            // Add users to the user list
            for (User user : userArray) {

                addUser(new User(user.getUsername(), user.getPassword(), user.getUserType()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading users data from JSON file: " + e.getMessage());
        }
    }

    public void loadPostsDataFromJSON(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            Post[] postArray = gson.fromJson(reader, Post[].class);
            // Assign random authors to the posts and add them to the post list
            for (int i = 0; i < 4; i++) {
                for (Post post : postArray) {
                    User randomAuthor = getRandomUser();

                    if (randomAuthor != null) {
                        post.setAuthor(randomAuthor);
                        addPost(new Post(post.getTitle(), post.getContent(), randomAuthor));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading posts data from JSON file: " + e.getMessage());
        }
    }

    public void loadCommentsDataFromJSON(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            String[] commentDataArray = gson.fromJson(reader, String[].class);

            for(int i = 0; i < 4; i++) {
            for (String commentData : commentDataArray) {
                Post post = getRandomPost(); // Helper method to find the post by ID
                if (post != null) {
                    User randomAuthor = getRandomUser();
                    Comment comment = new Comment(post, randomAuthor, commentData);
                    addComment(comment);
                    post.addComment(comment); // Add the comment to the corresponding post
                }
            }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading comments data from JSON file: " + e.getMessage());
        }
    }

    private Post getRandomPost() {
        List<Post> availablePosts = getPosts();
        if (!availablePosts.isEmpty()) {
            int randomIndex = (int) (Math.random() * availablePosts.size());
            return availablePosts.get(randomIndex);
        }
        return null;
    }

    private User getRandomUser() {
        List<User> availableUsers = getUsers();
        if (!availableUsers.isEmpty()) {
            int randomIndex = (int) (Math.random() * availableUsers.size());
            return availableUsers.get(randomIndex);
        }
        return null;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public ArrayList<Post> filterPostsByAuthor(String authorUsername) {
        ArrayList<Post> filteredPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getAuthor().getUsername().equals(authorUsername)) {
                filteredPosts.add(post);
            }
        }
        return filteredPosts;
    }

    public void upvoteComment(User user, Comment comment) {
        if (user != null && comment != null) {
            comment.upvote(user);
        }
    }

    public void downvoteComment(User user, Comment comment) {
        if (user != null && comment != null) {
            comment.downvote(user);
        }
    }

    public void likePost(User user, Post post) {
        if (user != null && post != null) {
            post.incrementLikeCount(user);
            post.incrementViewCount(user);
        }
    }
    public void viewPost(User user, Post post) {
        if (user != null && post != null) {
            post.incrementViewCount(user);
        }
    }


    public void editPost(Post post, String newTitle, String newContent) {
        post.setTitle(newTitle);
        post.setContent(newContent);
    }

    public void editComment(Comment comment, String newContent) {
        comment.setContent(newContent);
    }

    public void deletePost(Post post) {
        if (posts.contains(post)) {
            ArrayList<Report> reportsToRemove = new ArrayList<>();
            for (Report report : reports) {
                if (report.getReportedItem() == post) {
                    reportsToRemove.add(report);
                }
            }
            reports.removeAll(reportsToRemove);
            posts.remove(post);
        }
    }

    public void deleteComment(Comment comment) {
        if (comments.contains(comment)) {
            ArrayList<Report> reportsToRemove = new ArrayList<>();
            for (Report report : reports) {
                if (report.getReportedItem() == comment) {
                    reportsToRemove.add(report);
                }
            }
            reports.removeAll(reportsToRemove);
            comments.remove(comment);
        }
    }

    public ArrayList<Post> sortPostsByPopularity() {
        ArrayList<Post> sortedPosts = new ArrayList<>(posts);
        sortedPosts.sort(Comparator.comparingInt(Post::getLikeCount).reversed());
        return sortedPosts;
    }

    public ArrayList<Post> sortPostsByDate() {
        ArrayList<Post> sortedPosts = new ArrayList<>(posts);
        sortedPosts.sort(Comparator.comparing(Post::getTimestamp));
        return sortedPosts;
    }

    public ArrayList<Post> searchPosts(String keyword) {
        ArrayList<Post> matchingPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getTitle().contains(keyword) || post.getContent().contains(keyword)) {
                matchingPosts.add(post);
            }
        }
        return matchingPosts;
    }

    public ArrayList<Post> searchPostsByDateRange(Date startDate, Date endDate) {
        ArrayList<Post> matchingPosts = new ArrayList<>();
        for (Post post : posts) {
            Date postTimestamp = post.getTimestamp();
            if (postTimestamp.compareTo(startDate) >= 0 && postTimestamp.compareTo(endDate) <= 0) {
                matchingPosts.add(post);
            }
        }
        return matchingPosts;
    }
}

