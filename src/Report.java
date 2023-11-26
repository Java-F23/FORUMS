import java.util.Date;

class Report {
    private int reportID;
    private User reporter;
    private Object reportedItem; // Can be a Post or Comment
    private String reason;
    private boolean resolved;
    private Date timestamp;

    public Report(User reporter, Object reportedItem, String reason) {
        this.reportID = generateUniqueReportID();
        this.reporter = reporter;
        this.reportedItem = reportedItem;
        this.reason = reason;
        this.resolved = false;
        this.timestamp = new Date();
    }

    private static int nextReportID = 1;

    private int generateUniqueReportID() {
        int uniqueID = nextReportID;
        nextReportID++;
        return uniqueID;
    }

    public int getReportID() {
        return reportID;
    }

    public User getReporter() {
        return reporter;
    }

    public Object getReportedItem() {
        return reportedItem;
    }

    public String getReason() {
        return reason;
    }

    public boolean isResolved() {
        return resolved;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void resolveReport() {
        resolved = true;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportID=" + reportID +
                ", reporter=" + reporter +
                ", reportedItem=" + reportedItem +
                ", reason='" + reason + '\'' +
                ", resolved=" + resolved +
                ", timestamp=" + timestamp +
                '}';
    }
}
