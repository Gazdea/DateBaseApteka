<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Components</title>
</head>
<script>
    function editMode(id) {
        var name = document.getElementsByName('name-' + id)[0].value;
        var description = document.getElementsByName('description-' + id)[0].value;
        var updateid = document.getElementsByName('updateid')[0];
        var updatename = document.getElementsByName('updatename')[0];
        var updatedescription = document.getElementsByName('updatedescription')[0];
        updateid.value = id;
        updatename.value = name;
        updatedescription.value = description;
    }
</script>
<body>
<a href="index.jsp">Back</a>
<h2>Components</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Buttons</th>
    </tr>
    </thead>
    <c:forEach var="components" items="${components}">
        <tr>
            <td><input type="text" name="id-${components.component_id}" value="${components.component_id}" disabled></td>
            <td><input type="text" name="name-${components.component_id}" value="${components.name}" disabled></td>
            <td><input type="text" name="description-${components.component_id}" value="${components.description}" disabled></td>
            <td>
                <input type="button" value="Select" onclick="editMode(${components.component_id})">
                <form action="components" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="componentId" value="${components.component_id}">
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
        <form action="components" method="post">
            <input type="hidden" name="action" value="add">
            <td> <input type="text" name="addname" required> </td>
            <td> <input type="text" name="adddescription" required> </td>
            <td> <input type="submit" name="add" value="Add"> </td>
        </form>
    </tr>
</table>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>description</th>
    </tr>
    <tr>
        <form action="components" method="post">
            <input type="hidden" name="action" value="update">
            <td> <input type="text" name="updateid" required></td>
            <td> <input type="text" name="updatename" required> </td>
            <td> <input type="text" name="updatedescription" required> </td>
            <td> <input type="submit" name="update" value="Update"> </td>
        </form>
    </tr>
    </tbody>
</table>
</body>
</html>