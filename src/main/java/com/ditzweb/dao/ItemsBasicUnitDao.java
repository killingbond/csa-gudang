package com.ditzweb.dao;

import com.ditzweb.beans.ItemsBasicUnit;

public interface ItemsBasicUnitDao {

	public ItemsBasicUnit getItems(int id);
	public void save(ItemsBasicUnit bsc);
	public void update(ItemsBasicUnit bsc);
}
