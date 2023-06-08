package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.RoomDTO;

import java.sql.SQLException;
import java.util.List;

public interface RoomBO extends SuperBO {
    List<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException;
    List<RoomDTO> searchRooms(String roomNo) throws SQLException, ClassNotFoundException;
    boolean updateRooms(RoomDTO roomDTO) throws SQLException, ClassNotFoundException;
    boolean removeRooms(String roomNo) throws SQLException, ClassNotFoundException;
    boolean saveRooms(RoomDTO roomDTO) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;
}
