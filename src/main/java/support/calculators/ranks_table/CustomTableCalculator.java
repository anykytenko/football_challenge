package support.calculators.ranks_table;

import entities.challenge.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import reports.ranks.RanksTable;
import reports.ranks.Row;
import support.holders.ChallengeHolder;
import support.holders.SessionsHolder;
import support.calculators.ranks_table.point.ResultsCalculator;

import java.util.*;

/**
 * Created by ANykytenko on 8/12/2016.
 */
@Component
public class CustomTableCalculator implements TableCalculator {

    @Autowired
    private SessionsHolder sessionsHolder;

    @Autowired
    private ChallengeHolder challengeHolder;

    @Autowired
    private ResultsCalculator resultsCalculator;

    private int year;
    private int month;

    /**
     * Comparator allows to sort users by their ranks. It has the next priorities:
     * 1. Average level of points per game
     * 2. Total Points count
     * 3. Personal game
     * 4. Wins count
     * 5. Games count
     */
    private Comparator<? super Row> comparator = new Comparator<Row>() {
        public int compare(Row row1, Row row2) {

            // 1. Average level of points per game
            if (row2.getPointsAverage() == row1.getPointsAverage()) {

                // 2. Total Points count
                if (row2.getPointsCount() == row1.getPointsCount()) {

                    // 3. Personal game
                    Challenge personalGame = challengeHolder.ONE.PERSONAL_BETWEEN.getLastClosed(row1.getUserId(), row2.getUserId(), year, month);
                    if (personalGame == null || personalGame.getResult() == Challenge.Result.DRAW) {

                        // 4. Wins count
                        if (row2.getWinsCount() == row1.getWinsCount()) {

                            // 5. Games count
                            return row2.getChallengesCount() - row1.getChallengesCount();
                        } else {
                            return row2.getWinsCount() - row1.getWinsCount();
                        }
                    } else {
                        if (personalGame.getResult() == Challenge.Result.TEAM1_WON) {
                            if (personalGame.getHostUserId() == row1.getUserId() ||
                                    personalGame.getOtherUser1Id() == row1.getUserId()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        } else {
                            if (personalGame.getReceivingUserId() == row1.getUserId() ||
                                    personalGame.getOtherUser2Id() == row1.getUserId()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    }
                } else {
                    return row2.getPointsCount() - row1.getPointsCount();
                }
            } else {
                return row2.getPointsAverage() > row1.getPointsAverage() ? 1 : -1;
            }
        }
    };

    public RanksTable calculate(User securityUser, int year, int month) {
        Collection<entities.user.User> activeUsers = sessionsHolder.getActiveUsers();
        RanksTable ranksTable = new RanksTable();
        ranksTable.setUser(sessionsHolder.getUserByName(securityUser.getUsername()));
        List<Row> rows = new ArrayList<Row>();
        for (entities.user.User user: sessionsHolder.getAllUsers()) {
            rows.add(calculateRow(user, activeUsers, ranksTable, year, month));
        }
        Collections.sort(rows, comparator);
        ranksTable.setRows(rows);
        return ranksTable;
    }

    private Row calculateRow(entities.user.User user, Collection<entities.user.User> activeUsers, RanksTable ranksTable,
                             int year, int month) {
        Row row = new Row();
        row.setLinkToAvatar(user.getLinkToAvatar());
        row.setFirstName(user.getFirstName());
        row.setLastName(user.getLastName());
        row.setUserName(user.getUserName());
        row.setUserId(user.getId());
        row.setActive(activeUsers.contains(user));
        ResultsCalculator.Results results = resultsCalculator.calculate(user.getId(), year, month);
        row.setPointsCount(results.getPointsCount());
        row.setPointsAverage(results.getPointsAverage());
        row.setLossesCount(results.getLossesCount());
        row.setWinsCount(results.getWinsCount());
        row.setChallengesCount(results.getChallengesCount());
        row.setDrawsCount(results.getDrawsCount());
        row.setScored(results.getScored());
        row.setConceded(results.getConceded());
        row.setRowState(getState(user, ranksTable, year, month));
        return row;
    }

    private Row.RowState getState(entities.user.User user, RanksTable ranksTable, int year, int month) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (currentMonth == month && currentYear == year) {
            Row.RowState rowState;
            Challenge challenge = challengeHolder.ONE.PERSONAL_BETWEEN.getActiveWhereHostAndReceiving(ranksTable.getUser().getId(), user.getId(), year, month);
            if (user.equals(ranksTable.getUser())) {
                rowState = Row.RowState.ME;
            } else if (challenge == null) {
                rowState = Row.RowState.CHALLENGE;
            } else if (challenge.getHostUserId() == user.getId()) {
                if (challenge.getStatus() == Challenge.Status.IN_PROGRESS) {
                    rowState = Row.RowState.APPROVE_OR_REJECT;
                } else if (challenge.getStatus() == Challenge.Status.APPROVED) {
                    rowState = Row.RowState.APPROVED;
                } else if (challenge.getStatus() == Challenge.Status.REJECTED) {
                    rowState = Row.RowState.REJECTED;
                } else {
                    rowState = Row.RowState.CHALLENGE;
                }
            } else {
                if (challenge.getStatus() == Challenge.Status.IN_PROGRESS) {
                    rowState = Row.RowState.WAITING_FOR_RESPONSE;
                } else if (challenge.getStatus() == Challenge.Status.APPROVED) {
                    rowState = Row.RowState.APPROVED;
                } else if (challenge.getStatus() == Challenge.Status.REJECTED) {
                    rowState = Row.RowState.REJECTED;
                } else {
                    rowState = Row.RowState.CHALLENGE;
                }
            }
            return rowState;
        } else {
            return Row.RowState.NOT_ACTIVE_USER;
        }
    }
}
