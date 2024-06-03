<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prescriptions</title>
</head>
<script>
    function editMode(id) {
        var patientID = document.getElementsByName('patientID-' + id)[0].value;
        var medicationID = document.getElementsByName('medicationID-' + id)[0].value;
        var date_of_prescribed = document.getElementsByName('date_of_prescribed-' + id)[0].value;
        var dosage = document.getElementsByName('dosage-' + id)[0].value;
        var updateprescriptionID = document.getElementsByName('updateprescriptionID')[0];
        var updatepatientID = document.getElementsByName('updatepatientID')[0];
        var updatemedicationID = document.getElementsByName('updatemedicationID')[0];
        var updatedate_of_prescribed = document.getElementsByName('updatedate_of_prescribed')[0];
        var updatedosage = document.getElementsByName('updatedosage')[0];
        updateprescriptionID.value = id;
        updatepatientID.value = patientID;
        updatemedicationID.value = medicationID;
        updatedate_of_prescribed.value = date_of_prescribed;
        updatedosage.value = dosage;
    }
</script>
<body>
<a href="index.jsp">Back</a>
<h2>Prescriptions</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Patient_ID</th>
        <th>Medication_ID</th>
        <th>Date prescribed</th>
        <th>Dosage</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="prescriptions" items="${prescriptions}">
        <tr>
            <td><input type="text" name="prescriptionID-${prescriptions.prescriptionID}" value="${prescriptions.prescriptionID}" disabled></td>
            <td><input type="text" name="patientID-${prescriptions.prescriptionID}" value="${prescriptions.patientID}" disabled></td>
            <td><input type="text" name="medicationID-${prescriptions.prescriptionID}" value="${prescriptions.medicationID}" disabled></td>
            <td><input type="date" name="date_of_prescribed-${prescriptions.prescriptionID}" value="${prescriptions.date_of_prescribed}" disabled></td>
            <td><input type="text" name="dosage-${prescriptions.prescriptionID}" value="${prescriptions.dosage}" disabled></td>
            <td>
                    <input type="submit" value="Select" onclick="editMode(${prescriptions.prescriptionID})">
                <form action="prescriptions" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="prescription_id" value="${prescriptions.prescriptionID}">
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
        <th>medication id</th>
        <th>date of prescribed</th>
        <th>dosage</th>
    </tr>
    <tr>
        <form action="prescriptions" method="post">
            <input type="hidden" name="action" value="add">
            <td><input type="text" name="addpatientID" required></td>
            <td><input type="text" name="addmedicationID" required></td>
            <td><input type="date" name="adddate_of_prescribed" required></td>
            <td><input type="text" name="adddosage" required></td>
            <td><input type="submit" value="Add"></td>
        </form>
    </tr>
</table>
<table>
    <tr>
        <th>id</th>
        <th>patient id</th>
        <th>medication id</th>
        <th>date of prescribed</th>
        <th>dosage</th>
    </tr>
    <tr>
        <form action="prescriptions" method="post">
            <input type="hidden" name="action" value="update">
            <td><input type="text" name="updateprescriptionID" required></td>
            <td><input type="text" name="updatepatientID" required></td>
            <td><input type="text" name="updatemedicationID" required></td>
            <td><input type="date" name="updatedate_of_prescribed" required></td>
            <td><input type="text" name="updatedosage" required></td>
            <td><input type="submit" value="Update"></td>
        </form>
    </tr>
</table>
</body>
</html>