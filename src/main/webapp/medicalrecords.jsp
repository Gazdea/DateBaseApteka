<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Medical Records</title>
</head>
<script>
    function editMode(id) {
        var patientid = document.getElementsByName('patient_id-' + id)[0].value;
        var record_details = document.getElementsByName('record_details-' + id)[0].value;
        var updaterecord_id = document.getElementsByName('updaterecord_id')[0];
        var updatepatient_id = document.getElementsByName('updatepatient_id')[0];
        var updaterecord_details = document.getElementsByName('updaterecord_details')[0];
        updaterecord_id.value = id;
        updatepatient_id.value = patientid;
        updaterecord_details.value = record_details;
    }
</script>
<body>
<a href="index.jsp">Back</a>
<h2>Medical Records</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Patient_ID</th>
        <th>Record Details</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="medicalrecords" items="${medicalrecords}">
        <tr>
            <td><input type="text" name="record_id-${medicalrecords.record_id}" value="${medicalrecords.record_id}" disabled></td>
            <td><input type="text" name="patient_id-${medicalrecords.record_id}" value="${medicalrecords.patient_id}" disabled></td>
            <td><input type="text" name="record_details-${medicalrecords.record_id}" value="${medicalrecords.record_details}" disabled></td>
            <td>
                <input type="button" value="Select" onclick="editMode(${medicalrecords.record_id})">
                <form action="medicalrecords" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="recordId" value="${medicalrecords.record_id}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<table>
    <tr>
        <th>patient id</th>
        <th>record details</th>
    </tr>
    <tr>
        <form action="medicalrecords" method="post">
            <input type="hidden" name="action" value="add">
            <td> <input type="text" id="addpatient_id" name="patient_id" required> </td>
            <td> <input type="text" id="addrecord_details" name="record_details" required> </td>
            <td> <input type="submit" value="Add"> </td>
        </form>
    </tr>
</table>
<table>
    <tr>
        <th>record id</th>
        <th>patient id</th>
        <th>record details</th>
    </tr>
    <tr>
        <form action="medicalrecords" method="post">
            <input type="hidden" name="action" value="update">
            <td> <input type="text" name="updaterecord_id" required></td>
            <td> <input type="text" name="updatepatient_id" required> </td>
            <td> <input type="text" name="updaterecord_details" required> </td>
            <td> <input type="submit" name="update" value="Update"> </td>
        </form>
    </tr>
</table>
</table>
<br>
</body>
</html>