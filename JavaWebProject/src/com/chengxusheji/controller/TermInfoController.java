package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.TermInfoService;
import com.chengxusheji.po.TermInfo;

//TermInfo管理控制层
@Controller
@RequestMapping("/TermInfo")
public class TermInfoController extends BaseController {

    /*业务层对象*/
    @Resource TermInfoService termInfoService;

	@InitBinder("termInfo")
	public void initBinderTermInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("termInfo.");
	}
	/*跳转到添加TermInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new TermInfo());
		return "TermInfo_add";
	}

	/*客户端ajax方式提交添加学期信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated TermInfo termInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        termInfoService.addTermInfo(termInfo);
        message = "学期添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询学期信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)termInfoService.setRows(rows);
		List<TermInfo> termInfoList = termInfoService.queryTermInfo(page);
	    /*计算总的页数和总的记录数*/
	    termInfoService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = termInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = termInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(TermInfo termInfo:termInfoList) {
			JSONObject jsonTermInfo = termInfo.getJsonObject();
			jsonArray.put(jsonTermInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询学期信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<TermInfo> termInfoList = termInfoService.queryAllTermInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(TermInfo termInfo:termInfoList) {
			JSONObject jsonTermInfo = new JSONObject();
			jsonTermInfo.accumulate("termId", termInfo.getTermId());
			jsonTermInfo.accumulate("termName", termInfo.getTermName());
			jsonArray.put(jsonTermInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询学期信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<TermInfo> termInfoList = termInfoService.queryTermInfo(currentPage);
	    /*计算总的页数和总的记录数*/
	    termInfoService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = termInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = termInfoService.getRecordNumber();
	    request.setAttribute("termInfoList",  termInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "TermInfo/termInfo_frontquery_result"; 
	}

     /*前台查询TermInfo信息*/
	@RequestMapping(value="/{termId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer termId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键termId获取TermInfo对象*/
        TermInfo termInfo = termInfoService.getTermInfo(termId);

        request.setAttribute("termInfo",  termInfo);
        return "TermInfo/termInfo_frontshow";
	}

	/*ajax方式显示学期修改jsp视图页*/
	@RequestMapping(value="/{termId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer termId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键termId获取TermInfo对象*/
        TermInfo termInfo = termInfoService.getTermInfo(termId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonTermInfo = termInfo.getJsonObject();
		out.println(jsonTermInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新学期信息*/
	@RequestMapping(value = "/{termId}/update", method = RequestMethod.POST)
	public void update(@Validated TermInfo termInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			termInfoService.updateTermInfo(termInfo);
			message = "学期更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "学期更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除学期信息*/
	@RequestMapping(value="/{termId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer termId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  termInfoService.deleteTermInfo(termId);
	            request.setAttribute("message", "学期删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "学期删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条学期记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String termIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = termInfoService.deleteTermInfos(termIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出学期信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<TermInfo> termInfoList = termInfoService.queryTermInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "TermInfo信息记录"; 
        String[] headers = { "学期id","学期名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<termInfoList.size();i++) {
        	TermInfo termInfo = termInfoList.get(i); 
        	dataset.add(new String[]{termInfo.getTermId() + "",termInfo.getTermName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TermInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}