package reports.challenges;

import entities.challenge.Challenge;
import entities.user.User;

/**
 * Created by ANykytenko on 8/18/2016.
 */
public class Row {

    private int challengeId;

    private User hostUser;      // team 1
    private User otherUser1;    // team 1

    private User receivingUser; // team 2
    private User otherUser2;    // team 2

    private Challenge.Result result;
    private int team1Goals;
    private int team2Goals;

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public User getHostUser() {
        return hostUser;
    }

    public void setHostUser(User hostUser) {
        this.hostUser = hostUser;
    }

    public User getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(User receivingUser) {
        this.receivingUser = receivingUser;
    }

    public Challenge.Result getResult() {
        return result;
    }

    public void setResult(Challenge.Result result) {
        this.result = result;
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

    public void setTeam2Goals(int team2Goals) {
        this.team2Goals = team2Goals;
    }

    public User getOtherUser1() {
        return otherUser1;
    }

    public void setOtherUser1(User otherUser1) {
        this.otherUser1 = otherUser1;
    }

    public User getOtherUser2() {
        return otherUser2;
    }

    public void setOtherUser2(User otherUser2) {
        this.otherUser2 = otherUser2;
    }
}