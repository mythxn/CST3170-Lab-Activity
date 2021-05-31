public class States {

    // auto incrementing identifier
    static private int counter = 0;

    // state information
    public final int identifier;
    public int parentIdentifier;
    public int missionariesLeft;
    public int cannibalsLeft;
    public int missionariesRight;
    public int cannibalsRight;
    public char riverSide;

    public States() {
        identifier = counter;
        counter++;
    }

    // cloner
    public States(States parent) {
        identifier = counter;
        counter++;
        parentIdentifier = parent.identifier;
        missionariesLeft = parent.missionariesLeft;
        cannibalsLeft = parent.cannibalsLeft;
        missionariesRight = parent.missionariesRight;
        cannibalsRight = parent.cannibalsRight;
        riverSide = parent.riverSide;
    }

    public boolean isGoalState() {
        return missionariesRight == 3 && cannibalsRight == 3;
    }

    public boolean isStateValid() {
        int[] counts = new int[4];
        counts[0] = missionariesLeft;
        counts[1] = missionariesRight;
        counts[2] = cannibalsLeft;
        counts[3] = cannibalsRight;

        // x<3 and x>0
        for (int count : counts) {
            if (count > 3 || count < 0) {
                return false;
            }
        }

        // missionaries > cannibals
        if (counts[1] > 0)
            return counts[1] >= counts[3];
        if (counts[0] > 0)
            return counts[0] >= counts[2];

        return true;
    }

    public void move(int missionaries, int cannibals) {
        if (riverSide == 'L') {
            riverSide = 'R';
            cannibalsRight += cannibals;
            cannibalsLeft -= cannibals;
            missionariesRight += missionaries;
            missionariesLeft -= missionaries;
        } else if (riverSide == 'R') {
            riverSide = 'L';
            cannibalsRight -= cannibals;
            cannibalsLeft += cannibals;
            missionariesRight -= missionaries;
            missionariesLeft += missionaries;
        }
    }
}

