import java.util.List;
import java.util.ArrayList;

class DataStorageManager {
    private List<User> users = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<AdministrativeAction> adminActions = new ArrayList<>();

    public List<User> readUserDataFromFile() {
        return users;
    }

    public void writeUserDataToFile(List<User> users) {
        this.users = users;
    }

    public void writePostData(Post post) {
        posts.add(post);
    }
    public void saveToDataStorage(Post post) {
        writePostData(post);
    }
    public Post readFromDataStorage(int postID) {
        List<Post> allPosts = readAllPosts();

        for (Post post : allPosts) {
            if (post.getPostID() == postID) {
                return post;
            }
        }
        return null;
    }

    public List<Comment> readCommentDataFromFile() {
        return comments;
    }

    public void writeCommentDataToFile(List<Comment> comments) {
        this.comments = comments;
    }

    public List<AdministrativeAction> readAdminActionDataFromFile() {
        return adminActions;
    }

    public void writeAdminActionDataToFile(List<AdministrativeAction> actions) {
        this.adminActions = actions;
    }
    public List<Post> readAllPosts() {
        return posts;
    }

}
