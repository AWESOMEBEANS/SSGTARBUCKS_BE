package com.ssgtarbucks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.SaleDTO;
import com.ssgtarbucks.domain.StockDTO;

@Mapper
public interface StockRepository {
		
	public List<StockDTO> selectStorageByBranchId(String branch_id);
	
	//수정된 갯수로 넘김 (3으로 넘길시 재고 3으로 수정됨) -> 수량정정시 필요
	public int updateStockQuantityByItemId(int stock_quantity, int item_id);
	//차감할 갯수로 넘김 (3으로 넘길 시 원래재고 - 3으로 수정됨) -> 판매갱신시 필요
	public int updateStockQuantity(int stock_quantity, int item_id);
	
	public List<SaleDTO> selectSaleListByBranchId(String branch_id);
	
	public int updateSaleList(String branch_id);
	
	public int updateItemStatus(int item_id);

		
}