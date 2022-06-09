package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestBookVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	
	 //0. import java.sql.*;
    private Connection conn = null;
    private PreparedStatement pstmt= null;
    private ResultSet rs= null;
    
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String id = "webdb";
    private String pw = "webdb";
    
 //메소드 드라이버 연결
    public void getConnecting() {
       try {
          // 1. JDBC 드라이버 (Oracle) 로딩
          Class.forName(driver);
          
          // 2. Connection 얻어오기
          conn = DriverManager.getConnection(url, id, pw);
       } catch (Exception e) {
          System.out.println("error:" + e);
       }
    }
    
    public void close() {
       // 5. 자원정리
       try {
          if (rs != null) {
             rs.close();
          }
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
	
    public int insert(BoardVo boardVo) {
		
		this.getConnecting();
		int count = -1;
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query +=" INSERT INTO board ";
			query +=" values(SEQ_board_no.nextval, ?, ?, 0, sysdate, ?) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			 count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println(count + "건 등록");

		}    catch (SQLException e) {
				System.out.println("error:" + e);
	}
		this.close();
		return count;
	}	
	
    public List<BoardVo> boardList() {
		List<BoardVo> bList = new ArrayList<BoardVo>();
		
		this.getConnecting();	
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select b.no, ";
			query += "        title, ";
			query += "        content, ";
			query += "        name, ";
			query += "        hit, ";
			query += "        reg_date, ";
			query += "        user_no ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			// 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				
				BoardVo boardVo = new BoardVo(no, title, content, name, hit, regDate, userNo);
				bList.add(boardVo);
			}
		} 
		 catch (SQLException e) {
			System.out.println("error:" + e);
		} 
			this.close();
			return bList;
	}
    
    public BoardVo getUser(int no) {
        BoardVo bVo = null;
        try {
           this.getConnecting();
           // 3. SQL문 준비 / 바인딩 / 실행
           //SQl문 준비
           String query = "";
           query += " select  b.no ";
           query += "         ,title ";
           query += "         ,content ";
           query += "         ,name ";
           query += "         ,hit ";
           query += "         ,reg_date ";
           query += "         ,user_no ";
           query += " from board b , users u ";
           query += " where b.user_no = u.no ";
           query += " and u.no = ? ";

           //바인딩
           pstmt = conn.prepareStatement(query);
           pstmt.setInt(1, no);
           //실행
           rs= pstmt.executeQuery();
           //결과처리
           while(rs.next()) {
              String title = rs.getString("title");
              String content = rs.getString("content");
              String name = rs.getString("name");
              int hit = rs.getInt("hit");
              String regDate = rs.getString("reg_date");
              int userNo = rs.getInt("user_no");
              
              bVo = new BoardVo(no,title,content,name,hit,regDate,userNo);
              
           }
        } catch (SQLException e) {
           System.out.println("error:" + e);
        } 
        
        this.close();
        return bVo;
     }
    
    public int delete(BoardVo boardVo) {
		this.getConnecting();
		int count = -1;
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardVo.getNo());
			
			count = pstmt.executeUpdate();
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		return count;
	}
    
    public int update(BoardVo boardVo) {
        int count = -1;
        this.getConnecting();
        try {
           // 3. SQL문준비/ 바인딩/ 실행
           // SQL문준비
           String query = "";
           query += " update board ";
           query += " set title = ?, ";
           query += "     content = ? ";
           query += " where no = ? ";
           
           pstmt = conn.prepareStatement(query);
           
           pstmt = conn.prepareStatement(query);
           pstmt.setString(1, boardVo.getTitle());
           pstmt.setString(2, boardVo.getContent());
           pstmt.setInt(3, boardVo.getNo());
           count = pstmt.executeUpdate();
           
           // 4.결과처리
           System.out.println(count + "건이 수정되었습니다");
        } catch (SQLException e) {
           System.out.println("error:" + e);
        }
        close();

        return count;
     }
    
    public int uphit(int no) {
        int count = -1;
        this.getConnecting();
        try {
           // 3. SQL문준비/ 바인딩/ 실행
           // SQL문준비
          String query = "";
          query += " update board ";
          query += " set hit = hit +1 ";
          query += " where no = ? ";
           
          pstmt = conn.prepareStatement(query);
          pstmt.setInt(1, no);
          
           count = pstmt.executeUpdate();
           // 4.결과처리
           System.out.println("조회수가"+ no + "건이 조회되었습니다");
        } catch (SQLException e) {
           System.out.println("error:" + e);
        }
        close();

        return count;
     }
    
}
