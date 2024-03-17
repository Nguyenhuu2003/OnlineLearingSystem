/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;



/**
 *
 * @author MH
 */
public class DAOCourse extends DBContext  {

    public void updateCourse(Course course) {
        try {
            String sql = "UPDATE [dbo].[courses]\n"
                    + "   SET [expert_id] = ?\n"
                    + "      ,[sub_category_id] = ?\n"
                    + "      ,[name] = ?\n"
                    + "      ,[img_url] = ?\n"
                    + "      ,[tagline] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[description] = ?\n"
                    + "      ,[created_at] = GETDATE()\n"
                    + "      ,[feature] = ?\n"
                    + " WHERE course_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course.getExpert_id());
            stm.setInt(2, course.getSub_category_id());
            stm.setString(3, course.getCourse_name());
            stm.setString (4,course.getImage());
            stm.setString(5, course.getTagline());
            stm.setInt(6, course.getStatus());
            stm.setString(7, course.getDescription());
            stm.setInt(8, course.getFeature());
            stm.setInt(9,course.getCourse_id() );
            
            stm.executeUpdate();

        } catch (SQLException ex) {

        }

    }

    ///////////// ham count
    //da check 
//    public int getCountSearchAndCategory(String courseName, int cateId) {
//        int count = 0;
//        try {
//            String sql = "SELECT COUNT(*)\n"
//                    + "FROM courses\n"
//                    + "WHERE sub_category_id IN (SELECT sub_category_id FROM sub_categories WHERE category_id = ?)\n"
//                    + "  AND name LIKE ?;";
//
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, cateId);
//            stm.setString(2, "%" + courseName + "%");
//
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                count = rs.getInt(1);
//            }
//            return count;
//        } catch (Exception ex) {
//            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return 0;
//    }
//
//    //da check 
//    public int getCountSearchAndSubCategory(String courseName, int subCateId) {
//        int count = 0;
//
//        try {
//            String sql = "SELECT COUNT(*)\n"
//                    + "FROM courses\n"
//                    + "WHERE sub_category_id = ? AND name LIKE ?;";
//
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, subCateId);
//            stm.setString(2, "%" + courseName + "%");
//
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                count = rs.getInt(1);
//            }
//            return count;
//        } catch (Exception ex) {
//            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return 0;
//    }

    //da check 
    @Override
    public int CountCourseByName(String courseName) {
        int count = 0;

        try {
            String sql = "SELECT COUNT(course_id) FROM courses WHERE NAME LIKE ?  ";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + courseName + "%");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    //da check
   @Override
    public int CountCourseByCategory(int cateId) {

        try {
            String sql = "SELECT COUNT(*) \n"
                    + "FROM courses\n"
                    + "JOIN sub_categories ON courses.sub_category_id = sub_categories.sub_category_id\n"
                    + "JOIN categories ON sub_categories.category_id = categories.category_id\n"
                    + "WHERE categories.category_id = ? ";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, cateId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    

    // da check
    @Override
    public List<Course> getCourseByCateId(int Cateid) {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM courses\n"
                    + "WHERE sub_category_id IN (SELECT sub_category_id FROM sub_categories WHERE category_id = ?)";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, Cateid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourse_id(rs.getInt(1));
                course.setExpert_id(rs.getInt(2));
                course.setSub_category_id(rs.getInt(3));
                course.setCourse_name(rs.getString(4));
                course.setImage(rs.getString(5));
                course.setTagline(rs.getString(6));
                course.setStatus(rs.getInt(7));
                course.setDescription(rs.getString(8));
                course.setCreated_at(rs.getDate(9));
                list.add(course);

            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    // da check
    @Override
    public int CountAllCourse() {

        try {
            String sql = "select Count(course_id) from courses ";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;

    }

    ///////////// lay theo category
    //da check
//    @Override
//    public List<Course> getCourseByCateId (int cateId) {
//        List<Course> list = new ArrayList<>();
//        try {
//            String sql = "select * from courses "
//                    + "JOIN sub_categories ON courses.sub_category_id = sub_categories.sub_category_id\n"
//                    + "JOIN categories ON sub_categories.category_id = categories.category_id\n"
//                    + "WHERE categories.category_id = ?";
//
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, cateId);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Course course = new Course();
//                course.setCourse_id(rs.getInt(1));
//                course.setExpert_id(rs.getInt(2));
//                course.setSub_category_id(rs.getInt(3));
//                course.setCourse_name(rs.getString(4));
//                course.setImage(rs.getString(5));
//                course.setTagline(rs.getString(6));
//                course.setStatus(rs.getInt(7));
//                course.setDescription(rs.getString(8));
//                course.setCreated_at(rs.getDate(9));
//                list.add(course);
//
//            }
//            return list;
//
//        } catch (Exception ex) {
//            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
//    }

    //da check
    @Override
    public List<Course> getCourseByCateIdPaging(int cateId, int pageIndex, int pageSize) {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM courses JOIN sub_categories ON courses.sub_category_id = sub_categories.sub_category_id\n"
                    + "JOIN categories ON sub_categories.category_id = categories.category_id\n"
                    + "WHERE categories.category_id = ? ORDER BY created_at DESC offset (?-1)*? ROW FETCH NEXT ? ROWS only";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, cateId);
            stm.setInt(2, pageIndex);
            stm.setInt(3, pageSize);
            stm.setInt(4, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourse_id(rs.getInt(1));
                course.setExpert_id(rs.getInt(2));
                course.setSub_category_id(rs.getInt(3));
                course.setCourse_name(rs.getString(4));
                course.setImage(rs.getString(5));
                course.setTagline(rs.getString(6));
                course.setStatus(rs.getInt(7));
                course.setDescription(rs.getString(8));
                course.setCreated_at(rs.getDate(9));
                list.add(course);

            }
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    ////////////// lay theo subcate      
    //da check
//    public List<Course> getAllCourseByDateForSubCategory(int cateId, int pageIndex, int pageSize) {
//        List<Course> list = new ArrayList<>();
//        try {
//            String sql = "select * from courses where sub_category_id = ? ORDER BY created_at DESC offset (?-1)*? ROW FETCH NEXT ? ROWS only";
//
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, cateId);
//            stm.setInt(2, pageIndex);
//            stm.setInt(3, pageSize);
//            stm.setInt(4, pageSize);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Course course = new Course();
//                course.setCourse_id(rs.getInt(1));
//                course.setExpert_id(rs.getInt(2));
//                course.setSub_category_id(rs.getInt(3));
//                course.setCourse_name(rs.getString(4));
//                course.setImage(rs.getString(5));
//                course.setTagline(rs.getString(6));
//                course.setStatus(rs.getInt(7));
//                course.setDescription(rs.getString(8));
//                course.setCreated_at(rs.getDate(9));
//                list.add(course);
//
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return list;
//    }

    /////////// search 
    //da check
    @Override
    public List<Course> getCourseBySearchPaging(String txt, int pageIndex, int pageSize) {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM courses WHERE NAME LIKE ? ORDER BY created_at DESC offset (?-1)*? ROW FETCH NEXT ? ROWS only";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + txt + "%");
            stm.setInt(2, pageIndex);
            stm.setInt(3, pageSize);
            stm.setInt(4, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourse_id(rs.getInt(1));
                course.setExpert_id(rs.getInt(2));
                course.setSub_category_id(rs.getInt(3));
                course.setCourse_name(rs.getString(4));
                course.setImage(rs.getString(5));
                course.setTagline(rs.getString(6));
                course.setStatus(rs.getInt(7));
                course.setDescription(rs.getString(8));
                course.setCreated_at(rs.getDate(9));
                list.add(course);

            }
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    //da check
//    public List<Course> getCourseBySearchForCateId(String txt, int cateId, int pageIndex, int pageSize) {
//        List<Course> list = new ArrayList<>();
//        try {
//            String sql = "SELECT *\n"
//                    + "FROM courses\n"
//                    + "JOIN sub_categories ON courses.sub_category_id = sub_categories.sub_category_id\n"
//                    + "JOIN categories ON sub_categories.category_id = categories.category_id\n"
//                    + "WHERE categories.category_id = ? AND courses.name LIKE ? ORDER BY created_at DESC offset (?-1)*? ROW FETCH NEXT ? ROWS only;";
//
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setString(2, "%" + txt + "%");
//            stm.setInt(1, cateId);
//            stm.setInt(3, pageIndex);
//            stm.setInt(4, pageSize);
//            stm.setInt(5, pageSize);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Course course = new Course();
//                course.setCourse_id(rs.getInt(1));
//                course.setExpert_id(rs.getInt(2));
//                course.setSub_category_id(rs.getInt(3));
//                course.setCourse_name(rs.getString(4));
//                course.setImage(rs.getString(5));
//                course.setTagline(rs.getString(6));
//                course.setStatus(rs.getInt(7));
//                course.setDescription(rs.getString(8));
//                course.setCreated_at(rs.getDate(9));
//                list.add(course);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return list;
//    }

    //da check
//    public List<Course> getCourseBySearchForSubCateId(String txt, int subCateId, int pageIndex, int pageSize) {
//        List<Course> list = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM courses WHERE NAME LIKE ? and sub_category_id =? ORDER BY created_at DESC offset (?-1)*? ROW FETCH NEXT ? ROWS only";
//
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setString(1, "%" + txt + "%");
//            stm.setInt(2, subCateId);
//            stm.setInt(3, pageIndex);
//            stm.setInt(4, pageSize);
//            stm.setInt(5, pageSize);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Course course = new Course();
//                course.setCourse_id(rs.getInt(1));
//                course.setExpert_id(rs.getInt(2));
//                course.setSub_category_id(rs.getInt(3));
//                course.setCourse_name(rs.getString(4));
//                course.setImage(rs.getString(5));
//                course.setTagline(rs.getString(6));
//                course.setStatus(rs.getInt(7));
//                course.setDescription(rs.getString(8));
//                course.setCreated_at(rs.getDate(9));
//                list.add(course);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return list;
//    }

    //da check
    @Override
    public Course getCourseByCourseId(int courseId) {
        Course course = new Course();
        try {
            String sql = "SELECT * FROM courses WHERE course_id = ? ";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                course.setCourse_id(rs.getInt(1));
                course.setExpert_id(rs.getInt(2));
                course.setSub_category_id(rs.getInt(3));
                course.setCourse_name(rs.getString(4));
                course.setImage(rs.getString(5));
                course.setTagline(rs.getString(6));
                course.setStatus(rs.getInt(7));
                course.setDescription(rs.getString(8));
                course.setCreated_at(rs.getDate(9));
                course.setFeature(rs.getInt(10));

            }
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return course;

    }
    @Override
    public List<Course> getAllCourse(int pageIndex, int pageSize) {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "select * from courses  ORDER BY created_at DESC offset (?-1)*? ROW FETCH NEXT ? ROWS only";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pageIndex);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourse_id(rs.getInt(1));
                course.setExpert_id(rs.getInt(2));
                course.setSub_category_id(rs.getInt(3));
                course.setCourse_name(rs.getString(4));
                course.setImage(rs.getString(5));
                course.setTagline(rs.getString(6));
                course.setStatus(rs.getInt(7));
                course.setDescription(rs.getString(8));
                course.setCreated_at(rs.getDate(9));
                list.add(course);

            }
        } catch (Exception ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }


    
    public void createCourse(Course c){
        try {
            String sql = "INSERT INTO [dbo].[courses]\n" +
"           \n" +
"     VALUES\n" +
"           (?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?)";

//    public sub_categories getCategoryIdBySubCategoryId(int sub_category_id) {
//        sub_categories subCate = new sub_categories();
//        try {
//            String sql = "SELECT * FROM sub_categories WHERE sub_category_id = ? ";
//
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, sub_category_id);
//            ResultSet rs = stm.executeQuery();
//
//            while (rs.next()) {
//
//                subCate.setSub_category_id(rs.getInt(1));
//                subCate.setCategory_id(rs.getInt(2));
//                subCate.setSub_category_name(rs.getString(3));
//
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return subCate;
//
//    }


            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,c.getExpert_id());
            stm.setInt(2,c.getSub_category_id());
            stm.setString(3,c.getCourse_name());
            stm.setString(4,c.getImage());
            stm.setString(5,c.getTagline());
            stm.setInt(6,c.getStatus());
            stm.setString(7,c.getDescription());
            stm.setDate(8,c.getCreated_at());
            stm.setInt(9,c.getFeature());
          
            
            ResultSet rs = stm.executeQuery();
            

        } catch (Exception e) {

        }
    }
    
    public int count(String txtSearch) {

        try {
            String sql = "select count (*) from courses where name like ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + txtSearch + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

        }

        return 0;
    }

    

    

    

    public static void main(String[] args) {

        DAOCourse dao = new DAOCourse();

        System.out.println(dao.getCourseByCourseId(1).toString());

    }

    


    

}
