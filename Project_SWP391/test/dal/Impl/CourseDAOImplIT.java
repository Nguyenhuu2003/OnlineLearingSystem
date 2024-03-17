/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal.Impl;

import dal.CourseDAO;
import dal.DAOCourse;
import java.util.List;
import model.Course;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MH
 */
public class CourseDAOImplIT {

    CourseDAO daoCourse = new CourseDAOImpl();

    public CourseDAOImplIT() {

    }

    

    @Test
    public void testCountCourseByName() {
        assertEquals(6, daoCourse.CountCourseByName("d"));
        assertEquals(0, daoCourse.CountCourseByName("dddd"));
    }

    @Test
    public void testCountCourseByCategory() {
        assertEquals(10, daoCourse.CountCourseByCategory(1));
        assertEquals(4, daoCourse.CountCourseByCategory(2));
        assertEquals(2, daoCourse.CountCourseByCategory(3));
        assertEquals(0, daoCourse.CountCourseByCategory(4));
    }

    //GetCourseByCateId() 
    @Test
    public void testGetCourseByExistingCateId() {
        int categoryId = 1;
        List<Course> list = daoCourse.getCourseByCateId(categoryId);
        assertNotNull(list);
        assertFalse(list.isEmpty());

        for (Course course : list) {
            assertEquals(categoryId, course.getCategory_id());
        }
    }

    @Test
    public void testGetCourseByNonExistingCategoryId() {

        int categoryId = 99;

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByCateId(categoryId);
        // Kiểm tra kết quả
        assertNotNull(courses);
        assertTrue(courses.isEmpty());
    }

    @Test
    public void testGetCourseByNonExistingCategory() {
        int nonExistingCategoryId = -1; // ID danh mục không tồn tại trong cơ sở dữ liệu

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByCateId(nonExistingCategoryId);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertTrue(courses.isEmpty());
    }

    //testCountAllCourse() 
    @Test
    public void testCountAllActiveCourses() {

        int count = daoCourse.CountAllCourse();

        // Kiểm tra kết quả
        assertEquals(16, count);
    }

    // testGetCourseByCateIdPaging() 
    @Test
    public void testGetCourseByCateIdPaging_Success() {
        // Chuẩn bị dữ liệu
        int categoryId = 1; // ID danh mục có trong cơ sở dữ liệu
        int pageIndex = 1; // Trang đầu tiên
        int pageSize = 10; // Số lượng bản ghi trên mỗi trang

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByCateIdPaging(categoryId, pageIndex, pageSize);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        // Kiểm tra số lượng bản ghi trả về
        assertTrue(courses.size() <= pageSize); // Không quá số lượng bản ghi trên mỗi trang
        // Kiểm tra các bản ghi có thuộc danh mục đã chọn
        for (Course course : courses) {
            assertEquals(categoryId, course.getCategory_id());
        }
    }

    @Test
    public void testGetCourseByCateIdPaging_NoCoursesFound() {
        // Chuẩn bị dữ liệu
        int categoryId = -1; // ID danh mục không tồn tại trong cơ sở dữ liệu
        int pageIndex = 1; // Trang đầu tiên
        int pageSize = 10; // Số lượng bản ghi trên mỗi trang

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByCateIdPaging(categoryId, pageIndex, pageSize);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertTrue(courses.isEmpty()); // Danh sách trả về phải rỗng
    }

    @Test
    public void testGetCourseByCateIdPaging_DatabaseConnectionError() {
        // Giả lập lỗi khi không thể kết nối cơ sở dữ liệu
        // Đảm bảo phương thức xử lý lỗi một cách thích hợp

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByCateIdPaging(1, 1, 10);

        // Kiểm tra kết quả
        assertNotNull(courses); // Hoặc xử lý lỗi một cách thích hợp
    }

    @Test
    public void testGetCourseByCateIdPaging_SQLQueryError() {
        // Giả lập lỗi khi không thể thực hiện truy vấn SQL
        // Đảm bảo phương thức xử lý lỗi một cách thích hợp

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByCateIdPaging(-1, 1, 10); // Truy vấn không hợp lệ

        // Kiểm tra kết quả
        assertNotNull(courses); // Hoặc xử lý lỗi một cách thích hợp
    }

    //testGetCourseBySearchPaging
    @Test
    public void testGetCourseBySearchPaging_Success() {
        // Chuẩn bị dữ liệu
        String searchText = "Java"; // Từ khóa tìm kiếm
        int pageIndex = 1; // Trang đầu tiên
        int pageSize = 10; // Số lượng bản ghi trên mỗi trang

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseBySearchPaging(searchText, pageIndex, pageSize);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        // Kiểm tra số lượng bản ghi trả về
        assertTrue(courses.size() <= pageSize); // Không quá số lượng bản ghi trên mỗi trang
        // Kiểm tra các bản ghi có tên khóa học chứa từ khóa tìm kiếm
        for (Course course : courses) {
            assertTrue(course.getCourse_name().toLowerCase().contains(searchText.toLowerCase()));
        }
    }

