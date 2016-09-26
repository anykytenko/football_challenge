package entities.challenge;

import java.util.Date;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public class Challenge {
    private int id;
    private int otherUser1Id;
    private int otherUser2Id;
    private int hostUserId;
    private int receivingUserId;
    private Status status;
    private int team1Goals;
    private int team2Goals;
    private Date dateOfCreation;
    private Date dateOfClosing;

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfClosing() {
        return dateOfClosing;
    }

    public void setDateOfClosing(Date dateOfClosing) {
        this.dateOfClosing = dateOfClosing;
    }

    public int getTeam1Goals() {
        return team1Goals;
    }

    public void setTeam1Goals(int team1Goals) {
        this.team1Goals = team1Goals;
    }

    public int getTeam2Goals() {
        return team2Goals;
    }

    public void setTeam2Goals(int team2goals) {
        this.team2Goals = team2goals;
    }

    public int getHostUserId() {
        return hostUserId;
    }

    public void setHostUserId(int hostUserId) {
        this.hostUserId = hostUserId;
    }

    public int getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(int receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtherUser1Id() {
        return otherUser1Id;
    }

    public void setOtherUser1Id(int otherUser1Id) {
        this.otherUser1Id = otherUser1Id;
    }

    public int getOtherUser2Id() {
        return otherUser2Id;
    }

    public void setOtherUser2Id(int otherUser2Id) {
        this.otherUser2Id = otherUser2Id;
    }

    public Result getResult() {
        if (getTeam1Goals() == getTeam2Goals()) {
            return Result.DRAW;
        } else if (getTeam1Goals() > getTeam2Goals()) {
            return Result.TEAM1_WON;
        } else {
            return Result.TEAM2_WON;
        }
    }

    public boolean isReady() {
        return hostUserId != -1 && receivingUserId != -1 && otherUser1Id != -1 && otherUser2Id != -1;
    }

    public enum Result {
        DRAW(0),
        TEAM1_WON(1),
        TEAM2_WON(2);

        final int id;

        Result(int id) {
            this.id = id;
        }

        public static Result getById(int id) {
            for (Result result : Result.values()) {
                if (result.id == id) {
                    return result;
                }
            }
            return null;
        }
    }

    public enum Status {
        IN_PROGRESS(0), // Challenge in progress
        APPROVED(1),    // Challenge is approved by user2
        REJECTED(2),    // Challenge is rejected by user2
        CLOSED(3);      // Challenge is closed with scores, for, example 19:19

        public final int id;

        Status(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Status getById(int id) {
            for (Status status : Status.values()) {
                if (status.id == id) {
                    return status;
                }
            }
            return null;
        }

    }
}
