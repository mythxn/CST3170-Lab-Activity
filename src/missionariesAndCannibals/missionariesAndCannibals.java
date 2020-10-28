package missionariesAndCannibals;

import java.util.ArrayList;

public class missionariesAndCannibals {

    static ArrayList<States> stack = new ArrayList<>();

    public static void main(String[] args) {

        // initialise
        States S0 = new States();
        S0.missionariesLeft = 3;
        S0.cannibalsLeft = 3;
        S0.missionariesRight = 0;
        S0.cannibalsRight = 0;
        S0.riverSide = 'L';
        stack.add(S0);
        int index = 0;

        // loop until last state is goal state
        while (!stack.get(stack.size() - 1).isGoalState()) {
            // make new state for each move
            // if state is valid and not visited already
            // add to stack

            States S1 = new States(stack.get(index));
            S1.move(0, 2);
            if (S1.isStateValid() && isNotVisited(S1))
                stack.add(S1);

            States S2 = new States(stack.get(index));
            S2.move(2, 0);
            if (S2.isStateValid() && isNotVisited(S2))
                stack.add(S2);

            States S3 = new States(stack.get(index));
            S3.move(1, 1);
            if (S3.isStateValid() && isNotVisited(S3))
                stack.add(S3);

            States S4 = new States(stack.get(index));
            S4.move(0, 1);
            if (S4.isStateValid() && isNotVisited(S4))
                stack.add(S4);

            States S5 = new States(stack.get(index));
            S5.move(1, 0);
            if (S5.isStateValid() && isNotVisited(S5))
                stack.add(S5);

            index++;
        }

        // goal state found, keep only moves to reach goal
        System.out.println();
        for (int i = stack.size() - 2; i >= 0; i--) {
            if (stack.get(i).identifier != stack.get(i + 1).parentIdentifier) {
                stack.remove(i);
            }
        }

        // print stack
        System.out.println("ML  CL  D  MR  CR");
        stack.forEach(stack -> {
            System.out.print(stack.missionariesLeft + "   ");
            System.out.print(stack.cannibalsLeft + "   ");
            System.out.print(stack.riverSide + "   ");
            System.out.print(stack.missionariesRight + "   ");
            System.out.print(stack.cannibalsRight);
            System.out.println();
        });
    }


    // check if we have curState in stack
    private static boolean isNotVisited(States curState) {
        for (States prevState : stack) {
            if (

                    prevState.cannibalsRight == curState.cannibalsRight &&
                            prevState.cannibalsLeft == curState.cannibalsLeft &&
                            prevState.missionariesRight == curState.missionariesRight &&
                            prevState.missionariesLeft == curState.missionariesLeft &&
                            prevState.riverSide == curState.riverSide
            ) {
                return false;
            }
        }
        return true;
    }
}
