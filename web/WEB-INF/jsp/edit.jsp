<%@ page import="model.ContactType" %>
<%@ page import="model.SectionType" %>
<%@ page import="util.DateUtil" %>
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
            <dd><input required type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="${ContactType.values()}">
        <dl>
            <dt>${type.title}</dt>
        <dd><input type="text" ${type == ContactType.PHONE ? 'required': ''} name="${type.name()}" size="30" value="${resume.getContact(type)}" size="30"></dd>
        </dl>
        </c:forEach>
        <hr/>
        <c:forEach var="sectionType" items="${SectionType.values()}">

            <c:choose>
                <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                    <dl>
                        <dt><h3>${sectionType.title}</h3></dt>
                        <dd><textarea name="${sectionType.name()}" cols="100"
                                      rows="10">${resume.getSection(sectionType).content}</textarea></dd>
                        <br/>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                    <c:set var="newLine" value="
"/>
                    <c:set var="itemsList" value="${resume.getSection(sectionType)}"/>
                    <dl>
                        <dt><h3>${sectionType.title}</h3></dt>
                        <dd><textarea placeholder="каждый элемент с новой строки бее знаков препинания"
                                      name="${sectionType.name()}" cols="50" rows="10"><c:if
                                test="${not empty itemsList}">${String.join(newLine, resume.getSection(sectionType).items)}</c:if></textarea>
                        </dd>
                        <br/>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE}">
                    <c:set var="place" value="${resume.getSection(sectionType).places.get(0)}"/>
                    <c:set var="placeUrl" scope="request" value="${place.homePage.url}"/>
                    <dl>
                        <dt><h3>${sectionType.title != SectionType.EDUCATION.title ? " Последнее место работы" : "Релевантное образование"}</h3></dt>
                        <c:set var="placeName" value="${place.homePage.name}"/><br/>
                        <c:set var="workPosition" value="${place.workPositions.get(0)}"/>
                        <dt>Название места:</dt>
                        <dd><input type="text" name="${sectionType}placeName" value="${placeName}"></dd>
                        <br/>
                        <dt>Начальная дата:</dt>
                        <dd><input type="text" name="${sectionType}startDate" size="10"
                                   value="${DateUtil.format(workPosition.startDate)}"></dd>
                        <br/>
                        <dt>Конечная дата:</dt>
                        <dd><input type="text" name="${sectionType}endDate" size="10"
                                   value="${DateUtil.format(workPosition.endDate)}"></dd>
                        <br/>
                        <dt>Веб-сайт:</dt>
                        <dd><input type="text" name="${sectionType}placeUrl" value="${placeUrl}"></dd>
                        <br/>
                        <dt>Должность:</dt>
                        <dd><input type="text" name="${sectionType}workPosition" value="${workPosition.title}"></dd>
                        <br/>
                        <dt>Описание должности:</dt>
                        <dd><textarea name="${sectionType}description" cols="50"
                                      rows="10">${workPosition.description}</textarea></dd>
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
