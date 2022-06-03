package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

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
      
      //회원가입 --> 회원정보 저장
      public int insert(UserVo userVo) {

         int count = -1;
         getConnecting();
         try {
            // 3. SQL문준비/ 바인딩/ 실행
            // SQL문준비
            String query = "";
            query += " insert into users ";
            query += " values (seq_users_no.nextval, ?, ?, ?, ?) ";

            // 바인딩
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userVo.getId());
            pstmt.setString(2, userVo.getPassword());
            pstmt.setString(3, userVo.getName());
            pstmt.setString(4, userVo.getGender());

            // 실행
            count = pstmt.executeUpdate();
            // 4.결과처리

            System.out.println(count + "건이 추가되었습니다.");
         } catch (SQLException e) {
            System.out.println("error:" + e);
         }
         close();

         return count;

      }
      
      //사용자 정보 가져오기(로그인 시 사용)
      public UserVo getUser(UserVo userVo) {
         getConnecting();
         UserVo authUser = null;
         try {
         
         // 3. SQL문준비/ 바인딩/ 실행
         //SQL문준비
         String query= "";
         query += " select  no ";
         query += "         ,name ";
         query += " from    users ";
         query += " where   id = ? ";
         query += " and     password = ? ";
         System.out.println(query);
         
         //바인딩
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, userVo.getId());
         pstmt.setString(2, userVo.getPassword());
         
         //실행   
         rs = pstmt.executeQuery();
         
         // 4.결과처리
         while(rs.next()) {
            int no = rs.getInt("no");
            String name = rs.getString("name");
            
            authUser = new UserVo();
            authUser.setNo(no);
            authUser.setName(name);
         }
         } catch (SQLException e) {
         System.out.println("error:" + e);
         }
         close();
         return authUser;
      }
      
      
      public UserVo getUser(int no) {
         getConnecting();
         UserVo authUser = null;
         try {
         
         // 3. SQL문준비/ 바인딩/ 실행
         //SQL문준비
         String query= "";
         query += " select  no ";
         query += "         ,id ";
         query += "         ,name ";
         query += "         ,password ";
         query += "         ,gender ";
         query += " from    users ";
         query += " where   no = ? ";
         System.out.println(query);
         
         //바인딩
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, no);
         
         //실행   
         rs = pstmt.executeQuery();
         
         // 4.결과처리
         while(rs.next()) {
            no = rs.getInt("no");
            String id = rs.getString("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String gender = rs.getString("gender");
            
            authUser = new UserVo();
            authUser.setNo(no);
            authUser.setId(id);
            authUser.setName(name);
            authUser.setPassword(password);
            authUser.setGender(gender);
         }
         } catch (SQLException e) {
         System.out.println("error:" + e);
         }
         close();
         return authUser;
      }
      
      
      
      
      
      
      
      
      
      
      
      public int update(UserVo userVo) {
         int count = -1;
         getConnecting();
         try {
            // 3. SQL문준비/ 바인딩/ 실행
            // SQL문준비
            String query = "";
            query += " update users ";
            query += " set name = ? ";
            query += "     ,password = ? ";
            query += "     ,gender = ? ";
            query += " where id = ? ";
            

            // 바인딩
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userVo.getName());
            pstmt.setString(2, userVo.getPassword());
            pstmt.setString(3, userVo.getGender());
            pstmt.setString(4, userVo.getId());

            // 실행
            count = pstmt.executeUpdate();
            // 4.결과처리

            System.out.println(count + "건이 변경되었습니다.");
         } catch (SQLException e) {
            System.out.println("error:" + e);
         }
         close();

         return count;
      }
      
}