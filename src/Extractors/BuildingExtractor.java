package Extractors;

import Core.Building;
import Core.Room;
import Core.CaseFile;

import java.util.*;

public class BuildingExtractor {
    public static Building loadBuilding(CaseFile caseFile) {
        Building building = new Building() {}; // Anonymous class for generic building
        Map<String, Room> roomMap = new HashMap<>();

        // Create rooms using concrete subclasses based on JSON data
        for (CaseFile.RoomData roomData : caseFile.getRooms()) {
            Room room = createRoomFromData(roomData);
            roomMap.put(room.getName(), room);
            building.addRoom(room);
        }

        // Link neighbors
        for (CaseFile.RoomData roomData : caseFile.getRooms()) {
            Room currentRoom = roomMap.get(roomData.getName());
            roomData.getNeighbors().forEach((direction, neighborName) -> {
                Room neighbor = roomMap.get(neighborName);
                if (neighbor != null) {
                    currentRoom.setNeighbor(direction, neighbor);
                }
            });
        }

        // Set starting room
        building.setCurrentRoom(roomMap.get(caseFile.getStartingRoom()));

        // Validate that all rooms are reachable
        validateRoomConnectivity(building, building.getCurrentRoom());

        return building;
    }

    private static Room createRoomFromData(CaseFile.RoomData roomData) {
        return new Room(roomData.getName(), roomData.getDescription()) {
            @Override
            public String examine(String objectName) {
                return "Default examination text for " + objectName;
            }
        };
    }

    /**
     * Validates that all rooms in the building are reachable from the starting room.
     *
     * @param building The building containing all rooms.
     * @param startRoom The starting room for traversal.
     */
    private static void validateRoomConnectivity(Building building, Room startRoom) {
        Set<Room> visited = new HashSet<>();
        Queue<Room> queue = new LinkedList<>();

        // Start BFS from the starting room
        queue.add(startRoom);
        visited.add(startRoom);

        while (!queue.isEmpty()) {
            Room currentRoom = queue.poll();
            for (Room neighbor : currentRoom.getNeighbors().values()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Check if all rooms were visited
        for (Room room : building.getRooms().values()) {
            if (!visited.contains(room)) {
                throw new IllegalStateException("Room '" + room.getName() + "' is unreachable from the starting room.");
            }
        }
    }
}