package com.koreait.board4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import com.koreait.board4.vo.BoardCmtVO;

public class BoardCmtDAO {
	public static int insertCmt(BoardCmtVO param) {
		int result = 0;
		
		Connection con = null;
		PreparedStatement ps = null;

		String sql = " INSERT INTO t_board3_cmt"
				+ " (i_cmt, i_board, i_user, cmt)"
				+ " VALUES"
				+ " (seq_cmt.nextval, ?, ?, ?) ";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);			
			ps.setInt(1, param.getI_board());
			ps.setInt(2, param.getI_user());
			ps.setString(3, param.getCmt());
			
			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
		
		return result;
	}
	
	public static List<BoardCmtVO> selectBoardCmtList(BoardCmtVO param){
		List<BoardCmtVO> list = new LinkedList<BoardCmtVO>();
		
		Connection con  = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " select A.i_cmt, A.i_user, A.cmt, A.r_dt, B.nm as writerNm "
				+" from t_board3_cmt A " 
				+" inner join t_user3 B " 
				+" on A.i_user = B.i_user " 
				+" where i_board = ? " 
				+" order by i_cmt ";
		
			try {
				con = DbCon.getCon();
				ps = con.prepareStatement(sql);
				ps.setInt(1, param.getI_board());
				rs = ps.executeQuery();
				
				while(rs.next()) {
					BoardCmtVO vo = new BoardCmtVO();
					//i_cmt, i_user, cmt, r_dt, writernm
					vo.setI_cmt(rs.getInt("i_cmt"));
					vo.setI_user(rs.getInt("i_user"));
					vo.setCmt(rs.getString("cmt"));
					vo.setR_dt(rs.getString("r_dt"));
					vo.setWriterNm(rs.getString("writerNm"));
					//리스트 만들기 
					list.add(vo); 
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DbCon.close(con, ps, rs);
			}
			return list;
	}
	public static  void deleteBoardCmt(BoardCmtVO param) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = " delete from t_board3_cmt where i_cmt=? and i_board=? ";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_cmt());
			ps.setInt(2, param.getI_board());
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps);
		}
	}
}
