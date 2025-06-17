<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Boilerplate</title>
    <%@ include file="../AllComponent/AllCssHeaders.jsp" %>
</head>
<body>
<%@include file = "../AllComponent/NavBar.jsp"%>

    <div class="d-flex justify-content-center align-items-center vh-100">
      <div class="card" style="width: 25rem;">
        <div class="card-body">
         <h4 class="text-center"> Login Page</h4>
          <form>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Email address*</label>
              <input type="email" class="form-control" id="exampleInputEmail1" required="required">
            </div>
            <div class="mb-3">
              <label for="exampleInputPassword1" class="form-label">Password</label>
              <input type="password" class="form-control" id="exampleInputPassword1" required="required">
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Loginss</button>
            </div>

            <div class="text-center">
                <br> <a href="Register.jsp" >Register</a>
            </div>

          </form>
        </div>
      </div>
    </div>



    <%@ include file="../AllComponent/AllCssJavaScript.jsp" %>
</body>
</html>
