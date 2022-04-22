<?php
if(filter_input(INPUT_POST, "email")) {
	$mysqli = mysqli_connect("69.172.204.200", "jlabelle93", "KindlyW@t", "jlabelle_db");
	$email = filter_input(INPUT_POST, "email");
	
	if(!$mysqli) {
		die("Connection to the database failed: " . $mysqli -> connect_error);
	}
	
	$query = $mysqli -> prepare("INSERT INTO ajaxDemo VALUES(?)");
	$query -> bind_param("s", $email);		
	$query -> execute();
	$mysqli -> close();
}
else if(filter_input(INPUT_GET, "email")) {
	$mysqli = mysqli_connect("69.172.204.200", "jlabelle93", "KindlyW@t", "jlabelle_db");
	$email = filter_input(INPUT_GET, "email");
	
	if(!$mysqli) {
		die("Connection to the database failed: " . $mysqli -> connect_error);
	}

	$query = $mysqli -> prepare("SELECT email FROM ajaxDemo WHERE email=?");
	$query -> bind_param("s", $email);		
	if(!$query -> execute()) {
		die("Query execution failed. The following error occurred: " . $query -> error);
	} else {
		$query->bind_result($result_email);
		$fetch_worked = $query->fetch();
		if ($fetch_worked && $result_email) {
			echo "<p>The specified email \"".$result_email."\" was found on the server.</p>";
		} else {
			echo "<p>The specified email \"".$email."\" was not found on the server.</p>";
		}
	}
	$mysqli -> close();
}
?>