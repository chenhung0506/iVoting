package com.iVoting.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ILotteryDAO {
	List<Map<String, Object>> queryAwardList( Map<String, Object> map ) throws Exception;
	
	List<Map<String, Object>> drawReward( Map<String, Object> map ) throws Exception;

	boolean updateLotteryFlag( List<Map<String, Object>> paramListMap ) throws Exception;
	
	boolean updateLotteryFlag2( List<Map<String, Object>> paramListMap ) throws Exception;

}
