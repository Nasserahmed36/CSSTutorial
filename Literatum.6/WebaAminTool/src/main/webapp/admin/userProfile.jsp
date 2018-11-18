<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="icon" type="image/png" href="assets/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <title>Identities</title>

    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <meta name="viewport" content="width=device-width"/>


    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Bootstrap core CSS     -->
    <link href="${pageContext.request.contextPath}/admin/assets/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- Animation library for notifications   -->
    <link href="${pageContext.request.contextPath}/admin/assets/css/animate.min.css" rel="stylesheet"/>

    <!--  Light Bootstrap Table core CSS    -->
    <link href="${pageContext.request.contextPath}/admin/assets/css/light-bootstrap-dashboard.css?v=1.4.0"
          rel="stylesheet"/>


    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="${pageContext.request.contextPath}/admin/assets/css/demo.css" rel="stylesheet"/>


    <!--     Fonts and icons     -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/admin/assets/css/pe-icon-7-stroke.css" rel="stylesheet"/>

</head>
<body>
<div class="wrapper">
    <%@include file="leftBar.jsp" %>
    <div class="main-panel">
        <nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/identities/all">Identities</a>
                    <a style="font-weight: bold" class="navbar-brand" href="#">User Profile</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="#">
                                <p>Log out</p>
                            </a>
                        </li>
                        <li class="separator hidden-lg"></li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="content">
            <div class="row">
                <div class="col-md-8">
                    <div class="card">
                        <div class="content">
                            <form>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>First Name</label>
                                            <input value="${requestScope.identity.firstName}" name="firstName"
                                                   id="firstName" type="text" class="form-control"
                                                   placeholder="First Name">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Last Name</label>
                                            <input value="${requestScope.identity.lastName}" name="lastName"
                                                   id="lastName" type="text" class="form-control"
                                                   placeholder="Last Name">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Username</label>
                                            <input value="${requestScope.identity.username}" name="username"
                                                   id="username" type="text" class="form-control"
                                                   placeholder="Username">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Email Address</label>
                                            <input value="${requestScope.identity.email}" name="email" id="email"
                                                   type="text" class="form-control"
                                                   placeholder="Email Address">
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>New Password</label>
                                            <input value="${requestScope.identity.password}" name="password"
                                                   id="newPassword" type="password" class="form-control"
                                                   placeholder="New Password">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Confirm Password</label>
                                            <input value="${requestScope.identity.password}" id="confirmPassword"
                                                   type="password" class="form-control"
                                                   placeholder="Confirm Password">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Type</label>
                                            <select name="type" id="type" class="form-control">
                                                <option value="user">User</option>
                                                <option value="admin">Admin</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <button formaction="${pageContext.request.contextPath}/identities/create"
                                        type="submit" style="margin-left: 10px;"
                                        formmethod="post" class="btn btn-info btn-fill pull-right">Create User
                                </button>
                                <button formaction="${pageContext.request.contextPath}/identities/update"
                                        type="submit" onclick="updateIdentity()" style="margin-left: 10px;"
                                        formmethod="post" class="btn btn-info btn-fill pull-right">Update Profile
                                </button>
                                <button formaction="${pageContext.request.contextPath}/identities/find"
                                        type="submit" style="margin-left: 10px;"
                                        class="btn btn-info btn-fill pull-right">Find Identity
                                </button>
                                <button formaction="${pageContext.request.contextPath}/identities/delete"
                                        type="submit" style="margin-left: 10px;"
                                        formmethod="get"
                                        class="btn btn-info btn-fill pull-right">Delete Identity
                                </button>
                                <div class="clearfix"></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <footer class="footer">
            <div class="container-fluid">
                <nav class="pull-left">
                    <ul>
                        <li>
                            <a href="#">
                                Home
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Company
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Portfolio
                            </a>
                        </li>
                        <li>
                            <a id="anElement" href="#">
                                Blog
                            </a>
                        </li>
                    </ul>
                </nav>
                <p class="copyright pull-right">
                    &copy;
                    <script>document.write(new Date().getFullYear())</script>
                    <a href="http://www.creative-tim.com">Nasser Alkhateeb</a>, made with love for a better Literatum
                </p>
            </div>
        </footer>

    </div>
</div>
<script>

</script>
</body>

<!--   Core JS Files   -->
<script src="${pageContext.request.contextPath}/admin/assets/js/jquery.3.2.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/admin/assets/js/bootstrap.min.js" type="text/javascript"></script>

<!--  Charts Plugin -->
<script src="${pageContext.request.contextPath}/admin/assets/js/chartist.min.js"></script>

<!--  Notifications Plugin    -->
<script src="${pageContext.request.contextPath}/admin/assets/js/bootstrap-notify.js"></script>

<!--  Google Maps Plugin    -->
<script type="${pageContext.request.contextPath}/admin/text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

<!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
<script src="${pageContext.request.contextPath}/admin/assets/js/light-bootstrap-dashboard.js?v=1.4.0"></script>

<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
<script src="${pageContext.request.contextPath}/admin/assets/js/demo.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $(function () {
            $(".a").addClass("active");
        });
    });

    function notify(message) {
        $.notify({
            message: message,
            icon: 'pe-7s-speaker',

        }, {
            placement: {
                from: "top",
                align: "center"
            },
            type: 'info',
            timer: 4000,
        });
    }

</script>


<c:if test="${not empty requestScope.result}">
    <script>notify("${requestScope.result}")</script>
</c:if>
<c:forEach items="${requestScope.errors}" var="error">
    <script>
        notify("${error}");
    </script>
</c:forEach>

</html>