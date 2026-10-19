<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hello Servlet</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 60px auto; padding: 0 16px; color: #222; }
        h1 { color: #188038; }
        table { border-collapse: collapse; margin-top: 16px; }
        td { padding: 6px 12px; border-bottom: 1px solid #eee; }
    </style>
</head>
<body>
    <%-- c:out tu dong escape HTML, chong XSS --%>
    <h1>Xin chào, <c:out value="${name}"/>!</h1>
    <p>Trang này được HelloServlet xử lý và chuyển tiếp (forward) sang JSP.</p>

    <table>
        <tr><td>Thời gian máy chủ</td><td><c:out value="${serverTime}"/></td></tr>
        <tr><td>Máy chủ</td><td><c:out value="${serverInfo}"/></td></tr>
    </table>

    <p><a href="${pageContext.request.contextPath}/">&larr; Quay lại trang chủ</a></p>
</body>
</html>
