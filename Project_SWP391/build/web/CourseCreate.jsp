<%-- 
    Document   : CreatePost
    Created on : Jan 20, 2024, 10:23:03 PM
    Author     : caomi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="java.util.List"%>

<%@page import ="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="ckeditor5-41.0.0-fawpjqxdt1w9/build/ckeditor.js"></script>
        <title>JSP Page</title>
        <!------ Include the above in your HEAD tag ---------->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>
        <style>
            .ck-editor__editable_inline{
                min-height: 100px;
                overflow-y: auto;
            }
        </style>
        <jsp:include page="components/header.jsp"></jsp:include>
            <div class="container">
                <div class="col-md-10 offset-md-1">
                    <form></form>
                    <h1>Create Course</h1>
                    <h3 style="color:red;" >${infor}</h3>
                <br>
                <form action="CourseCreate" method="post">
                    <div class="row mb-3 align-items-center">
                        <div class="col-2">
                            <label for="title" class="col-form-label">Title:</label>
                        </div>
                        <div class="col-9">
                            <input type="text" id="title" name="title" class="form-control" placeholder="Title..." required>
                        </div>

                    </div>
                    <div class="row mb-3 align-items-center">
                        <div class="col-2">
                            <label for="thumbnail" class="col-form-label">Thumbnail:</label>
                        </div>
                        <div class="col-9">
                            <textarea name="image" id="image" style="height:100px;"></textarea>
                        </div>
                    </div>
                    
                    <div class="row mb-3 align-items-center">
                        <div class="col-2">
                            <label for="expert" class="col-form-label">Assign Expert:</label>
                        </div> 
                        <div class="col-6">
                            <select class="form-control" name="expert"id="expert">

                                <c:forEach items="${expertList}" var="expert">
                                    <option value="${expert.user_id}" >${expert.fullname}</option>
                                </c:forEach>

                            </select>
                        </div>                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-2">
                            <label for="category" class="col-form-label">Category:</label>
                        </div> 
                        <div class="col-6">
                            <select class="form-control" name="cateId"id="category">

                                <c:forEach items="${listSubCate}" var="cate">
                                    <option value="${cate.sub_category_id}" >${cate.sub_category_name}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="col-1">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="active" name="active" id="active">
                                <label class="form-check-label" for="active">
                                    Active
                                </label>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="1" name="feature" id="feature">
                                <label class="form-check-label" for="feature">
                                    Feature
                                </label>
                            </div>
                        </div>

                    </div>
                    <div class="row mb-3 align-items-center">
                        <div class="col-2">
                            <label for="tagline" class="col-form-label">Tag Line:</label>
                        </div>
                        <div class="col-9">
                            <input type="text" id="tagline" class="form-control" name="tagline" placeholder="Sub title..." required>
                        </div>
                    </div>
                    <div class="row mb-3 ">
                        <div class="col-2">
                            <label for="editor1" class="col-form-label">Description</label>
                        </div>
                        <div class="col-9">

                            <textarea name="description" style="height: 400px;"  id="editor1">        </textarea>

                        </div>
                    </div>


                    <button type="submit" class="btn btn-primary mb-2">Save</button>
                </form>
                
            </div>
        </div>
        <script>



            ClassicEditor
                    .create(document.querySelector('#editor1'));

            ClassicEditor
                    .create(document.querySelector('#image'),{
                         toolbar: ['imageInsert']
            });   


        </script>
    </body>
</html>
