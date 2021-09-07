package com.kh.myprj.domian.common.dao;

import java.util.List;

import com.kh.myprj.domian.common.dto.UpLoadFileDTO;

public interface UpLoadFileDAO {
	/**
	 * 업로드 파일추가
	 * @param list
	 */
	void addFile(UpLoadFileDTO uploadFileDTO);
	void addFiles(List<UpLoadFileDTO> list);
	
	/**
	 * 업로드 파일 조회
	 * @param rid
	 * @return
	 */
	List<UpLoadFileDTO> getFiles(String rid);
	List<UpLoadFileDTO> getFiles(String rid,String code);
	
	/**
	 * 업로드파일 삭제 by 상품
	 * @param pid
	 */
	void deleteFileByRid(String rid);
	void deleteFileByRid(String rid,String code);
	
	List<String> getStore_Fname(String rid);
	
//	/**
//	 * 업로드파일 삭제 by FID
//	 * @param fid
//	 */
//	void deleteFileByFid(String fid);
	
}
