<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>JSP Servlet Demo</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 60px auto; padding: 0 16px; color: #222; }
        h1 { color: #1a73e8; }
        form { display: flex; gap: 8px; margin-top: 24px; }
        input[type=text] { flex: 1; padding: 10px; border: 1px solid #ccc; border-radius: 6px; }
        button { padding: 10px 20px; border: 0; border-radius: 6px; background: #1a73e8; color: #fff; cursor: pointer; }
        .info { margin-top: 32px; font-size: 14px; color: #666; }
    </style>
</head>
<body>
    <h1>Xin chào từ JSP!</h1>
    <p>Dự án <strong>jsp-servlet-demo</strong> đang chạy trên Tomcat 10.1 (Jakarta Servlet 6.0).</p>
    <p>Thời gian hiện tại của máy chủ: <strong><fmt:formatDate value="${now}" pattern="HH:mm:ss dd/MM/yyyy" /></strong></p>

    <form action="${pageContext.request.contextPath}/hello" method="post">
        <input type="text" name="name" placeholder="Nhập tên của bạn..." required>
        <button type="submit">Gửi tới HelloServlet</button>
    </form>

    <p class="info">
        Hoặc mở trực tiếp:
        <a href="${pageContext.request.contextPath}/hello?name=Tomcat">${pageContext.request.contextPath}/hello?name=Tomcat</a>
    </p>
</body>
</html>
