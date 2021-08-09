package com.mycompany.webapp.dto;

import org.springframework.web.multipart.MultipartFile;

public class InspectionImgs {
	private int inspection_img_id;
	private int inspection_img_inspection_id;
	private String inspection_img_oname;
	private String inspection_img_sname;
	private String inspection_img_type;
	
	private MultipartFile inspection_img_attach;
	private String inspection_img_path;
	
	public int getInspection_img_id() {
		return inspection_img_id;
	}
	public void setInspection_img_id(int inspection_img_id) {
		this.inspection_img_id = inspection_img_id;
	}
	public int getInspection_img_inspection_id() {
		return inspection_img_inspection_id;
	}
	public void setInspection_img_inspection_id(int inspection_img_inspection_id) {
		this.inspection_img_inspection_id = inspection_img_inspection_id;
	}
	public String getInspection_img_oname() {
		return inspection_img_oname;
	}
	public void setInspection_img_oname(String inspection_img_oname) {
		this.inspection_img_oname = inspection_img_oname;
	}
	public String getInspection_img_sname() {
		return inspection_img_sname;
	}
	public void setInspection_img_sname(String inspection_img_sname) {
		this.inspection_img_sname = inspection_img_sname;
	}
	public String getInspection_img_type() {
		return inspection_img_type;
	}
	public void setInspection_img_type(String inspection_img_type) {
		this.inspection_img_type = inspection_img_type;
	}
	
	public MultipartFile getInspection_img_attach() {
		return inspection_img_attach;
	}
	public void setInspection_img_attach(MultipartFile inspection_img_attach) {
		this.inspection_img_attach = inspection_img_attach;
	}
	public String getInspection_img_path() {
		return inspection_img_path;
	}
	public void setInspection_img_path(String inspection_img_path) {
		this.inspection_img_path = inspection_img_path;
	}
	
}
