package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;


public class GuestbookDao {
	
	//필드
		private Connection conn = null;
		private	PreparedStatement pstmt = null;
		private	ResultSet rs = null;
		
		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "webdb";
		private String pw = "webdb";
		
		public void close () {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		public void  getConnection() {
			
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			}catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		public List<GuestBookVo> guestbookList() {
			List<GuestBookVo> gList = new ArrayList<GuestBookVo>();
			
			this.getConnection();	
			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " select no, "; 
				query += "        name, "; 
				query += "        password, ";
				query += "        content, ";
				query += "        reg_date ";
				query += " from guestbook ";
				
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				// 4.결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regdate = rs.getString("reg_date");
					
					GuestBookVo guestbookVo = new GuestBookVo(no, name, password, content, regdate);
					gList.add(guestbookVo);
				}

			} 
			 catch (SQLException e) {
				System.out.println("error:" + e);
			} 
				this.close();
				return gList;
		}
		
		public int insert(GuestBookVo gVo) {
			
			this.getConnection();
			int count = -1;
			
			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " INSERT INTO guestbook ";
				query += " values(SEQ_GUESTBOOK_no.nextval, ?, ?, ?,sysdate) ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, gVo.getName());
				pstmt.setString(2, gVo.getPassword());
				pstmt.setString(3, gVo.getContent());
				
				 count = pstmt.executeUpdate();
				// 4.결과처리
				System.out.println(count + "건 등록");

			}    catch (SQLException e) {
					System.out.println("error:" + e);
		}
			this.close();
			return count;
		}	
		
		public int delete(GuestBookVo gVo) {
			this.getConnection();
			int count = -1;
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " delete from guestbook ";
				query += " where no= ? ";
				query += " and password= ? ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1,gVo.getNo());
				pstmt.setString(2, gVo.getPassword());
				
				count = pstmt.executeUpdate();
				
			}  catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			this.close();
			return count;
		}
		
		public GuestBookVo getGuest(int no) {
			this.getConnection();
			GuestBookVo guestbookVo = null;
			List<GuestBookVo> gList = new ArrayList<GuestBookVo>();
			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				String query ="";
				query += " select no, ";
				query += "        name, ";
				query += "        password, ";
				query += "        content, ";
				query += "        reg_date ";
				query += " from guestbook ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
				// 4.결과처리
				while (rs.next()) {
					 no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regdate = rs.getString("reg_date");
					
					 guestbookVo = new GuestBookVo(no,name,password,content,regdate);
				}
				

			}  catch (SQLException e) {
					System.out.println("error:" + e);
				}

				this.close();
				return guestbookVo;
		}
}
