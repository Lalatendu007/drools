package org.drools.planner.examples.examination.solver.move.factory;

import java.util.ArrayList;
import java.util.List;

import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.factory.AbstractMoveFactory;
import org.drools.planner.core.solution.Solution;
import org.drools.planner.examples.examination.domain.Exam;
import org.drools.planner.examples.examination.domain.Examination;
import org.drools.planner.examples.examination.domain.Period;
import org.drools.planner.examples.examination.domain.Room;
import org.drools.planner.examples.examination.solver.move.PeriodChangeMove;
import org.drools.planner.examples.examination.solver.move.RoomChangeMove;

/**
 * @author Geoffrey De Smet
 */
public class NeighbourExaminationMoveFactory extends AbstractMoveFactory {

    private static final int PERIOD_JUMP = 2;
    private static final int ROOM_JUMP = 2;

    public List<Move> createMoveList(Solution solution) {
        Examination examination = (Examination) solution;
        List<Period> periodList = examination.getPeriodList();
        List<Room> roomList = examination.getRoomList();
        List<Move> moveList = new ArrayList<Move>();
        for (Exam exam : examination.getExamList()) {
            if (exam.isCoincidenceLeader()) {
                for (Period period : periodList) {
                    int distance = calculateShortestDistance(
                            period.getPeriodIndex(), exam.getPeriod().getPeriodIndex(), periodList.size());
                    if (distance <= PERIOD_JUMP) {
                        moveList.add(new PeriodChangeMove(exam, period));
                    }
                }
            }
            for (Room room : roomList) {
                long distance = calculateShortestDistance(
                        room.getId(), exam.getRoom().getId(), roomList.size());
                if (distance <= ROOM_JUMP) {
                    moveList.add(new RoomChangeMove(exam, room));
                }
            }
        }
        return moveList;
    }

    public int calculateShortestDistance(int a, int b, int size) {
        int innerDistance = Math.abs(a - b);
        int outerDistance = size - innerDistance;
        return Math.min(innerDistance, outerDistance);
    }

    public long calculateShortestDistance(long a, long b, long size) {
        long innerDistance = Math.abs(a - b);
        long outerDistance = size - innerDistance;
        return Math.min(innerDistance, outerDistance);
    }

}
