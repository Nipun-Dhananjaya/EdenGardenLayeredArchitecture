package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Room;

public interface RoomDAO extends CrudDAO<Room,String> {
    public String setNextIdValue(int number);
}
