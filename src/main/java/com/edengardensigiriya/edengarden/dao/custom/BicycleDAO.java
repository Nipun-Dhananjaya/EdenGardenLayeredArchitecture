package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Bicycle;

public interface BicycleDAO extends CrudDAO<Bicycle,String> {
    public String setNextIdValue(int number);
}
