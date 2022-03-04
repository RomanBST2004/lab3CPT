
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AStarState {
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    private HashMap<Location, Waypoint> OpenedWaypoints;

    private HashMap<Location, Waypoint> ClosedWaypoints;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map) {
        if (map == null) {
            throw new NullPointerException("map cannot be null");
        }
        this.map = map;
        OpenedWaypoints = new HashMap<>();
        ClosedWaypoints = new HashMap<>();
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap() {
        return map;
    }

    /**
    Метод проверяет все вершины в наборе открытых вершин, 
    и после этого он должен вернуть ссылку на вершину с наименьшей общей 
    стоимостью. Если в "открытом" наборе нет вершин, функция возвращает NULL.
     **/
    public Waypoint getMinOpenWaypoint() {
        if (OpenedWaypoints.isEmpty()) {
            return null;
        }
        List<Location> ListWaypoints = new ArrayList<>(OpenedWaypoints.keySet());
        Waypoint TempMinWaypoint = OpenedWaypoints.get(ListWaypoints.get(0));
        for (int i = 1; i < ListWaypoints.size(); i++) {
            if (TempMinWaypoint.getRemainingCost() > OpenedWaypoints.get(ListWaypoints.get(i)).getRemainingCost()) {
                TempMinWaypoint = OpenedWaypoints.get(ListWaypoints.get(i));
            }
        }
        return TempMinWaypoint;
    }

    /**
    Если в наборе «открытых вершин» в настоящее время нет вершины 
    для данного местоположения, то необходимо просто добавить новую вершину.
    Если в наборе «открытых вершин» уже есть вершина для этой 
    локации, добавляется новая вершину только в том случае, если стоимость пути до 
    новой вершины меньше стоимости пути до текущей.  
    Если путь через новую вершину короче, чем путь через текущую вершину, замена текущей вершины на новую
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {
        if (!OpenedWaypoints.containsKey(newWP.getLocation())) {
            OpenedWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        if (OpenedWaypoints.get(newWP.getLocation()).getPreviousCost() > newWP.getPreviousCost()) {
            OpenedWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        return false;
    }

    /** Возвращает открытое число вершин **/
    public int numOpenWaypoints() {
        return OpenedWaypoints.size();
    }

    /**
    Метод перемещает вершину из набора «открытых вершин» в набор 
    «закрытых вершин». Так как вершины обозначены местоположением, метод 
    принимает местоположение вершины.
     **/
    public void closeWaypoint(Location loc) {
        ClosedWaypoints.put(loc, OpenedWaypoints.get(loc));
        OpenedWaypoints.remove(loc);
    }

    /**
     * Возвращает true если коллекция закрытых вершин содержит вершину 
     * для указанного расположения и false в противном случае
     **/
    public boolean isLocationClosed(Location loc) {
        if (ClosedWaypoints.containsKey(loc)) {
            return true;
        }
        return false;
    }
}