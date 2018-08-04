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
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h1>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<model.ContactType, java.lang.String>"/>
            ${contactEntry.key.toHtml(contactEntry.value)}<br/>
        </c:forEach>
        <hr/>
    </p>
    <div>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<model.SectionType, model.Section>"/>
            <c:set var="section" scope="request" value="${sectionEntry.getKey().getTitle()}"/>
            <h3>${section}</h3>
            <c:choose>
                <c:when test="${section == SectionType.PERSONAL.title || section == SectionType.OBJECTIVE.title}">
                    <p>${sectionEntry.value}</p>
                </c:when>
                <c:when test="${section==SectionType.ACHIEVEMENTS.title || section==SectionType.QUALIFICATIONS.title}">
                    <c:forEach var="sectionTextListItem" items="${sectionEntry.value.items}">
                        ${sectionTextListItem}<br/>
                    </c:forEach>
                </c:when>
                <c:when test="${section == SectionType.EDUCATION.title || section == SectionType.EXPERIENCE.title}">
                    <c:forEach var="place" items="${sectionEntry.value.places}">
                        <jsp:useBean id="place" type="model.Place"/>
                            <c:set var="url" scope="request" value="${place.homePage.url}"/>
                            <c:set var="placeName" value="${place.homePage.name}"/>
                            <c:if test="${not empty url}">
                                <a href="//${url}"><h4>${placeName}</h4></a>
                            </c:if>
                            <c:if test="${empty url}">
                                <h4>${placeName}</h4>
                            </c:if>
                            <c:forEach var="workPosition" items="${place.workPositions}">
                                <p>
                                <jsp:useBean id="workPosition" type="model.Place.WorkPosition"/>
                                    <b><i>${workPosition.title}</i></b><br/>
                                ${workPosition.description}
                                </p>
                            </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
