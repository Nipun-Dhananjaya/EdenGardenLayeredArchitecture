package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.RoomBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.RoomDAO;
import com.edengardensigiriya.edengarden.dto.RoomDTO;
import com.edengardensigiriya.edengarden.entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {
    RoomDAO roomDAO= (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public List<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException {
        List<RoomDTO> roomList = new ArrayList<>();
        for (Room room : roomDAO.getAll()) {
            roomList.add(new RoomDTO(
                    room.getRoomNo(),
                    room.getRoomType(),
                    room.getSleepCount(),
                    room.getCostPerDay(),
                    room.getAvailability()
            ));
        }
        return roomList;
    }

    @Override
    public List<RoomDTO> searchRooms(String roomNo) throws SQLException, ClassNotFoundException {
        List<Room> roomList = new ArrayList<>();
        for (Room room : roomDAO.search(roomNo)) {
            roomList.add(new Room(
                    room.getRoomNo(),
                    room.getRoomType(),
                    room.getSleepCount(),
                    room.getCostPerDay()
            ));
        }
        return null;
    }

    @Override
    public boolean updateRooms(RoomDTO roomDTO) throws SQLException, ClassNotFoundException {
        return roomDAO.update(new Room(roomDTO.getRoomNo(), roomDTO.getRoomType(), roomDTO.getSleepCount(), roomDTO.getCostPerDay()));
    }

    @Override
    public boolean removeRooms(String roomNo) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(roomNo);
    }

    @Override
    public boolean saveRooms(RoomDTO roomDTO) throws SQLException, ClassNotFoundException {
        return roomDAO.save(new Room(roomDTO.getRoomNo(), roomDTO.getRoomType(), roomDTO.getSleepCount(), roomDTO.getCostPerDay()));
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return roomDAO.newIdGenerate();
    }
}
