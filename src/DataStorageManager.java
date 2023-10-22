import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;

class DataStorageManager {
    private List<User> users = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<AdministrativeAction> adminActions = new ArrayList<>();

    private List<Report> reports = new ArrayList<>();


    public void reportPost(Post post, User reporter, String reason) {
        Report report = new Report(reporter, post, reason);
        reports.add(report);
    }

    public void reportComment(Comment comment, User reporter, String reason) {
        Report report = new Report(reporter, comment, reason);
        reports.add(report);
    }

    public List<Report> getReports() {
        return reports;
    }

    public void resolveReport(Report report) {
        report.resolveReport();
    }

    public void deleteReportedItem(Report report) {
        if (report.getReportedItem() instanceof Post) {
            Post post = (Post) report.getReportedItem();
            posts.remove(post);
        } else if (report.getReportedItem() instanceof Comment) {
            Comment comment = (Comment) report.getReportedItem();
            comments.remove(comment);
        }
        reports.remove(report);
    }

    public List<User> readUserDataFromFile() {
        return users;
    }

    public List<Post> searchPosts(String keyword) {
        List<Post> matchingPosts = new ArrayList<>();

        for (Post post : posts) {
            if (post.getTitle().contains(keyword) || post.getContent().contains(keyword)) {
                matchingPosts.add(post);
            }
        }

        return matchingPosts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void writeUserDataToFile(List<User> users) {
        this.users = users;
    }

    public void storePostData(Post post) {
        posts.add(post);
    }

    public void storeCommentData(Comment comment) {
        comments.add(comment);
    }
    public List<Post> filterPostsByAuthor(String authorUsername) {
        List<Post> filteredPosts = new ArrayList<>();

        for (Post post : posts) {
            if (post.getAuthor().getUsername().equals(authorUsername)) {
                filteredPosts.add(post);
            }
        }

        return filteredPosts;
    }

    public void editPost(Post post, String newTitle, String newContent) {
        post.setTitle(newTitle);
        post.setContent(newContent);
    }

    public void editComment(Comment comment, String newContent) {
        comment.setContent(newContent);
    }

    public void deletePost(Post post) {
        // Remove associated reports
        List<Report> reportsToRemove = new ArrayList<>();
        for (Report report : reports) {
            if (report.getReportedItem() == post) {
                reportsToRemove.add(report);
            }
        }
        reports.removeAll(reportsToRemove);

        // Remove the post
        posts.remove(post);
    }

    public List<Post> sortPostsByPopularity() {
        List<Post> sortedPosts = new ArrayList<>(posts);
        // Sort by the number of likes in descending order
        sortedPosts.sort((post1, post2) -> post2.getLikeCount() - post1.getLikeCount());
        return sortedPosts;
    }
    public List<Post> sortPostsByDate() {
        List<Post> sortedPosts = new ArrayList<>(posts);
        sortedPosts.sort(Comparator.comparing(Post::getTimestamp));
        return sortedPosts;
    }


    public void deleteComment(Comment comment) {
        // Remove associated reports
        List<Report> reportsToRemove = new ArrayList<>();
        for (Report report : reports) {
            if (report.getReportedItem() == comment) {
                reportsToRemove.add(report);
            }
        }
        reports.removeAll(reportsToRemove);

        // Remove the comment
        comments.remove(comment);
    }

    public void storeAdminActionData(AdministrativeAction action) {
        adminActions.add(action);
    }

    public List<AdministrativeAction> readAdminActionDataFromFile() {
        return adminActions;
    }

    public List<Post> readAllPosts() {
        return posts;
    }

    public List<Post> searchPostsByDateRange(Date startDate, Date endDate) {
        List<Post> matchingPosts = new ArrayList<>();

        for (Post post : posts) {
            Date postTimestamp = post.getTimestamp();
            if (postTimestamp.compareTo(startDate) >= 0 && postTimestamp.compareTo(endDate) <= 0) {
                matchingPosts.add(post);
            }
        }

        return matchingPosts;
    }

}
