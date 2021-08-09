package com.mycompany.webapp.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.mycompany.webapp.dao.InspectionImgsDao;
import com.mycompany.webapp.dao.InspectionsDao;
import com.mycompany.webapp.dao.TreatmentsDao;
import com.mycompany.webapp.dto.InspectionImgs;
import com.mycompany.webapp.dto.Inspections;
import com.mycompany.webapp.dto.Treatments;

@Service
public class InspectionsService {
   
   @Autowired
   private TreatmentsDao treatmentsDao;
   @Autowired
   private InspectionsDao inspectionsDao;
   @Autowired
   private InspectionImgsDao inspectionImgsDao;
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
   
   // private final String url = "http://localhost:8080";
    private final String url = "http://kosa3.iptime.org:50002";

   public List<Treatments> getPatients(String treatmentDate, String state) {
      List<Treatments> treatmentsList = treatmentsDao.selectTreatments(treatmentDate, state);
      return treatmentsList;
   }
   
   public boolean istateW(int treatmentId) {
      try {
         int row = treatmentsDao.updateIstateW(treatmentId);
         if(row != 1) {
            return false;
         }
      } catch(NullPointerException e) {
         return false;
      }
      return true;
   }

   public boolean istateI(int treatmentId) {
      try {
         int row = treatmentsDao.updateIstateI(treatmentId);
         if(row != 1) {
            return false;
         }
      } catch(NullPointerException e) {
         return false;
      }
      return true;
   }
   
   public boolean istateC(int treatmentId) {
      try {
         int row = treatmentsDao.updateIstateC(treatmentId);
         if(row != 1) {
            return false;
         }
      } catch(NullPointerException e) {
         return false;
      }
      return true;
   }

   public List<Inspections> getInspections(int treatmentId) {
      List<Inspections> inspectionList = inspectionsDao.selectInspections(treatmentId);
      return inspectionList;
   }

   public boolean state(int inspectionId, String state) {
      try {
         int row = inspectionsDao.updateState(inspectionId, state);
         if(row != 1) {
            return false;
         }
      } catch(NullPointerException e) {
         return false;
      }
      return true;
   }

   public boolean result(int inspectionId, String inspectionResult) {
      try {
         int row = inspectionsDao.updateResult(inspectionId, inspectionResult);
         if(row != 1) {
            return false;
         }
      } catch(NullPointerException e) {
         return false;
      }
      return true;
   }

   public List<InspectionImgs> getInspectionImgId(int inspectionId) {
      List<InspectionImgs> inspectionImgList = inspectionImgsDao.selectInspectionImgs(inspectionId);
      return inspectionImgList;
   }

   public void downloadImg(HttpServletResponse response, int inspectionImgId) {
      try {
         InspectionImgs inspectionImg = inspectionImgsDao.selectByInspectionImgId(inspectionImgId);
         response.setContentType(inspectionImg.getInspection_img_type().substring(1));
         String inspection_img_oname = inspectionImg.getInspection_img_oname();
         String inspection_img_type = inspectionImg.getInspection_img_type();
         if(inspection_img_oname == null) return;
         inspection_img_oname = new String(inspection_img_oname.getBytes("UTF-8"),"ISO-8859-1");
         inspection_img_type = new String(inspection_img_type.getBytes("UTF-8"),"ISO-8859-1");
         
         response.setHeader("Content-Disposition", "attachment; filename=\"" + inspection_img_oname + inspection_img_type + "\";");
         
         InputStream is = new FileInputStream(imgUrl + inspectionImg.getInspection_img_sname());
         OutputStream os = response.getOutputStream();
         FileCopyUtils.copy(is, os);
         os.flush();
         is.close();
         os.close();
      } catch(Exception e) {
         e.printStackTrace();
      }
      
   }

   public List<InspectionImgs> getInspectionImg(int inspectionId) {
      List<InspectionImgs> inspectionImgList = inspectionImgsDao.selectInspectionImgs(inspectionId);
      
      for(InspectionImgs inspectionImg : inspectionImgList) {
         inspectionImg.setInspection_img_path(url+"/inspection/images/" + inspectionImg.getInspection_img_id());
      }
      
      return inspectionImgList;
   }

   public boolean createImgs(InspectionImgs inspectionImgs) {
      try {
         int row = inspectionImgsDao.insertImg(inspectionImgs);
         if(row != 1) {
            return false;
         }
      } catch(NullPointerException e) {
         return false;
      }
      return true;
   }

   public boolean deleteImage(int inspectionId) {
      try {
         int row = inspectionImgsDao.deleteImg(inspectionId);
      } catch(NullPointerException e) {
         return false;
      }
      return true;
   }
   


}