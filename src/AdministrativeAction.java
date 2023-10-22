
class AdministrativeAction {
    private int actionID;
    private User actor;
    private String target;
    private String actionType;
    private String timestamp;

    public AdministrativeAction() {
        // Constructor
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int id) {
        this.actionID = id;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void saveToFile() {
        // Implement code to save administrative action data to a file
    }

    public void readFromFile() {
        // Implement code to read administrative action data from a file
    }
}