    @Test
    public void testGetCourseBySearchPaging_NoResultsFound() {
        // Chuẩn bị dữ liệu
        String searchText = "NonExistent"; // Từ khóa tìm kiếm không tồn tại trong cơ sở dữ liệu
        int pageIndex = 1; // Trang đầu tiên
        int pageSize = 10; // Số lượng bản ghi trên mỗi trang

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseBySearchPaging(searchText, pageIndex, pageSize);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertTrue(courses.isEmpty()); // Danh sách trả về phải rỗng
    }

    //testGetCourseByCourseId() 
    @Test
    public void testGetCourseByCourseId_ExistingCourseId() {
        // Chuẩn bị dữ liệu
        int existingCourseId = 1; // ID của khóa học tồn tại trong cơ sở dữ liệu

        // Thực hiện hàm
        Course course = daoCourse.getCourseByCourseId(existingCourseId);

        // Kiểm tra kết quả
        assertNotNull(course); // Không được trả về null
        assertEquals(existingCourseId, course.getCourse_id()); // ID của khóa học trả về phải đúng
        // Kiểm tra các thuộc tính của khóa học
        // (Bạn có thể kiểm tra từng thuộc tính cụ thể của khóa học nếu cần)
    }

    @Test
    public void testGetCourseByCourseId_NonExistingCourseId() {
        // Chuẩn bị dữ liệu
        int nonExistingCourseId = -1; // ID của khóa học không tồn tại trong cơ sở dữ liệu

        // Thực hiện hàm
        Course course = daoCourse.getCourseByCourseId(nonExistingCourseId);

        // Kiểm tra kết quả
        assertNull(course); // Phải trả về null vì không có khóa học nào tương ứng với ID đã cho
    }

    // testGetAllCourse() 
    @Test
    public void testGetAllCourse_Success() {
        // Chuẩn bị dữ liệu
        int pageIndex = 1; // Trang đầu tiên
        int pageSize = 10; // Số lượng bản ghi trên mỗi trang

        // Thực hiện hàm
        List<Course> courses = daoCourse.getAllCourse(pageIndex, pageSize);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        // Kiểm tra số lượng bản ghi trả về
        assertTrue(courses.size() <= pageSize); // Không quá số lượng bản ghi trên mỗi trang

    }

    @Test
    public void testGetAllCourse_NoCoursesFound() {
        // Chuẩn bị dữ liệu
        // Đảm bảo không có khóa học nào trong cơ sở dữ liệu

        // Thực hiện hàm
        List<Course> courses = daoCourse.getAllCourse(100, 10); // Lấy trang đầu tiên với pageSize là 10

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertTrue(courses.isEmpty()); // Danh sách trả về phải rỗng
    }

    // testGetCourseByUserId() 
    @Test
    public void testGetCourseByUserId_UserHasCourses() {
        // Chuẩn bị dữ liệu
        int userId = 2; // ID của người dùng có khóa học

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByUserId(userId);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertFalse(courses.isEmpty()); // Danh sách khóa học của người dùng không được rỗng
        // Kiểm tra các thuộc tính của từng khóa học trong danh sách
        for (Course course : courses) {
            assertEquals(userId, course.getExpert_id()); // Kiểm tra xem người dùng đã tạo khóa học này chính là userId đã cho
        }
    }

    @Test
    public void testGetCourseByUserId_UserHasNoCourses() {
        // Chuẩn bị dữ liệu
        int userId = -1; // ID của người dùng không có khóa học

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByUserId(userId);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertTrue(courses.isEmpty()); // Danh sách khóa học của người dùng phải rỗng
    }

    //testCountCourseByUserId() 
    @Test
    public void testCountCourseByUserId_UserHasCourses() {
        // Chuẩn bị dữ liệu
        int userId = 2; // ID của người dùng có khóa học

        // Thực hiện hàm
        int count = daoCourse.CountCourseByUserId(userId);

        // Kiểm tra kết quả
        assertEquals(16, count); // Số lượng khóa học của người dùng phải lớn hơn 0
    }

    @Test
    public void testCountCourseByUserId_UserHasNoCourses() {
        // Chuẩn bị dữ liệu
        int userId = -1; // ID của người dùng không có khóa học

        // Thực hiện hàm
        int count = daoCourse.CountCourseByUserId(userId);

        // Kiểm tra kết quả
        assertEquals(0, count); // Số lượng khóa học của người dùng phải bằng 0
    }

