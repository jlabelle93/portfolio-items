<?php
	$mysqli = mysqli_connect("69.172.204.200", "jlabelle93", "KindlyW@t", "jlabelle_db");
	
	$flag = mysqli_real_escape_string($mysqli, $_POST['flag']);
	$username = mysqli_real_escape_string($mysqli, $_POST['user']);
	$userpw = sha1(mysqli_real_escape_string($mysqli, $_POST['userpass']));
	
	if($flag == 1) {
		$query = "SELECT * FROM projectUsers WHERE username='$username' AND password='$userpw'";
	
		$result = mysqli_query($mysqli, $query) or die(mysqli_error($mysqli));
	
		if(mysqli_num_rows($result) > 0) {
			echo 0;
		} else {
			echo 1;
		}
	}
	
	else if($flag == 2) {
		$query = "SELECT * FROM projectUsers WHERE username='$username'";
		$result = mysqli_query($mysqli, $query) or die(mysqli_error($mysqli));
		
		if(mysqli_num_rows($result) > 0) {
			echo 3;
		}
		else {
			$insQuery = $mysqli -> prepare("INSERT INTO projectUsers(username, password) VALUES(?, ?)");
			$insQuery -> bind_param("ss", $username, $userpw);
			
			$insQuery -> execute();
			echo 2;
		}
	}
?>