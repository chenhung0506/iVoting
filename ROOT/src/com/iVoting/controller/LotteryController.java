package com.iVoting.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iVoting.dao.ILotteryDAO;
//import com.iVoting.controller.utils.ModuleException;
//import com.iVoting.lms.dao.DataTempDAO;
//import com.iVoting.lms.dao.IProductExpiredDAO;
//import com.iVoting.lms.dao.IShipFileDAO;
//import com.iVoting.lms.dto.LmsDataFileDTO;
//import com.iVoting.lms.service.DataImporter;
//import com.iVoting.lms.util.FormatUtils;
//import com.iVoting.web.auth.user.Cas335UserObject;
import com.iVoting.dao.IUserDAO;
import com.iVoting.utils.FormatUtils;

@Controller
@RequestMapping( value = "/go", method = { RequestMethod.GET, RequestMethod.POST } )
public class LotteryController {

	private static final Logger log = Logger.getLogger( LotteryController.class );

	@Value( "${shipfile.upload.path}" )
	private String uploadPath;

	@Value( "${shipfile.pdfupload.path}" )
	private String pdfuploadPath;

	@Value( "${shipfile.tempxml.path}" )
	private String xmlTempPath;

	@Value( "${project.name}" )
	private String projectName;

	@Value( "${project.url}" )
	private String projectUrl;

	@Autowired
	private FormatUtils formatUtils;

	@Autowired
	private ILotteryDAO LotteryDAO;

	@RequestMapping( value = "", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView index( HttpServletRequest request, HttpServletResponse response ) {
		ModelAndView mav = new ModelAndView( "lottery.view" );

		mav.addObject( "projectUrl", projectUrl );
		mav.addObject( "projectName", projectName );
		log.info( "show lottery.view" );
		return mav;
	}

	@RequestMapping( value = "/queryAward", method = { RequestMethod.GET, RequestMethod.POST } )
	public @ResponseBody Map<String, List<Map<String, Object>>> queryAward( HttpServletRequest request, Model model ) {
		Map<String, List<Map<String, Object>>> award = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> awardList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> awardList1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> awardList2 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> awardList3 = new ArrayList<Map<String, Object>>();

		try {
			awardList = LotteryDAO.queryAwardList( formatUtils.arraysAsObjMap( "drawReward param: ", "FLAG", Arrays.asList( new String[] { "1", "2", "3" } ) ) );
			for ( Map<String, Object> map : awardList ) {
				switch ( map.get( "FLAG" ).toString() ) {
					case "1":
						awardList1.add( map );
						break;
					case "2":
						awardList2.add( map );
						break;
					case "3":
						awardList3.add( map );
						break;
					default:
						break;
				}
			}
		} catch ( Exception e ) {
			log.error( e.getMessage() );
		}

		award.put( "award1", awardList1 );
		award.put( "award2", awardList2 );
		award.put( "award3", awardList3 );
		return award;
	}

	@RequestMapping( value = "/drawReward", method = { RequestMethod.GET, RequestMethod.POST } )
	public @ResponseBody List<Map<String, Object>> drawReward1( HttpServletRequest request, Model model ) {
		String prize = request.getParameter( "prize" );
		String zip = request.getParameter( "zip" );
		int limit = Integer.parseInt( request.getParameter( "limit" ) );

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = LotteryDAO.drawReward( formatUtils.arraysAsObjMap( "drawReward param: ", "ZIP", zip, "LIMIT", limit, "PRIZE", prize ) );
			Assert.isTrue( LotteryDAO.updateLotteryFlag( result ), "updateLotteryFlag 失敗!!!" );
		} catch ( Exception e ) {
			log.error( e.getMessage() );
		}
		return result;
	}

	@RequestMapping( value = "/resetFlag", method = { RequestMethod.GET, RequestMethod.POST } )
	public @ResponseBody List<Map<String, Object>> resetFlag( HttpServletRequest request, Model model ) {
		String inputPrize = request.getParameter( "prize" );
		String prize = "";
		String flag = "";
		// 參數寫JAVA避免被帶參數串改
		switch ( inputPrize ) {
			case "1":
				prize = "0";
				flag = "1";
				break;
			case "2":
				prize = "0";
				flag = "2";
				break;
			case "3":
				prize = "0";
				flag = "3";
				break;
			default:
				break;
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result.add( formatUtils.arraysAsObjMap( "resetFlag param: ", "PRIZE", prize, "FLAG", flag ) );
			Assert.isTrue( LotteryDAO.updateLotteryFlag2( result ), "resetFlag: " + inputPrize + " 失敗!!!" );
		} catch ( Exception e ) {
			log.error( e.getMessage() );
		}
		return result;
	}

}
