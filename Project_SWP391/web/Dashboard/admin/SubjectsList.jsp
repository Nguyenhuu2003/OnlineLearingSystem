
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="././css/styles.css" rel="stylesheet" type="text/css"/>
        
    </head>
    <body>

        <jsp:include page="../../components/header.jsp"></jsp:include>

            <div class="container">
                <div class="row" >
                            <div class="col" >
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="Home">Home</a></li>
                                        <li class="breadcrumb-item active" aria-current="#">List Course Manage</li>
                                        
                                    </ol>
                                </nav>
                            </div>
                </div>
                <div class="row" style="min-height: 100vh">
                    <div class="col-sm-2">
                        <form action="SearchSubjectList" method="get">
                            <div class="form-group">
                                <label for="search">Search course</label>
                                <input type="text" name="courseKeyword"class="form-control" id="courseKeyword" placeholder="Search course..."  value="${courseKeyword}">
                        </div>

                        <div class="form-group">
                            <label for="cateID">Category</label>
                            <select class="form-control" name="cateID" id="cateID" >
                                <option  value="" >Show All</option>
                                <c:forEach items="${listCate}" var="cate">
                                    <option value="${cate.category_id}" ${cateID == cate.category_id ? 'selected' : ''}>${cate.category_name}</option>
                                </c:forEach>

                            </select>
                        </div>

                        <div class="form-group">
                            <label class="form-check-label" for="status">
                                Status 
                            </label>
                            <select class="form-control" name="status" id="status" >
                                <option  value="" selected="">Show All</option>
                                <option  value="1" ${status==1 ? 'selected':''}>Active</option>
                                <option  value="0" ${status==0 ? 'selected':''}>Inactive</option>
                                

                            </select>
                            

                        </div>

                        <input type="text" name="page"value="1" hidden>
                        <button type="submit" class="btn btn-primary mb-2">Filter</button>


                    </form>
                </div>

                <div class="col-md-10 col-sm-3">
                    <c:if test="${numberOfCourse > 0}">
                        
                        <table style="text-align: center;" class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Category</th>
                                    <th>Owner</th>
                                    <th>Created</th>
                                    <th>Status</th>
                                   
                                    <th>Actions</th>
                                    

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listCourse}" var="course">
                                    <tr>
                                        <td>${course.course_id}</td>
                                        <td>

                                            ${course.course_name}

                                        </td>
                                        <c:forEach items="${listCate}" var="cate">
                                            <c:if test="${course.category_id==cate.category_id}">
                                                <td>${cate.category_name}</td>
                                            </c:if>
                                        </c:forEach>
                                        <td>Expert</td>
                                        <td>${course.created_at}</td>
                                        <td>
                                            <c:if test="${course.status == 1}">
                                                <span style="color: green;">Active</span>
                                            </c:if>
                                            <c:if test="${course.status == 0}">
                                                <span style="color: red;">Inactive</span>
                                            </c:if>
                                        </td>
                                        
                                        <td>
                                            <div class="btn-group btn-group-sm" role="group" aria-label="Button group">
                                                <a href="ShowInformationCourse?courseId=${course.course_id}&action=view" class="btn btn-outline-info">View</a>
                                                <c:if test="${userRole.role_id==1||userRole.role_id==4}">
                                                    <a href="ShowInformationCourse?courseId=${course.course_id}&action=edit" class="btn btn-outline-primary">Edit</a>
                                                    <a href="#" class="btn btn-outline-danger">Delete</a>
                                                </c:if>
                                            </div>
                                        </td>
                                        
                                        


                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>


                    <c:if test="${numberOfCourse==0}">
                        <p>No course found</p>
                    </c:if>

                    <c:if test="${endPage != 0}">    
                        <div class="page">
                            <nav aria-label="Page navigation ">
                                <a href="#" id="SubjectsList"></a>
                                <ul class="pagination">
                                    <li class="page-item"><a class="page-link" href="SearchSubjectList?courseKeyword=${courseKeyword}&cateID=${cateID}&status=${status}&page=${(pageIndex <= 1) ? 1 : pageIndex-1}">Previous</a></li>

                                        <c:forEach begin="1" end="${endPage}" var = "i">
                                        <li class="page-item"><a class="page-link ${i == pageIndex ? 'active':'' }" href="SearchSubjectList?courseKeyword=${courseKeyword}&cateID=${cateID}&status=${status}&sale=${sale}&page=${i}">${i}</a></li>
                                        </c:forEach>
                                    <li class="page-item"><a class="page-link" href="SearchSubjectList?courseKeyword=${courseKeyword}&cateID=${cateID}&status=${status}&page=${(pageIndex + 1 <= endPage) ? pageIndex + 1 : pageIndex}">Next</a></li>
                                </ul>
                            </nav>  
                        </div>
                    </c:if>     

                </div>    
            </div>
            
        </div>







<jsp:include page="../../components/footer.jsp"></jsp:include>


    </body>
</html>