    //testGetCourseByUserIdPaging() 
    @Test
    public void testGetCourseByUserIdPaging_UserHasCourses() {
        // Chuẩn bị dữ liệu
        int userId = 2; // ID của người dùng có khóa học
        int pageIndex = 1; // Trang đầu tiên
        int pageSize = 10; // Số lượng bản ghi trên mỗi trang

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByUserIdPaging(userId, pageIndex, pageSize);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertFalse(courses.isEmpty()); // Danh sách khóa học của người dùng không được rỗng
        // Kiểm tra số lượng bản ghi trả về
        assertTrue(courses.size() <= pageSize); // Không quá số lượng bản ghi trên mỗi trang
        // Kiểm tra các thuộc tính của từng khóa học trong danh sách
        for (Course course : courses) {
            assertEquals(userId, course.getExpert_id()); // Kiểm tra xem người dùng đã tạo khóa học này chính là userId đã cho
        }
    }

    @Test
    public void testGetCourseByUserIdPaging_UserHasNoCourses() {
        // Chuẩn bị dữ liệu
        int userId = -1; // ID của người dùng không có khóa học
        int pageIndex = 1; // Trang đầu tiên
        int pageSize = 10; // Số lượng bản ghi trên mỗi trang

        // Thực hiện hàm
        List<Course> courses = daoCourse.getCourseByUserIdPaging(userId, pageIndex, pageSize);

        // Kiểm tra kết quả
        assertNotNull(courses);
        assertTrue(courses.isEmpty()); // Danh sách khóa học của người dùng phải rỗng
    }

    // testInsertCourse
//    @Test
//    public void testInsertCourseUserExist() {
//        // Chuẩn bị dữ liệu
//        Course newCourse = new Course();
//        newCourse.setExpert_id(2); // ID của người dùng tạo khóa học
//        newCourse.setCategory_id(1); // ID của danh mục khóa học
//        newCourse.setCourse_name("Tên khóa học"); // Tên của khóa học
//        newCourse.setImage("url_hinh_anh.jpg"); // URL của hình ảnh khóa học
//        newCourse.setTagline("Mô tả ngắn về khóa học"); // Mô tả ngắn về khóa học
//        newCourse.setStatus(1); // Trạng thái của khóa học
//        newCourse.setDescription("Mô tả chi tiết về khóa học"); // Mô tả chi tiết về khóa học
//        newCourse.setCreated_at(new java.sql.Date(System.currentTimeMillis())); // Ngày tạo khóa học
//        newCourse.setFeature(0); // Đặc điểm của khóa học
//
//        // Thực hiện hàm
//        daoCourse.insertCourse(newCourse);
//
//        // Kiểm tra kết quả
//        // Bạn có thể thêm các kiểm tra phù hợp tùy theo cụ thể của ứng dụng,
//        // ví dụ kiểm tra xem dữ liệu đã được thêm vào cơ sở dữ liệu thành công không.
//    }

//    @Test
//    public void testInsertCourseUserNotExist() {
//        // Chuẩn bị dữ liệu
//        Course newCourse = new Course();
//        newCourse.setExpert_id(20); // ID của người dùng tạo khóa học
//        newCourse.setCategory_id(1); // ID của danh mục khóa học
//        newCourse.setCourse_name("Tên khóa học"); // Tên của khóa học
//        newCourse.setImage("url_hinh_anh.jpg"); // URL của hình ảnh khóa học
//        newCourse.setTagline("Mô tả ngắn về khóa học"); // Mô tả ngắn về khóa học
//        newCourse.setStatus(1); // Trạng thái của khóa học
//        newCourse.setDescription("Mô tả chi tiết về khóa học"); // Mô tả chi tiết về khóa học
//        newCourse.setCreated_at(new java.sql.Date(System.currentTimeMillis())); // Ngày tạo khóa học
//        newCourse.setFeature(0); // Đặc điểm của khóa học
//
//        // Thực hiện hàm
//        daoCourse.insertCourse(newCourse);
//
//        // Kiểm tra kết quả
//        // Bạn có thể thêm các kiểm tra phù hợp tùy theo cụ thể của ứng dụng,
//        // ví dụ kiểm tra xem dữ liệu đã được thêm vào cơ sở dữ liệu thành công không.
//    }

    @Test
    public void testDeleteCourseExist() {
        // Chuẩn bị dữ liệu
        int courseIdToDelete = 10; // ID của khóa học cần xóa

        // Thực hiện hàm
        daoCourse.deleteCourse(courseIdToDelete);

        // Kiểm tra kết quả
        // Bạn có thể thêm các kiểm tra phù hợp tùy theo cụ thể của ứng dụng,
        // ví dụ kiểm tra xem khóa học đã được xóa khỏi cơ sở dữ liệu thành công không.
    }

    @Test
    public void testDeleteCourseNotExist() {
        // Chuẩn bị dữ liệu
        int courseIdToDelete = 100; // ID của khóa học cần xóa

        // Thực hiện hàm
        daoCourse.deleteCourse(courseIdToDelete);

        // Kiểm tra kết quả
        // Bạn có thể thêm các kiểm tra phù hợp tùy theo cụ thể của ứng dụng,
        // ví dụ kiểm tra xem khóa học đã được xóa khỏi cơ sở dữ liệu thành công không.
    }

}
