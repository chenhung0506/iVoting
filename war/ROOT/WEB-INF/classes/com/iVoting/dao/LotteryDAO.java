package com.iVoting.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.iVoting.utils.FormatUtils;

@Repository( "lotteryDAO" )
public class LotteryDAO extends MyDaoSupport implements ILotteryDAO {

	private static final Logger log = Logger.getLogger( LotteryDAO.class );

	@SuppressWarnings( "unchecked" )
	public List<Map<String, Object>> queryAwardList( Map<String, Object> map ) throws Exception {
		return getMySqlMapClient().queryForList( "lottery.queryAwardList", map );
	}

	@SuppressWarnings( "unchecked" )
	public List<Map<String, Object>> drawReward( Map<String, Object> map ) throws Exception {
		return getMySqlMapClient().queryForList( "lottery.drawReward", map );
	}

	@Transactional( value = "mySqlTransactionManager", rollbackFor = { Exception.class } )
	public boolean updateLotteryFlag( List<Map<String, Object>> paramListMap ) throws Exception {
		int count = 0;
		List<String> lmIdList = new ArrayList<String>();
		String prize = "";
		for ( Map<String, Object> param : paramListMap ) {
			count++;
			lmIdList.add( param.get( "LM_ID" ).toString() );
			prize = param.get( "PRIZE" ).toString();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "LM_ID_LIST", lmIdList );
		map.put( "PRIZE", prize );
		log.info( "count: " + count + ", param:" + map );
		Assert.isTrue( count == getMySqlMapClient().update( "lottery.updateLotteryFlag", map ), "更新資料筆數與輸入筆數不符應為:" + count );
		return true;
	}

	public boolean updateLotteryFlag1( List<Map<String, Object>> paramListMap ) throws Exception {
		SqlMapClient client1 = super.getMySqlMapClient();
		long startTime = 0L;
		long endTime = 0L;
		if ( !( paramListMap.size() > 0 ) ) {
			return false;
		}
		try {
			startTime = System.currentTimeMillis();
			client1.startTransaction();
			for ( Map<String, Object> map : paramListMap ) {
				client1.update( "lottery.updateLotteryFlag", map );
			}
			client1.commitTransaction();
			client1.endTransaction();
			endTime = System.currentTimeMillis();
		} catch ( Exception e ) {
			log.error( "更新資料異常!", e );
		} finally {
			client1.endTransaction();
		}
		log.info( " 花費亳秒: " + ( endTime - startTime ) );
		return true;
	}

	public boolean updateLotteryFlag2( List<Map<String, Object>> paramListMap ) throws Exception {
		SqlMapClient client = super.getMySqlMapClient();
		long startTime = 0L;
		long endTime = 0L;
		int updateCount = 0;
		int size = paramListMap.size();
		if ( !( size > 0 ) ) {
			return false;
		}
		try {
			client.startTransaction();
			client.startBatch();
			startTime = System.currentTimeMillis();
			for ( Map<String, Object> map : paramListMap ) {
				client.update( "lottery.updateLotteryFlag", map );
			}
			updateCount = client.executeBatch();
			Assert.isTrue( updateCount == size, "須更新: " + size + ", 實際更新: " + updateCount + " ,筆數不符" );
			client.commitTransaction();
			endTime = System.currentTimeMillis();
		} catch ( Exception e ) {
			throw e;
		} finally {
			client.endTransaction();
			log.info( "批次更新結束 異動筆數: " + updateCount + ", 花費亳秒: " + ( endTime - startTime ) );
		}
		return true;
	}

}
