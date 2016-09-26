package reports.ranks;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public class Row {
    private String firstName;
    private String lastName;
    private String userName;
    private int userId;
    private boolean active;
    private RowState rowState;
    private int pointsCount;
    private float pointsAverage;
    private int lossesCount;
    private int winsCount;
    private int challengesCount;
    private int drawsCount;
    private int scored;
    private int conceded;
    private String linkToAvatar;

    public float getPointsAverage() {
        return pointsAverage;
    }

    public void setPointsAverage(float pointsAverage) {
        this.pointsAverage = pointsAverage;
    }

    public String getLinkToAvatar() {
        return linkToAvatar;
    }

    public void setLinkToAvatar(String linkToAvatar) {
        this.linkToAvatar = linkToAvatar;
    }

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }

    public int getConceded() {
        return conceded;
    }

    public void setConceded(int conceded) {
        this.conceded = conceded;
    }

    public int getLossesCount() {
        return lossesCount;
    }

    public int getWinsCount() {
        return winsCount;
    }

    public int getChallengesCount() {
        return challengesCount;
    }

    public int getDrawsCount() {
        return drawsCount;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RowState getRowState() {
        return rowState;
    }

    public void setRowState(RowState rowState) {
        this.rowState = rowState;
    }

    public void setLossesCount(int lossesCount) {
        this.lossesCount = lossesCount;
    }

    public void setWinsCount(int winsCount) {
        this.winsCount = winsCount;
    }

    public void setChallengesCount(int challengesCount) {
        this.challengesCount = challengesCount;
    }

    public void setDrawsCount(int drawsCount) {
        this.drawsCount = drawsCount;
    }

    public enum RowState {
        NOT_ACTIVE_USER,
        ME,
        CHALLENGE,
        WAITING_FOR_RESPONSE,
        APPROVED,
        REJECTED,
        APPROVE_OR_REJECT
    }
}
