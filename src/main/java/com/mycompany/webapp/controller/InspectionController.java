package com.mycompany.webapp.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycompany.webapp.dto.InspectionImgs;
import com.mycompany.webapp.dto.Inspections;
import com.mycompany.webapp.dto.Treatments;
import com.mycompany.webapp.service.InspectionsService;
import com.mycompany.webapp.twilio.SendMessage;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/inspection")
public class InspectionController {
   private static final Logger logger = LoggerFactory.getLogger(InspectionController.class);
   
   // 이미지 파일 폴더 경로 
   //1. 
   //localhost: 종현
   // private final String imgUrl = "C:/Users/ant94/Documents/JavaProject/uploadfiles/";
   //2.
   //localhost: 빛나 , 서영, 지현
   // private final String imgUrl = "D:/uploadfiles/";
   //3.
   //kosa3.iptime.org
    private final String imgUrl = "C:/Users/COM/Documents/uploadfiles/";
   
   @Autowired
   private InspectionsService inspectionsService;
   
   private 	SendMessage msg = new SendMessage();
   
   @GetMapping("")
   public void readPatient(HttpServletResponse response, @RequestParam String treatmentDate, @RequestParam(defaultValue = "") String state) {
      List<Treatments> treatmentList = inspectionsService.getPatients(treatmentDate, state);
      
      response.setContentType("application/json;charset=UTF-8");
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("treatmentList", treatmentList);
      
      try {
         PrintWriter pw = response.getWriter();
         pw.write(jsonObj.toString());
         pw.flush();
         pw.close();
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   @PutMapping("/istateW")
   public void updateIstateW(@RequestParam int treatmentId) {
      boolean result = inspectionsService.istateW(treatmentId);

      if(result) {
         //logger.info("istate 대기 변경 성공");
      } else {
         //logger.info("istate 대기 변경 실패");
      }
   }
   
   @PutMapping("/istateI")
   public void updateIstateI(@RequestParam int treatmentId) {
      boolean result = inspectionsService.istateI(treatmentId);

      if(result) {
         //logger.info("istate 검사 변경 성공");
      } else {
         //logger.info("istate 검사 변경 실패");
      }
   }
   
   @PutMapping("/istateC")
   public void updateIstateC(@RequestParam int treatmentId) {
      boolean result = inspectionsService.istateC(treatmentId);

      if(result) {
         //logger.info("istate 완료 변경 성공");
      } else {
         //logger.info("istate 완료 변경 실패");
      }
      
//      msg.send("모든 검사가 완료 되었습니다.");
   }
   
   @GetMapping("/inspections")
   public void readPatient(HttpServletResponse response, @RequestParam int treatmentId) {
      List<Inspections> inspectionList = inspectionsService.getInspections(treatmentId);
      
      response.setContentType("application/json;charset=UTF-8");
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("inspectionList", inspectionList);
      
      try {
         PrintWriter pw = response.getWriter();
         pw.write(jsonObj.toString());
         pw.flush();
         pw.close();
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   @PutMapping("/state")
   public void updateStateI(@RequestParam int inspectionId, @RequestParam String state) {
      boolean result = inspectionsService.state(inspectionId, state);

      if(result) {
         //logger.info("state 변경 성공");
      } else {
         //logger.info("state 변경 실패");
      }
   }
   
   @PutMapping("/result")
   public void updateResult(@RequestParam int inspectionId, @RequestParam String inspectionResult) {
      boolean result = inspectionsService.result(inspectionId, inspectionResult);
      
      if(result) {
         //logger.info("result 변경 성공");
      } else {
         //logger.info("result 변경 실패");
      }
   }
   
   @GetMapping("/imgId")
   public void selectImgId(HttpServletResponse response, @RequestParam int inspectionId) {
      List<InspectionImgs> inspectionImgList = inspectionsService.getInspectionImgId(inspectionId);
      
      response.setContentType("application/json;charset=UTF-8");
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("inspectionImgList", inspectionImgList);
   
      try {
         PrintWriter pw = response.getWriter();
         pw.write(jsonObj.toString());
         pw.flush();
         pw.close();
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @GetMapping("/images/{inspectionImgId}")
   public void downloadImg(HttpServletResponse response, @PathVariable("inspectionImgId") int inspectionImgId) {
      inspectionsService.downloadImg(response, inspectionImgId);
   }
   
   @GetMapping("/images")
   public void readImage(HttpServletResponse response, @RequestParam int inspectionId) {
      //logger.info("" + inspectionId);
      List<InspectionImgs> inspectionImgList = inspectionsService.getInspectionImg(inspectionId);
      
      response.setContentType("application/json;charset=UTF-8");
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("inspectionImgList", inspectionImgList);
   
      try {
         PrintWriter pw = response.getWriter();
         pw.write(jsonObj.toString());
         pw.flush();
         pw.close();
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @PostMapping("/images")
   public void createImage(MultipartHttpServletRequest request, InspectionImgs inspectionImgs) {
     if(inspectionImgs.getInspection_img_attach() != null && !inspectionImgs.getInspection_img_attach().isEmpty()) {
        List<MultipartFile> mpf = request.getFiles("inspection_img_attach");
        
        for(MultipartFile mf : mpf) {
           inspectionImgs.setInspection_img_inspection_id(inspectionImgs.getInspection_img_inspection_id());
           inspectionImgs.setInspection_img_oname(mf.getOriginalFilename().substring(0, mf.getOriginalFilename().lastIndexOf(".")));
          inspectionImgs.setInspection_img_sname(new Date().getTime() + "-" + mf.getOriginalFilename());
          inspectionImgs.setInspection_img_type(mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".")));
          try {
             File file = new File(imgUrl + inspectionImgs.getInspection_img_sname());
                 mf.transferTo(file);
              } catch (Exception e) {
                 e.printStackTrace();
              }
          
          boolean result = inspectionsService.createImgs(inspectionImgs);
          
          if(result) {
             //logger.info("img 추가 성공");
          } else {
             //logger.info("img 추가 실패");
          }
        }
     }
   }
   
   @DeleteMapping("/images")
   public void deleteImage(@RequestParam int inspectionId) {
      boolean result = inspectionsService.deleteImage(inspectionId);
      
      if(result) {
         //logger.info("img 삭제 성공");
      } else {
         //logger.info("img 삭제 실패");
      }
   }

   
}