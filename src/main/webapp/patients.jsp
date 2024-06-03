<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Patients</title>
</head>
<script>
    function editMode(id) {
        var name = document.getElementsByName('name-' + id)[0].value;
        var birth_date = document.getElementsByName('birth_date-' + id)[0].value;
        var updatepatientId = document.getElementsByName('updatepatientId')[0];
        var updatename = document.getElementsByName('updatename')[0];
        var updatebirth_date = document.getElementsByName('updatebirth_date')[0];
        updatepatientId.value = id;
        updatename.value = name;
        updatebirth_date.value = birth_date;
    }
</script>
<body>
<a href="index.jsp">Back</a>
<h2>Patients</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Birth date</th>
        <th>Record details</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="patients" items="${patients}">
        <tr>
            <td><input type="text" name="patientId-${patients.patient_id}" value="${patients.patient_id}" disabled></td>
            <td><input type="text" name="name-${patients.patient_id}" value="${patients.name}" disabled></td>
            <td><input type="date" name="birth_date-${patients.patient_id}" value="${patients.birth_date}" disabled></td>
            <td><input type="text" name="record_details-${patients.medical_record.record_id}" value="${patients.medical_record.record_details}"></td>
            <td>
                <input type="button" value="Select" onclick="editMode(${patients.patient_id})">
                <form action="patients" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="patientId" value="${patients.patient_id}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<table>
    <tr>
        <th>name</th>
        <th>birth date</th>
    </tr>
    <tr>
        <form action="patients" method="post">
            <input type="hidden" name="action" value="add">
            <td><input type="text" name="addname" required></td>
            <td><input type="date" name="addbirth_date" required></td>
            <td><input type="submit" value="Add"></td>
        </form>
    </tr>
</table>
<table>
    <tr>
        <th>patient id</th>
        <th>name</th>
        <th>birth date</th>
    </tr>
    <tr>
        <form action="patients" method="post">
            <input type="hidden" name="action" value="update">
            <td><input type="text" name="updatepatientId" required></td>
            <td><input type="text" name="updatename" required></td>
            <td><input type="date" name="updatebirth_date" required></td>
            <td><input type="submit" value="Update"></td>
        </form>
    </tr>
</table>
<a href="medicalrecords">Add record details</a>
</body>
</html>