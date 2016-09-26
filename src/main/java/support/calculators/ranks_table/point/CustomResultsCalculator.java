package support.calculators.ranks_table.point;

import entities.challenge.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.holders.ChallengeHolder;

import java.util.List;

/**
 * Created by ANykytenko on 8/17/2016.
 */
@Component
public class CustomResultsCalculator implements ResultsCalculator {

    @Autowired
    private ChallengeHolder challengeHolder;

    public Results calculate(int userId) {
        Results results = new Results();
        int lossesCount = 0;
        int winsCount = 0;
        int drawsCount = 0;
        int scored = 0;
        int conceded = 0;
        List<Challenge> challenges = challengeHolder.ALL.BY_USER.getClosed(userId);
        for (Challenge challenge : challenges) {
            Challenge.Result result = challenge.getResult();
            if (result == Challenge.Result.DRAW) {
                drawsCount++;
                scored += challenge.getTeam1Goals();
                conceded += challenge.getTeam1Goals();
            } else if ((result == Challenge.Result.TEAM1_WON &&
                                    (challenge.getHostUserId() == userId || challenge.getOtherUser1Id() == userId)) ||
                        (result == Challenge.Result.TEAM2_WON &&
                                    (challenge.getReceivingUserId() == userId || challenge.getOtherUser2Id() == userId))) {
                scored += Math.max(challenge.getTeam1Goals(), challenge.getTeam2Goals());
                conceded += Math.min(challenge.getTeam1Goals(), challenge.getTeam2Goals());
                winsCount++;
            } else {
                scored += Math.min(challenge.getTeam1Goals(), challenge.getTeam2Goals());
                conceded += Math.max(challenge.getTeam1Goals(), challenge.getTeam2Goals());
                lossesCount++;
            }
        }
        results.setLossesCount(lossesCount);
        results.setWinsCount(winsCount);
        results.setDrawsCount(drawsCount);
        results.setScored(scored);
        results.setConceded(conceded);
        return results;
    }
}
