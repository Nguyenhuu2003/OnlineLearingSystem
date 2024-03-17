

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="././css/styles.css" rel="stylesheet" type="text/css"/>

        <title>Manage Lesson</title>

    </head>
    <body>
        <jsp:include page="../../components/header.jsp"></jsp:include>


            <!--                    <div class="col-md-3">
                                    <form action="SearchSubjectLesson" method="post">
                                        <div class="form-group">
                                            <label for="search">Search Lesson</label>
                                            <input type="text" name="lessonKeyword"class="form-control" id="courseKeyword" placeholder="Search lesson..."  value="${lessonKeyword}">
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
                                    <input hidden="" name="courseId" value="${courseId}">
                                    <input type="text" name="page"value="1" hidden>
                                    <button type="submit" class="btn btn-primary mb-2">Filter</button>
                                </form>   
            
                            </div>-->




        <div class="container-fluid "  style="display: flex;padding: 0px;padding-bottom: 15px;min-height: 100vh">
            <div class="row content " style="display: contents">
                <div class="col-sm-2 col-md-2 sidenav hidden-xs " style="background-color: white;border-radius: 15px;padding: 0px" >
                    <h2 style="text-align: center">Course Detail</h2>


                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link "  href="ShowInformationCourse?courseId=${course.course_id}&action=${action}" >Overview</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"  href="pricePackageView?courseId=${course.course_id}&action=${action}">Price Package</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" aria-current="page" aria-disabled="true" href="ShowLesson?courseId=${course.course_id}&action=${action}" style="background-color: #38A5EE">Lesson List</a>
                        </li>

                    </ul>

                </div>


                <div class="col-sm-10 col-md-10 "  style="padding-left: 30px;">
                    <div class="row" >
                        <div class="col" >
                            <nav aria-label="breadcrumb">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="Home">Home</a></li>
                                    <li class="breadcrumb-item"><a href="SubjectsList">List Course Manage</a></li>
                                    <li class="breadcrumb-item" aria-current="#">Subject Detail</li>
                                    <li class="breadcrumb-item active" aria-current="#">List Lesson Manage</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                    <div class="well alert alert-primary" >
                        <h3 style="color: blue">${course.course_name}</h3>
                    </div>
                    <div class="col-sm-12 col-md-9">
                        <c:if test="${ not empty message}">
                            <h4>${numberOfChapter} Chapters were found</h4>
                            <h5>Includes a total ${numberOfLesson} lessons</h5>
                        </c:if> 
                        <c:if test="${ not empty message}">
                            <div class="alert alert-success">
                                <strong>${message}!</strong> 
                            </div>
                        </c:if> 

                        <c:if test="${ numberOfLesson==0}">
                            <div class="alert alert-success">
                                <strong>No lesson founded!</strong> 
                            </div>
                        </c:if> 


                        <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addChapter">Add Chapter</button>
                        <c:forEach items="${listChapter}" var="chapter">    
                            <div class="accordion accordion-flush" id="Chapter${chapter.chapter_id}" style="margin: 0px;margin-bottom: 10px; ">

                                <div class="accordion-item lesson-item " style="padding: 0">

                                    <h2 class="accordion-header row" style="width: 100%;margin: 0px;">
                                        <div class="col-md-9" style="padding:  0">
                                            <button class="accordion-button collapsed button" style="color: black;background-color: #d2d2d2;"type="button" data-bs-toggle="collapse" data-bs-target="#collapse${chapter.chapter_id}" aria-expanded="true" aria-controls="collapse${chapter.chapter_id}">${chapter.chapter_name}
                                            </button>
                                        </div>

                                        <div class="col-md-3" style="padding: 0;align-items: center;text-align: center;">

                                            <div class="btn-group btn-group-sm" role="group" aria-label="Button group" style="width: 100%;height: 100%">

                                                <button type="button" data-bs-toggle="modal" data-bs-target="#editChapter${chapter.chapter_id}" class="btn btn-outline-info col-md-4" >Edit Chapter</button>
                                                <button type="button" data-bs-toggle="modal" data-bs-target="#deleteChapter${chapter.chapter_id}" class="btn btn-outline-danger col-md-4">Delete Chapter</button>  
                                                <button type="button" data-bs-toggle="modal" data-bs-target="#addLesson${chapter.chapter_id}" class="btn btn-outline-primary col-md-4">Add Lesson</button>  

                                            </div>
                                        </div>

                                    </h2>
                                    <div id="collapse${chapter.chapter_id}" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-heading${chapter.chapter_id}">
                                        <div class="accordion-body" style="padding: 0px">
                                            <c:forEach items="${listLesson}" var="lesson">
                                                <c:if test="${chapter.chapter_id == lesson.chapter_id}">  
                                                    <div class="row" style="margin: 0;padding: 0;    background-color: #ababab " >

                                                        <div class="col-md-1 "></div>

                                                        <div class="col-md-7 col-sm-12" style="padding-left: 16px;color: #343a40;text-decoration: none">
                                                            <a href="viewLessonDetail?action=${action}&courseId=${course.course_id}&lesson_id=${lesson.lesson_id}" style="text-decoration: none;">${lesson.lesson_name}</a>
                                                        </div>

                                                        <div class="col-md-1">
                                                            <c:if test="${lesson.status == 1}">
                                                                <span style="color: #1cb01c;">Active</span>
                                                            </c:if>
                                                            <c:if test="${lesson.status == 0}">
                                                                <span style="color: red;">Inactive</span>
                                                            </c:if>
                                                        </div>
                                                        <div class="col-md-1"></div>
                                                        <c:if test="${action eq 'edit'}">
                                                            <div class="btn-group btn-group-sm col-md-2 col-sm-12" role="group" aria-label="Button group" style="padding: 0px;background-color: white">
                                                                <button  type="button" data-bs-toggle="modal" data-bs-target="#editLesson${lesson.lesson_id}" class="btn btn-outline-info col-md-4">Edit</button>
                                                                <button type="button" data-bs-toggle="modal" data-bs-target="#deleteLesson${lesson.lesson_id}" class="btn btn-outline-danger col-md-4">Delete</button>
                                                            </div>
                                                        </c:if>
                                                        <!-- Delete  Lesson-->
                                                        <div class="modal" id="deleteLesson${lesson.lesson_id}">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h4 class="modal-title">Confirm Delete</h4>
                                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <p>Are you sure you want to delete this Dimension?</p>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <form action="deleteLesson" method="post">
                                                                            <input type="text" name="page"value="1" hidden>
                                                                            <input hidden="" name="chapter_name" value="${chapter.chapter_name}">
                                                                            <input hidden="" name="courseId" value="${courseId}">
                                                                            <input hidden="" name ="lessonId"  value="${lesson.lesson_id}">
                                                                            <button type="submit" class="btn btn-danger">Delete</button>
                                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div> 

                                                        <!--edit Lesson  -->                    
                                                        <div class="modal" id="editLesson${lesson.lesson_id}">

                                                            <div class="modal-dialog">
                                                                <div class="modal-content"
                                                                     style="border: 1px darkgrey solid; border-radius: 10px;  margin: 0 auto; padding: 20px;">

                                                                    <div class="modal-header">
                                                                        <h4 class="modal-title">Edit Lesson</h4>
                                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                    </div>

                                                                    <div class="modal-body">
                                                                        <form action="editLesson" method="post" enctype="multipart/form-data">
                                                                            <input type="text" 
                                                                                   hidden=""name="lessonId" value="${lesson.lesson_id}">
                                                                            <input type="text" 
                                                                                   hidden=""name="chapterId" value="${chapter.chapter_id}">
                                                                            <input type="text" 
                                                                                   hidden=""name="courseId" value="${courseId}">


                                                                            <div class="form-group">
                                                                                <label style="margin: 5px;">Full Name</label> 
                                                                                <input style="margin: 5px;" type="text" 
                                                                                       class="form-control" name="lessonName" placeholder="Enter name" value="${lesson.lesson_name}">
                                                                            </div>

                                                                            <div class="form-group">
                                                                                <label for="status">Status</label>
                                                                                <select class="form-select" name="status" id="pk_status">
                                                                                    <option value="1" selected=""style="color: #1cb01c" <c:if test="${lesson.status==1}"></c:if>>Active</option>
                                                                                    <option value="0" style="color: red" <c:if test="${lesson.status==0}"></c:if>>Inactive</option>
                                                                                    </select>
                                                                                </div> 
                                                                                <div class="form-group">
                                                                                    <label style="margin: 5px;">Video</label> <br/>
                                                                                    <video controls="" width="100%" height="100%"> <source src="video/${lesson.content}" type="video/mp4"></video>
                                                                                <input style="margin: 5px;" type="file" 
                                                                                       class="form-control" name="video" placeholder="Enter photo">
                                                                            </div>

                                                                            <button type="submit" class="btn btn-primary">Edit</button>
                                                                            <button type="reset" class="btn btn-primary">Cancel</button>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div> 

                                                    </div>
                                                </c:if>


                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>


                            </div>

                            <!--edit Chapter -->
                            <div class="modal" id="editChapter${chapter.chapter_id}">
                                <div class="modal-dialog">
                                    <div class="modal-content row">

                                        <div class="modal-header">
                                            <h4 class="modal-title">Edit Chapter</h4>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>

                                        <div class="modal-body">
                                            <form action="editChapter" method="post"> 
                                                <input type="text" name="page"value="1" hidden>
                                                <input hidden="" name="courseId" value="${courseId}" >
                                                <input hidden="" name="chapter_id" value="${chapter.chapter_id}" >
                                                <div class="form-group">
                                                    <label for="edit-chapter">Name</label>
                                                    <input type="text" class="form-control" name="chapter_name" required="" value="${chapter.chapter_name}">
                                                </div>
                                                <button type="submit" class="btn btn-primary" style="margin-top: 5px">Update</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div> 

                            <!-- Delete  Chapter-->
                            <div class="modal" id="deleteChapter${chapter.chapter_id}">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 class="modal-title">Confirm Delete</h4>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Are you sure you want to delete this Dimension?</p>
                                        </div>
                                        <div class="modal-footer">
                                            <form action="deleteChapter" method="post">
                                                <input type="text" name="page"value="1" hidden>
                                                <input hidden="" name ="chapter_id" value="${chapter.chapter_id}">
                                                <input hidden="" name="chapter_name" value="${chapter.chapter_name}">
                                                <input hidden="" name="courseId" value="${courseId}">
                                                <button type="submit" class="btn btn-danger">Delete</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div> 

                            <!--add lesson-->
                            <div class="modal" id="addLesson${chapter.chapter_id}">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 class="modal-title">Add Lesson</h4>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>

                                        <div class="modal-body">
                                            <form action="addLesson" method="post" enctype="multipart/form-data">
                                                <input hidden="" name="chapterId" value="${chapter.chapter_id}">
                                                <input hidden="" name="courseId" value="${courseId}">
                                                <input type="text" name="page"value="1" hidden>
                                                <div class="form-group">
                                                    <label>Full Name</label> 
                                                    <input type="text" 
                                                           class="form-control" name="lessonName" placeholder="Enter name">
                                                </div>



                                                <div class="form-group">
                                                    <label for="pk_status">Status</label>
                                                    <select class="form-select" name="pk_status" id="pk_status">
                                                        <option value="1" selected=""style="color: #1cb01c">Active</option>
                                                        <option value="0" style="color: red">Inactive</option>
                                                    </select>
                                                </div> 

                                                <div class="form-group">
                                                    <label>Video</label> <br/>

                                                    <input type="file" 
                                                           class="form-control" name="video" required="">
                                                </div>

                                                <button type="submit" class="btn btn-primary" style="margin-top: 5px">Add</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>                   


                        </c:forEach>


                        <c:if test="${endPage != 0}">    
                            <div class="page">
                                <nav aria-label="Page navigation ">
                                    <a href="#" id="searchCourseLink"></a>
                                    <ul class="pagination">
                                        <li class="page-item"><a class="page-link" href="ShowLesson?courseId=${courseId}&page=${(pageIndex <= 1) ? 1 : pageIndex-1}">Previous</a></li>

                                        <c:forEach begin="1" end="${endPage}" var = "i">
                                            <li class="page-item"><a class="page-link ${i == pageIndex ? 'active':'' }" href="ShowLesson?courseId=${courseId}&page=${i}">${i}</a></li>
                                            </c:forEach>
                                        <li class="page-item"><a class="page-link" href="ShowLesson?courseId=${courseId}&page=${(pageIndex + 1 <= endPage) ? pageIndex + 1 : pageIndex}">Next</a></li>
                                    </ul>
                                </nav>  
                            </div>
                        </c:if> 
                    </div> 
                    <!--add Chapter -->
                    <div class="modal" id="addChapter">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Add Chapter</h4>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>

                                <div class="modal-body">
                                    <form action="addChapter" method="post" >
                                        <input hidden="" name="courseId" value="${courseId}">
                                        <input type="text" name="page"value="1" hidden>
                                        <div class="form-group">
                                            <label for="edit-package">Name</label>
                                            <input type="text" class="form-control" name="chapter_name" required="">
                                        </div>

                                        <button type="submit" class="btn btn-primary" style="margin-top: 5px">Add</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>



    </body>
    <jsp:include page="../../components/footer.jsp"></jsp:include>
</html>

