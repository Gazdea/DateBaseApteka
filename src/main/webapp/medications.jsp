<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Medications</title>
</head>
<script>
    function editMode(id) {
        var name = document.getElementsByName('name-' + id)[0].value;
        var description = document.getElementsByName('description-' + id)[0].value;
        var updatemedicationId = document.getElementsByName('updatemedicationId')[0];
        var updatename = document.getElementsByName('updatename')[0];
        var updatedescription = document.getElementsByName('updatedescription')[0];
        updatemedicationId.value = id;
        updatename.value = name;
        updatedescription.value = description;
    }
</script>
<body>
<a href="index.jsp">Back</a>
<h2>Medications</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Components</th>
    </tr>
    </thead>
    <ta>
    <c:forEach var="medications" items="${medications}">
        <tr>
            <td><input type="text" name="medicationId-${medications.medication_id}" value="${medications.medication_id}" disabled></td>
            <td><input type="text" name="name-${medications.medication_id}" value="${medications.name}" disabled></td>
            <td><input type="text" name="description-${medications.medication_id}" value="${medications.description}" disabled></td>
            <td>
                <table>
                    <c:forEach var="component" items="${medications.components}">
                        <tr>
                            <input type="text" name="component-${component.component_id}" value="${component.name}" disabled>
                        </tr>
                        <tr>
                            <form action="medications" method="post">
                                <input type="hidden" name="action" value="deletecomponent">
                                <input type="hidden" name="deletecomponent_id" value="${component.component_id}">
                                <input type="hidden" name="deletemedication_id" value="${medications.medication_id}">
                                <input type="submit" value="Delete">
                            </form>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>
                <input type="button" value="Select" onclick="editMode(${medications.medication_id})">
                <form action="medications" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="medicationId" value="${medications.medication_id}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<table>
    <tr>
        <th>name</th>
        <th>description</th>
    </tr>
    <tr>
        <form action="medications" method="post">
            <input type="hidden" name="action" value="add">
            <td><input type="text" name="name" required></td>
            <td><input type="text" name="description" required></td>
            <td></td>
            <td><input type="submit" value="add"></td>
        </form>
    </tr>
</table>
<table>
    <tr>
        <th>medicationId</th>
        <th>name</th>
        <th>description</th>
    </tr>
    <tr>
        <form action="medications" method="post">
            <input type="hidden" name="action" value="update">
            <td><input type="text" name="updatemedicationId" required></td>
            <td><input type="text" name="updatename" required></td>
            <td><input type="text" name="updatedescription" required></td>
            <td><input type="submit" value="Update"></td>
        </form>
    </tr>
</table>
<table>
    <tr>
        <th>medicament id</th>
        <th>component id</th>
    </tr>
    <tr>
        <form action="medications" method="post">
            <input type="hidden" name="action" value="addcomponent">
            <td><input type="text" name="addmedicamentid" required></td>
            <td><input type="text" name="addcomponentid" required></td>
            <td><input type="submit" value="Add component to medicament"></td>
        </form>
    </tr>
</table>

    </tbody>
</body>
</html>