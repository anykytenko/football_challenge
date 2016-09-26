package reports.challenges;

import java.util.List;

/**
 * Created by ANykytenko on 8/18/2016.
 */
public class ChallengesList {

    private List<Row> activeChallengeRows;
    private List<Row> closedChallengeRows;

    public void setActiveChallengeRows(List<Row> activeChallengeRows) {
        this.activeChallengeRows = activeChallengeRows;
    }

    public void setClosedChallengeRows(List<Row> closedChallengeRows) {
        this.closedChallengeRows = closedChallengeRows;
    }

    public List<Row> getActiveChallengeRows() {
        return activeChallengeRows;
    }

    public List<Row> getClosedChallengeRows() {
        return closedChallengeRows;
    }

}
