package support.holders;

import entities.challenge.Challenge;
import entities.challenge.ChallengeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANykytenko on 8/12/2016.
 */
@Component
public class ChallengeHolder {

    @Autowired
    private ChallengeDao challengeDao;

    private List<Challenge> allActiveChallenges;
    private List<Challenge> allClosedChallenges;
    private boolean needUpdateActive = true;
    private boolean needUpdateClosed = true;

    public One ONE = new One();
    public All ALL = new All();

    //==================================================================================================================

    /**
     * To find one challenge (game) between two users
     */
    public class One {

        public PersonalBetween PERSONAL_BETWEEN = new PersonalBetween();
        public Concrete CONCRETE = new Concrete();

        /**
         * To find
         */
        public class Concrete {

            public Challenge get(int challengeId) {
                List<Challenge> list = new ArrayList<Challenge>(ALL.getActive());
                list.addAll(ALL.getClosed());
                for (Challenge challenge : list) {
                    if (challenge.getId() == challengeId) {
                        return challenge;
                    }
                }
                return null;
            }
        }

        /**
         * To find personal challenge (game) between two users
         */
        public class PersonalBetween {

            /**
             * method allows to get active challenge where some of users is host and some is receiving.
             * @param user1Id user id: host or receiving
             * @param user2Id user id: host or receiving
             * @return active challenge
             */
            public Challenge getActiveWhereHostAndReceiving(int user1Id, int user2Id) {
                Challenge result = null;
                for (Challenge challenge : ALL.getActive()) {
                    if (challenge.getHostUserId() == user1Id) {
                        if (challenge.getReceivingUserId() == user2Id) {
                            result = challenge;
                        }
                    } else if (challenge.getHostUserId() == user2Id) {
                        if (challenge.getReceivingUserId() == user1Id) {
                            result = challenge;
                        }
                    }
                }
                return result;
            }

            public Challenge getLastActive(int user1Id, int user2Id) {
                return getLastFromList(user1Id, user2Id, ALL.getActive());
            }

            public Challenge getLastClosed(int user1Id, int user2Id) {
                return getLastFromList(user1Id, user2Id, ALL.getActive());
            }

            private Challenge getLastFromList(int user1Id, int user2Id, List<Challenge> challenges) {
                Challenge result = null;
                for (Challenge challenge : challenges) {
                    if (challenge.getHostUserId() == user1Id || challenge.getOtherUser1Id() == user1Id) {
                        if (challenge.getReceivingUserId() == user2Id || challenge.getOtherUser2Id() == user2Id) {
                            result = challenge;
                        }
                    } else if (challenge.getHostUserId() == user2Id || challenge.getOtherUser1Id() == user2Id) {
                        if (challenge.getReceivingUserId() == user1Id || challenge.getOtherUser2Id() == user1Id) {
                            result = challenge;
                        }
                    }
                }
                return result;
            }
        }

    }

    //==================================================================================================================

    /**
     * To find all challenges by categories
     */
    public class All {

        public ByUser BY_USER = new ByUser();

        /**
         * To find all challenges by categories nad for a concrete user
         */
        public class ByUser {

            public List<Challenge> getActive(int userId) {
                return getFromList(userId, ALL.getActive());
            }

            public List<Challenge> getClosed(int userId) {
                return getFromList(userId, ALL.getClosed());
            }

            public List<Challenge> getFromList(int userId, List<Challenge> list) {
                List<Challenge> challenges = new ArrayList<Challenge>();
                for (Challenge challenge : list) {
                    if (challenge.getHostUserId() == userId || challenge.getReceivingUserId() == userId ||
                            challenge.getOtherUser1Id() == userId || challenge.getOtherUser2Id() == userId) {
                        challenges.add(challenge);
                    }
                }
                return challenges;
            }
        }

        public List<Challenge> getActive() {
            if (isNeedUpdateActive()) {
                updateActive();
            }
            return allActiveChallenges;
        }

        public List<Challenge> getClosed() {
            if (isNeedUpdateClosed()) {
                updateClosed();
            }
            return allClosedChallenges;
        }

        public List<Challenge> getApproved() {
            List<Challenge> result = new ArrayList<Challenge>();
            for (Challenge challenge : getActive()) {
                if (challenge.getStatus() == Challenge.Status.APPROVED) {
                    result.add(challenge);
                }
            }
            return result;
        }
    }

    //==================================================================================================================

    public void updateClosed() {
        allClosedChallenges = challengeDao.getClosed();
        setNeedUpdateClosed(false);
    }

    public void updateActive() {
        allActiveChallenges = challengeDao.getActive();
        setNeedUpdateActive(false);
    }

    public void setNeedUpdateActive(boolean needUpdateActive) {
        this.needUpdateActive = needUpdateActive;
    }

    public void setNeedUpdateClosed(boolean needUpdateClosed) {
        this.needUpdateClosed = needUpdateClosed;
    }

    public boolean isNeedUpdateActive() {
//        return needUpdateActive;
        return true; // to support updating all the time
    }

    public boolean isNeedUpdateClosed() {
//        return needUpdateClosed;
        return true; // to support updating all the time
    }
}
