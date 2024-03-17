

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

            <div class="container" style="min-height: 100vh">
                <div class="row" >
                    <div class="col">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="Home">Home</a></li>
                                <li class="breadcrumb-item"><a href="SubjectsList">List Course Manage</a></li>
                                <li class="breadcrumb-item active" aria-current="#">View Lesson</li>
                            </ol>
                        </nav>
                    </div>
                </div>



                <div class="row">
                <c:if test="${listChapter eq null}">
                    <h5>No lesson in this course</h5>
                </c:if>


                <c:if test="${lesson ne null}">
                    <div class="col-md-9">
                        <video controls="" width="100%" height="100%"> <source src="video/${lesson.content}" type="video/mp4"></video>
                    </div>
                </c:if>

                <div class="col-md-3">    
                    <c:forEach items="${listChapter}" var="chapter">    
                        <div class="accordion" id="accordionLesson" style="margin: 0px;margin-bottom: 10px; ">

                            <div class="accordion-item " style="padding: 0">

                                <h2 class="accordion-header row" style="width: 100%;margin: 0px;">
                                    <div class="col-md-12" style="padding:  0">
                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${chapter.chapter_id}" aria-expanded="false" aria-controls="collapse${chapter.chapter_id}">${chapter.chapter_name}
                                        </button>
                                    </div>                                  
                                </h2>
                                <div id="collapse${chapter.chapter_id}" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-heading${chapter.chapter_id}">
                                    <div class="accordion-body" style="padding: 0px">
                                        <c:forEach items="${listLesson}" var="lesson">
                                            <c:if test="${chapter.chapter_id == lesson.chapter_id}">  
                                                <div class="row" style="margin: 0;padding: 0;border-bottom:1px solid #38A5EE " >
                                                    <a class="col-md-12 col-sm-12" style="padding-left: 16px;text-decoration: none;" href="viewLessonDetail?courseId=${course.course_id}&lesson_id=${lesson.lesson_id}&action=${action}">${lesson.lesson_name}</a>

                                                </div>
                                            </c:if>                                             
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </div>


            </div> 

        </div>    

        
        <c:if test="${endPage != 0}">    
            <div class="page container">
                <nav aria-label="Page navigation ">
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
        <jsp:include page="../../components/footer.jsp"></jsp:include>

    </body>
</html>
