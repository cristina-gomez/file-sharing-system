package gui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WindowTracker {
    private final Map<String, Point> lastPositions = new HashMap<>();

    public void savePosition(String id, Point p) {
        lastPositions.put(id, p);
    }

    public Point getLastPosition(String id) {
        return lastPositions.getOrDefault(id, new Point(100, 100)); // default
    }
}
