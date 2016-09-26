package support.calculators.challenge_list;

import entities.challenge.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reports.challenges.ChallengesList;
import reports.challenges.Row;
import support.holders.ChallengeHolder;
import support.holders.SessionsHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANykytenko on 8/18/2016.
 */
@Component
public class CustomChallengesListCalculator implements ChallengesListCalculator {

    @Autowired
    private ChallengeHolder challengeHolder;

    @Autowired
    private SessionsHolder sessionsHolder;

    public ChallengesList calculate() {
        ChallengesList challengesList = new ChallengesList();
        challengesList.setActiveChallengeRows(createChallengeRows(challengeHolder.ALL.getApproved()));
        challengesList.setClosedChallengeRows(createChallengeRows(challengeHolder.ALL.getClosed()));
        return challengesList;
    }

    private List<Row> createChallengeRows(List<Challenge> challenges) {
        List<Row> rows = new ArrayList<Row>();
        for (Challenge challenge : challenges) {
            Row row = new Row();
            row.setTeam1Goals(challenge.getTeam1Goals());
            row.setTeam2Goals(challenge.getTeam2Goals());
            row.setChallengeId(challenge.getId());
            row.setHostUser(sessionsHolder.getUserById(challenge.getHostUserId()));
            row.setOtherUser1(sessionsHolder.getUserById(challenge.getOtherUser1Id()));
            row.setReceivingUser(sessionsHolder.getUserById(challenge.getReceivingUserId()));
            row.setOtherUser2(sessionsHolder.getUserById(challenge.getOtherUser2Id()));
            row.setResult(challenge.getResult());
            rows.add(row);
        }
        return rows;
    }
}
