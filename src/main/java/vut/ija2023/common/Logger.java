package vut.ija2023.common;
import vut.ija2023.enviroment.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import vut.ija2023.common.Log.MovementType;
import java.util.stream.Collectors;

public class Logger {

    // Map to store messages for each robot
    private Map<Robot, List<Log>> robotMessages = new HashMap<>();
    // Map to track the previous movement type for each robot
    private Map<Robot, MovementType> previousMovementType = new HashMap<>();

    public void handleMessage(NotifyMessage notifyMessage) {
        Position pos = notifyMessage.getPos();
        Robot robot = notifyMessage.getRobot();
        String message = notifyMessage.getMessage();

        // Check if the robot already has messages stored
        if (!robotMessages.containsKey(robot)) {
            List<Log> messages = new ArrayList<>(); // new list for the robot's messages
            messages.add(new Log(1, pos, null)); // Assuming null for the initial movement type
            robotMessages.put(robot, messages); // to messages array
            previousMovementType.put(robot, null);

            MovementType currentType = getMovementType(message);
            switch (currentType){
                case MOVE:
                    break;
                case TURN:
                    break;
                case WAIT:
                    break;
            }
        }
        // NEW ROBOT
        else {
            List<Log> messages = robotMessages.get(robot); // the list of messages for the robot
            int stepCount = messages.size() + 1;

            MovementType prevType = previousMovementType.get(robot);
            MovementType currentType = getMovementType(message);
            if (currentType == prevType) {
                messages.get(messages.size() - 1).setStepCount(stepCount);
            }
            else {
                messages.add(new Log(stepCount, pos, currentType));
            }
            switch (currentType){
                case MOVE:
                    break;
                case TURN:
                    break;
                case WAIT:
                    break;
            }
        }
    }

    // Helper method to extract the movement type from the message
    private MovementType getMovementType(String message) {
        String[] messageContent = message.split(" ");
        return switch (messageContent[0].toUpperCase()) {
            case "MOVE" -> MovementType.MOVE;
            case "TURN" -> MovementType.TURN;
            case "WAIT" -> MovementType.WAIT;
            default ->
                // does not match any known movement type, return null
                    null;
        };

    }
}

