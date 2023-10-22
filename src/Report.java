class Report {
    private int reportID;
    private User reporter;
    private Object reportedItem; // Can be a Post or Comment
    private String reason;
    private boolean resolved;

    public Report(User reporter, Object reportedItem, String reason) {
        this.reportID = generateUniqueReportID();
        this.reporter = reporter;
        this.reportedItem = reportedItem;
        this.reason = reason;
        this.resolved = false;
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

    public void resolveReport() {
        resolved = true;
    }
}
