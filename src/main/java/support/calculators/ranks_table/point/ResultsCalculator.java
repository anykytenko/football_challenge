package support.calculators.ranks_table.point;

/**
 * Created by ANykytenko on 8/17/2016.
 */
public interface ResultsCalculator {
    int MINIMUM_CHALLENGES_COUNT = 15;

    Results calculate(int userId);

    class Results {
        private int lossesCount;
        private int winsCount;
        private int drawsCount;
        private int scored;
        private int conceded;

        public void setLossesCount(int lossesCount) {
            this.lossesCount = lossesCount;
        }

        public void setWinsCount(int winsCount) {
            this.winsCount = winsCount;
        }

        public void setDrawsCount(int drawsCount) {
            this.drawsCount = drawsCount;
        }

        public void setScored(int scored) {
            this.scored = scored;
        }

        public void setConceded(int conceded) {
            this.conceded = conceded;
        }

        public int getPointsCount() {
            return winsCount * 3 + drawsCount;
        }

        public int getChallengesCount() {
            return lossesCount + winsCount + drawsCount;
        }

        public int getLossesCount() {
            return lossesCount;
        }

        public int getWinsCount() {
            return winsCount;
        }

        public int getDrawsCount() {
            return drawsCount;
        }

        public int getScored() {
            return scored;
        }

        public int getConceded() {
            return conceded;
        }

        public float getPointsAverage() {
            if (getChallengesCount() >= MINIMUM_CHALLENGES_COUNT) {
                return getPointsCount() / (float) getChallengesCount();
            } else {
                return -1;
            }
        }
    }
}
