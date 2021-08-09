package com.mycompany.webapp.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.Registers;
import com.mycompany.webapp.dto.RegistersCountByDate;

@Mapper
public interface RegistersDao {

	List<Registers> selectAllRegisters();

	List<Registers> selectRegistersByDate(@Param("register_date")String register_date,@Param("state") String state);
	
	List<Registers> selectRegistersByDateForTable(@Param("register_date")String register_date);

	int insertNewRegister(Registers register);
	
	int insertNewRegister2(Registers register);

	int updateRegister(Registers register);

	int updateStateRegister(Registers register);

	int checkRegister(Registers register);
	
	int checkSameRegister(Registers register);
	
	List<Registers> selectThreeMonths();

	List<Registers> selectRegistersState();

	List<Registers> selectPatientsByDays();

	List<Registers> selectQuatersState();

	int deleteRegister(Registers register);

	List<RegistersCountByDate> selectRegisterByDoctor(@Param("register_user_id")String user_id, @Param("register_date")String date);

}
