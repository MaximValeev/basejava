<%@ page import="model.ContactType" %>
<%@ page import="model.SectionType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="model.Resume" scope="request"/>
    <title>Просмотр резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input required type="text" name="fullName" size="50"></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="${ContactType.values()}">
        <dl>
            <dt>${type.title}</dt>
        <c:choose>
        <c:when test="${type != ContactType.PHONE}">
            <dd><input type="text" name="${type.name()}" size="30"></dd>
        </c:when>
        <c:otherwise>
            <dd><input required type="text" name="${type.name()}" size="30"></dd>
        </c:otherwise>
        </c:choose>
    </dl>
        </c:forEach>
        <hr/>
        <c:forEach var="sectionType" items="${SectionType.values()}">

            <c:choose>
                <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><textarea name="${sectionType.name()}" cols="100"
                                      rows="10"></textarea></dd>
                        <br/>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><textarea placeholder="каждый элемент с новой строки без знаков препинания"
                                      name="${sectionType.name()}" cols="50" rows="10"></textarea>
                        </dd>
                        <br/>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE}">
                    <dl>
                        <dt>${sectionType.title != SectionType.EDUCATION.title ? " Последнее место работы" : "Релевантное образование"}</dt>
                        <br/>
                        <dt>Название места:</dt>
                        <dd><input type="text" name="${sectionType}placeName"></dd>
                        <br/>
                        <dt>Веб-сайт:</dt>
                        <dd><input type="text" name="${sectionType}placeUrl"></dd>
                        <br/>
                        <dt>Должность:</dt>
                        <dd><input type="text" name="${sectionType}workPosition"></dd>
                        <br/>
                        <dt>Описание даолжности:</dt>
                        <dd><textarea name="${sectionType}description" cols="50"
                                      rows="10"></textarea></dd>
                    </dl>
                    <br/>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr/>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
        </p>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
