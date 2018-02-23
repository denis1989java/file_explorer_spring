<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
</head>
<body>
<div id="feedback"></div>
<div class="container-fluid">

    <div class="row center-block">
        <div class="col-md-5"></div>
        <div class="col-md-2">
            <form action="${pageContext.request.contextPath}/login" id="login-form" method="post">
                <div class="form-group">
                    <label for="username">Login</label>
                    <input onfocusout='checkEmail(this)' type="text" class="form-control" id="username" name="username" placeholder="Login" required>
                    <div class="errors" id="errorMessageEmail"></div>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input onfocusout='checkPassword(this)' type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                    <div class="errors" id="errorMessagePassword"></div>
                </div>
                <button type="submit" id="login-button" class="btn btn-default">Login</button>
            </form>
        </div>
        <div class="col-md-5"></div>
    </div>
</div>
<script>

</script>
</body>
</html>