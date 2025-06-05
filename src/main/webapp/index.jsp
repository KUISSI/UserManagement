<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Ajouter un utilisateur</title>
  </head>
  <body>
    <h1>Ajouter un utilisateur</h1>
    <form method="post" action="users">
      <label>Nom :</label><br />
      <input type="text" name="name" required /><br /><br />

      <label>Email :</label><br />
      <input type="email" name="email" required /><br /><br />

      <label>Téléphone :</label><br />
      <input type="text" name="phone" required /><br /><br />

      <label>Date de naissance :</label><br />
      <input type="date" name="dateNaissance" required /><br /><br />

      <input type="submit" value="Ajouter" />
    </form>

    <p><a href="users">Voir la liste des utilisateurs</a></p>
  </body>
</html>
