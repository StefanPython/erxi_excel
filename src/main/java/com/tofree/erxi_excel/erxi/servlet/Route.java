package com.tofree.erxi_excel.erxi.servlet;

import com.jm.excel.export.ExcelExport;
import com.tofree.erxi_excel.erxi.dao.OrderDetailDAO;
import com.tofree.erxi_excel.erxi.domain.OrderDetail;
import com.tofree.erxi_excel.erxi.domain.OrderMoney;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Route {
    private Map<String,List> listMap = new HashMap<>();
    private Logger log = LoggerFactory.getLogger(Route.class);

    @RequestMapping("/OrderMoney")
    public String OrderMoney(){
        return "OrderMoney";
    }
    @RequestMapping("/OrderDetail")
    public String OrderDetail(){
        return "OrderDetail";
    }


    @RequestMapping("/getOrderMoney")
    public String getOrderMoney(HttpServletRequest request){
        String StartDate = request.getParameter("StartDate");
        String EndDate=request.getParameter("EndDate");
        log.info("StartDate : "+StartDate.toString());
        OrderDetailDAO odd = new OrderDetailDAO();
        List<OrderMoney> oms = odd.getOrderMoney(StartDate,EndDate);
        //log.info("set : "+oms.toString());
        listMap.put(request.getLocalAddr()+"Money", oms);
        request.setAttribute("oms", oms);
        return "OrderMoney";
    }
    @RequestMapping("/getOrderDetail")
    public String getOrderDetail(HttpServletRequest request){
        String StartDate = request.getParameter("StartDate");
        String EndDate=request.getParameter("EndDate");
        OrderDetailDAO odd = new OrderDetailDAO();
        List<OrderDetail> ods = odd.getOrderDetail(StartDate,EndDate);
        log.info("ods size："+ods.size());
        //log.info("set : "+ods.toString());
        listMap.put(request.getLocalAddr()+"Detail", ods);
        request.setAttribute("ods", ods);
        return "OrderDetail";
    }

    @RequestMapping("/ExcelOut/{type}")
    public ResponseEntity<byte[]> download(HttpServletRequest request,@PathVariable("type") String type) throws IOException {
        // 生成EXcel
        List lists = listMap.get(request.getLocalAddr()+type);
        FileOutputStream fileOut;
        String tempFile = System.currentTimeMillis()+Math.round(Math.random()*10000)+"";
        tempFile = "./"+tempFile+".xls";
        fileOut = new FileOutputStream(tempFile);
        try {
            ExcelExport.exportExcel(fileOut, lists);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileOut.close();
        // 得到生成的Excel
//        response.setContentType("application/force-download");// 设置强制下载不打开
        //指定文件,必须是绝对路径
        File file=new File(tempFile);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //接收时按UTF-8编码接收，输出时以iso-8859-1编码输出
        //下载浏览器响应的那个文件名
        String fileName=new String((simpleDateFormat.format(new Date())+".xlsx").getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
        //下面开始设置HttpHeaders,使得浏览器响应下载
        HttpHeaders headers = new HttpHeaders();

        // 设置响应方式
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 设置响应文件
        headers.setContentDispositionFormData("attachment", fileName);
        //把文件以二进制形式写回
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        boolean result = file.delete();
        log.info("删除文件:"+result);
        return responseEntity;
    }
   /* @RequestMapping("/getOrderMoney1")
    public String getOrderDetail1(HttpServletRequest request,
                                  HttpServletResponse response){
        String StartDate = request.getParameter("StartDate");
        String EndDate=request.getParameter("EndDate");

        OrderDetailDAO odd = new OrderDetailDAO();
        List<OrderMoney> oms = odd.getTest(StartDate,EndDate);
        log.info("set : "+oms.toString());
        listMap.put(request.getLocalAddr(), oms);
        request.setAttribute("oms", oms);
        return "OrderMoney";
    }*/

}
